package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class TwitterStream {

    public static final String twitterLogic(ParametersParser parser) throws Exception {

        double[] coordinates;

        if (parser.getPlace().equals("") || parser.getPlace().equals("nearby")) {
            try {
                coordinates = Querist.findCoordinatesByIp();
            } catch (LocationException ex) {
                throw new Exception(ex.getMessage());
            }
        } else {
            try {
                coordinates = Querist.findCoordinates(parser.getPlace());
            } catch (LocationException | PropertiesException ex) {
                throw new Exception(ex.getMessage());
            }
        }
        if (parser.isStream()) {
            Querist.getTwitterStream(coordinates, parser, parser.getSubstring());
        } else {
            try {
                return Querist.getTweets(coordinates, parser, parser.getSubstring());
            } catch (GetTweetException ex) {
                throw new Exception(ex.getMessage());
            }
        }
        return "";
    }

    public static void main(String[] args) {
        ParametersParser parser = new ParametersParser();
        try {
            JCommander p1 = new JCommander(parser, args);

            boolean help = parser.isHelp();

            if (help) {
                p1.usage();
                return;
            } else {
                try {
                    System.out.println(twitterLogic(parser));
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }  catch (ParameterException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
