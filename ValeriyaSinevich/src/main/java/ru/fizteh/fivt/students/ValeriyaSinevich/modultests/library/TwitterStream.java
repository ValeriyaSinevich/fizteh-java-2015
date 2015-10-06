package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import com.beust.jcommander.JCommander;
import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.*;
import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.LocationException;
import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.ParametersParser;
import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.PropertiesException;
import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.Querist;

public class TwitterStream {

    public static void main(String[] args) {
        ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.ParametersParser parser = new ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.ParametersParser();
        JCommander p1 = new JCommander(parser, args);

        boolean help = parser.isHelp();

        if (help) {
            p1.usage();
            return;
        }
        double[] coordinates;

        if (parser.getPlace().equals("") || parser.getPlace().equals("nearby")) {
            try {
                coordinates = ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.Querist.findCoordinatesByIp();
            } catch (ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.LocationException ex) {
                System.err.println(ex.getMessage());
                return;
            }
        } else {
            try {
                coordinates = ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.Querist.findCoordinates(parser.getPlace());
            } catch (ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.LocationException | ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.PropertiesException ex) {
                System.err.println(ex.getMessage());
                return;
            }
        }
        if (parser.isStream()) {
            ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.Querist.getTwitterStream(coordinates, parser, parser.getSubstring());
        } else {
            try {
                ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.Querist.getTweets(coordinates, parser, parser.getSubstring());
            } catch (GetTweetException ex) {
                System.err.println(ex.getMessage());
                return;
            }
        }
    }
}
