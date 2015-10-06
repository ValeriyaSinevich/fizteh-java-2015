package ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream;


import twitter4j.Status;

public class Printer {
    public static void printTweet(Status tweet, ParametersParser parser, boolean stream, String substring) {
        if (!substring.equals("")) {
            if (!(tweet.getText().contains(substring))) {
                return;
            }
        }
        boolean isHide = parser.isHide();

        StringBuilder result = new StringBuilder();

        if (!tweet.isRetweet() || !isHide) {
            if (!stream) {
                result.append(TimeFormatter.formatTime(tweet.getCreatedAt()) + " ");
            }
            result.append("@"
                    + tweet.getUser().getName());
            if (tweet.isRetweet()) {
                String text = tweet.getText();
                String[] parts = text.split("RT");
                parts = parts[1].split(":");
                result.append(" retweeted "
                        + parts[0]
                        + ": "
                        + parts[1]);
            } else {
                result.append(" : "
                        + tweet.getText());
            }
            if (tweet.isRetweeted()) {
                result.append(Integer.toString(tweet.getRetweetCount()));
            }
        }
        System.out.println(result);
    }

}
