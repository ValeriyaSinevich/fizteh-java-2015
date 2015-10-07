package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.*;
import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.GetTweetException;
import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.LocationException;
import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.ParametersParser;
import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.PropertiesException;
import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.Querist;

public class TwitterStream {

    public static void main(String[] args) {
        ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.ParametersParser parser = new ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.ParametersParser();
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
