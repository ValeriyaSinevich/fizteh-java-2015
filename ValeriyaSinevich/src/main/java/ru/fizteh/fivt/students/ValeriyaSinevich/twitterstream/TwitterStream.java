package ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class TwitterStream {

    public static void main(String[] args) {
        ParametersParser parser = new ParametersParser();
        try {
            JCommander p1 = new JCommander(parser, args);

            boolean help = parser.isHelp();

            if (help) {
                p1.usage();
                return;
            }
            double[] coordinates;

            if (parser.getPlace().equals("") || parser.getPlace().equals("nearby")) {
                try {
                    coordinates = Querist.findCoordinatesByIp();
                } catch (LocationException ex) {
                    System.err.println(ex.getMessage());
                    return;
                }
            } else {
                try {
                    coordinates = Querist.findCoordinates(parser.getPlace());
                } catch (LocationException | PropertiesException ex) {
                    System.err.println(ex.getMessage());
                    return;
                }
            }
            if (parser.isStream()) {
                Querist.getTwitterStream(coordinates, parser, parser.getSubstring());
            } else {
                try {
                    Querist.getTweets(coordinates, parser, parser.getSubstring());
                } catch (GetTweetException ex) {
                    System.err.println(ex.getMessage());
                    return;
                }
            }
        }  catch (ParameterException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
