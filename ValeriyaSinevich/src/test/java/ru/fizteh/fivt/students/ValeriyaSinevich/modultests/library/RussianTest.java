package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RussianTest {

    private static final int TEST_COUNT = 6;

    @Test
    public void testTranslateMinutes() throws Exception {
        List<String> ans = new ArrayList<String>(0);

        long[] diff = {1, 22, 11, 14, 51, 66};

        ans.add(" минуту");
        ans.add(" минуты");
        ans.add(" минут");
        ans.add(" минут");
        ans.add(" минуту");
        ans.add(" минут");

        for (int i = 0; i < TEST_COUNT; ++i) {
            String result = Russian.translateMinutes(diff[i]);
            assertThat(result, is(ans.get(i)));
        }
    }

    @Test
    public void testTranslateHours() throws Exception {
        List<String> ans = new ArrayList<String>(0);
        long[] diff = {1, 22, 11, 14, 51, 66};

        ans.add(" час");
        ans.add(" часа");
        ans.add(" часов");
        ans.add(" часов");
        ans.add(" час");
        ans.add(" часов");

        for (int i = 0; i < TEST_COUNT; ++i) {
            String result = Russian.translateHours(diff[i]);
            assertThat(result, is(ans.get(i)));
        }
    }

    @Test
    public void testTranslateDays() throws Exception {
        List<String> ans = new ArrayList<String>(0);
        long[] diff = {1, 22, 11, 14, 51, 66};

        ans.add(" день");
        ans.add(" дня");
        ans.add(" дней");
        ans.add(" дней");
        ans.add(" день");
        ans.add(" дней");

        for (int i = 0; i < TEST_COUNT; ++i) {
            String result = Russian.translateDays(diff[i]);
            assertThat(result, is(ans.get(i)));
        }
    }
}