package ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream;

import com.beust.jcommander.JCommander;
import twitter4j.*;

public class TwitterStream {

    public static void main(String[] args) {
        ParametersParser parser = new ParametersParser();
        JCommander p1 = new JCommander(parser, args);

        boolean help = parser.isHelp();

        if (help) {
            p1.usage();
            return;
        }

        if (parser.getSubstring().equals("") && parser.getPlace().equals("")) {
            System.out.println("No parameters given, can't find everything in the world");
        } else {
            double[] coordinates = new double[2];

            if (!parser.getPlace().equals("")) {
                coordinates = Querist.findCoordinates(parser.getPlace());
            } else {
                coordinates = Querist.findCoordinatesByIp();
            }
            if (parser.isStream()) {
                Querist.getTwitterStream(coordinates, parser, parser.getSubstring());
            } else {
                Querist.getTweets(coordinates, parser, parser.getSubstring());
            }
        }

    }

    public static void printTweet(Status tweet, ParametersParser parser, boolean stream, String substring) {
        if (!substring.equals("")) {
            if (!(tweet.getText().contains(substring))) {
                return;
            }
        }
        boolean isHide = parser.isHide();
        if (!tweet.isRetweet()) {
            if (!stream) {
                System.out.println(TimeFormatter.formatTime(tweet.getCreatedAt())
                        + " @"
                        + tweet.getUser().getName()
                        + " : "
                        + tweet.getText()
                        + " ");
                if (tweet.isRetweeted()) {
                    System.out.println(tweet.getRetweetCount());
                }
            } else {
                System.out.println(" @"
                        + tweet.getUser().getName()
                        + " : "
                        + tweet.getText()
                        + " ");
                if (tweet.isRetweeted()) {
                    System.out.println(tweet.getRetweetCount());
                }
            }
        } else if (!isHide) {
            String text = tweet.getText();
            String[] parts = text.split("RT");
            parts = parts[1].split(":");

            if (!stream) {
                System.out.println(TimeFormatter.formatTime(tweet.getCreatedAt())
                        + " @"
                        + tweet.getUser().getName()
                        + " retweeted @"
                        + parts[0]
                        + " "
                        + parts[1]);

            } else {
                System.out.println(" @"
                        + tweet.getUser().getName()
                        + " retweeted @"
                        + parts[1]
                        + " "
                        + tweet.getText());
            }
        }
    }

}
