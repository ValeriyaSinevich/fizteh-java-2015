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

        String result = new String();

        if (!tweet.isRetweet() || !isHide) {
            if (!stream) {
                result.concat(TimeFormatter.formatTime(tweet.getCreatedAt()) + " ");
            }
            result.concat("@"
                    + tweet.getUser().getName());
            if (tweet.isRetweet()){
                String text = tweet.getText();
                String[] parts = text.split("RT");
                parts = parts[1].split(":");
                result.concat(" retweeted "
                        + parts[0]
                        + ": "
                        + parts[1]);
            } else {
                result.concat(" : "
                        + tweet.getText());
            }
            if (tweet.isRetweeted()) {
                result.concat(Integer.toString(tweet.getRetweetCount()));
            }
        }
        System.out.println();
    }

}
