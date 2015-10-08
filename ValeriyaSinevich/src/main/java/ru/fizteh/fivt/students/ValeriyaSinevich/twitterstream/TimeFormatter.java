package ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;


public class TimeFormatter {

    public static String formatTime(Date time) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String timeTweet = dateFormat.format(time);

        LocalDateTime tNow = LocalDateTime.now();
        LocalDateTime tTweet = LocalDateTime.parse(timeTweet, format);

        if (ChronoUnit.DAYS.between(tTweet, tNow) < 1) {
            if (ChronoUnit.HOURS.between(tTweet, tNow) < 1) {
                if (ChronoUnit.MINUTES.between(tTweet, tNow) < 2) {
                    return "Just now";
                } else {
                    long timeUnitsAgo = ChronoUnit.MINUTES.between(tTweet, tNow);
                    return Long.toString(timeUnitsAgo) + Russian.translateMinutes(timeUnitsAgo) + " назад";
                }
            } else if (ChronoUnit.HOURS.between(tTweet, tNow) < 2) {
                return "an hour ago";
            } else {
                long timeUnitsAgo = ChronoUnit.HOURS.between(tTweet, tNow);
                return  Long.toString(timeUnitsAgo) + Russian.translateHours(timeUnitsAgo) + " назад";
            }
        } else if (ChronoUnit.DAYS.between(tTweet, tNow) < 2) {
            return "yesterday";
        } else {
            long timeUnitsAgo = ChronoUnit.DAYS.between(tTweet, tNow);
            return Long.toString(timeUnitsAgo) + Russian.translateDays(timeUnitsAgo) + " назад";
        }
    }
}
