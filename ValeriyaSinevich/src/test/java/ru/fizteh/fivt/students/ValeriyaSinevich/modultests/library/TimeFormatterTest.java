package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TimeFormatterTest {

    private static final int TEST_COUNT = 5;

    @Test
    public void testFormatTime() throws Exception {

        Now now = mock(Now.class);
        String str = "2015-10-07 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        when(now.getTime()).thenReturn(dateTime);

        List<Date> dates = new ArrayList<Date>(0);
        Date date;
        DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss yyyy");

        List<String> ans = new ArrayList<String>(0);

        str = "Thu Oct 07 12:29:23 2015";//just now
        date =  df.parse(str);
        dates.add(date);
        ans.add("Just now");

        str = "Thu Oct 07 12:14:23 2015";//15 minutes ago
        date =  df.parse(str);
        dates.add(date);
        ans.add("15 минут назад");

        str = "Thu Oct 07 11:15:23 2015";
        date =  df.parse(str);
        dates.add(date);
        ans.add("an hour ago");

        str = "Thu Oct 07 10:28:23 2015";//2 hours ago
        date =  df.parse(str);
        dates.add(date);
        ans.add("2 часа назад");

        str = "Thu Oct 06 12:28:23 2015";// yesterday
        date =  df.parse(str);
        dates.add(date);
        ans.add("yesterday");

        str = "Thu Oct 01 12:32:23 2015";//6 days ago
        date =  df.parse(str);
        dates.add(date);
        ans.add("6 дней назад");


        for (int i = 0; i < TEST_COUNT; ++i) {
            Date tTime = dates.get(i);
            String result
                    = TimeFormatter.formatTime(tTime, now);
            assertThat(result, is(ans.get(i)));
        }

        //verify(Russian.translateMinutes());
    }
}