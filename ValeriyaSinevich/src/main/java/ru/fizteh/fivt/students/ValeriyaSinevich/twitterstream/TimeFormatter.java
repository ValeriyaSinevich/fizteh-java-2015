package ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatter {

    private static final int DAYS = 365;
    private static final int MINUTES = 60;
    private static  final int SECONDS = 60;

    private static final int YEAR = 1;
    private static final int  DAY = 0;
    private static  final int HOUR = 2;
    private static final int MINUTE = 3;
    private static final int SECOND = 4;



    public static String formatTime(Date time) {
        DateFormat dateFormat = new SimpleDateFormat("DDD YYYY HH mm ss");
        Date date = new Date();
        String timeNow = dateFormat.format(date);
        String timeTweet = dateFormat.format(time);
        String[] dayTimeNow = timeNow.split(" ");
        String[] dayTimeTweet = timeTweet.split(" ");

        if (Integer.parseInt(dayTimeNow[YEAR]) == Integer.parseInt(dayTimeTweet[YEAR])
                && Integer.parseInt(dayTimeNow[DAY]) == Integer.parseInt(dayTimeTweet[DAY])) {
            int seconds = (Integer.parseInt(dayTimeNow[HOUR])
                    - Integer.parseInt(dayTimeTweet[HOUR])) * MINUTES * SECONDS
                    + (Integer.parseInt(dayTimeNow[MINUTE])
                    - Integer.parseInt(dayTimeTweet[MINUTE])) * SECONDS
                    + (Integer.parseInt(dayTimeNow[SECOND])
                    - Integer.parseInt(dayTimeTweet[SECOND]));
                if (seconds <= 2 * MINUTES) {
                    return "Just now";
                } else if (seconds <= MINUTES * SECONDS) {
                    return "An hour ago";
                } else {
                    return Integer.toString(Integer.parseInt(dayTimeNow[HOUR])
                            - Integer.parseInt(dayTimeTweet[HOUR]))
                            + " hours ago";
                }
            } else if (Integer.parseInt(dayTimeNow[YEAR]) == Integer.parseInt(dayTimeTweet[YEAR])
                && Integer.parseInt(dayTimeNow[DAY]) - 1 == Integer.parseInt(dayTimeTweet[DAY])) {
                return "yesterday";
            } else {
            int days = (Integer.parseInt(dayTimeNow[YEAR]) - Integer.parseInt(dayTimeTweet[1])) * DAYS
                    + (Integer.parseInt(dayTimeNow[DAY]) - Integer.parseInt(dayTimeTweet[DAY]));
            return Integer.toString(days) + "days ago";
        }
    }

}


























































