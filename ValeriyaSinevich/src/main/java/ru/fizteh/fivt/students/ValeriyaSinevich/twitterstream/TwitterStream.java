package ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream;

import com.beust.jcommander.JCommander;

public class TwitterStream {

    public static void main(String[] args) {
        ParametersParser parser = new ParametersParser();
        JCommander p1 = new JCommander(parser, args);

        boolean help = parser.isHelp();

        if (help) {
            p1.usage();
            return;
        }
            double[] coordinates;

            if (parser.getPlace().equals("") || parser.getPlace().equals("nearby")) {
                coordinates = Querist.findCoordinatesByIp();
            } else {
                coordinates = Querist.findCoordinates(parser.getPlace());
            }
            if (parser.isStream()) {
                Querist.getTwitterStream(coordinates, parser, parser.getSubstring());
            } else {
                Querist.getTweets(coordinates, parser, parser.getSubstring());
            }
    }
}
