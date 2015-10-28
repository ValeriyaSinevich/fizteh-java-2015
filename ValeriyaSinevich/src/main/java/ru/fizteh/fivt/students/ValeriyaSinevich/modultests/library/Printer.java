package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;


import twitter4j.Status;

public class Printer {
    public static String printTweet(Status tweet, Now now, ParametersParser parser, boolean stream, String substring) {
        if (!substring.equals("")) {
            if (!(tweet.getText().contains(substring))) {
                return "";
            }
        }
        boolean isHide = parser.isHide();

        StringBuilder result = new StringBuilder();

        if (!tweet.isRetweet() || !isHide) {
            if (!stream) {
                result.append(TimeFormatter.formatTime(tweet.getCreatedAt(), now) + " ");
            }
            result.append("@"
                    + tweet.getUser().getName());
            if (tweet.isRetweet()) {
                String text = tweet.getText();
                String[] parts = text.split("RT");
                parts = parts[1].split(":");
                result.append(" retweeted"
                        + parts[0]);
                for (int i = 1; i < parts.length; ++i) {
                    result.append(":" + parts[i]);
                }
            } else {
                result.append(" : "
                        + tweet.getText());
            }
            if (tweet.getRetweetCount() > 0) {
                result.append(" retweeted " + Integer.toString(tweet.getRetweetCount()) + " times");
            }
        }
        return result.toString();
    }

}
