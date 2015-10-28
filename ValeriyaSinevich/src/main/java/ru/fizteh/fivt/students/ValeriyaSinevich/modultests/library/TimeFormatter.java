package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeFormatter {


    public static String formatTime(Date time, Now now) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String timeTweet = dateFormat.format(time);

        LocalDateTime tNow = now.getTime();
        LocalDateTime tTweet = LocalDateTime.parse(timeTweet, format);

        if (ChronoUnit.DAYS.between(tTweet, tNow) < 1) {
            if (ChronoUnit.HOURS.between(tTweet, tNow) < 1) {
                if (ChronoUnit.MINUTES.between(tTweet, tNow) < 2) {
                    return "Just now";
                } else {
                    long timeUnitsAgo = ChronoUnit.MINUTES.between(tTweet, tNow);
                    return Long.toString(timeUnitsAgo)
                            + ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.Russian.translate(timeUnitsAgo, "m") + " назад";
                }
            } else if (ChronoUnit.HOURS.between(tTweet, tNow) < 2) {
                return "an hour ago";
            } else {
                long timeUnitsAgo = ChronoUnit.HOURS.between(tTweet, tNow);
                return  Long.toString(timeUnitsAgo)
                        + ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.Russian.translate(timeUnitsAgo, "h") + " назад";
            }
        } else if (ChronoUnit.DAYS.between(tTweet, tNow) < 2) {
            return "yesterday";
        } else {
            long timeUnitsAgo = ChronoUnit.DAYS.between(tTweet, tNow);
            return Long.toString(timeUnitsAgo)
                    + ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.Russian.translate(timeUnitsAgo, "d") + " назад";
        }
    }
}
