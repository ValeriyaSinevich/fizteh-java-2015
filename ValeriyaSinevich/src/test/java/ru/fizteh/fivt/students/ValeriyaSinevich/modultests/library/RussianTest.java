package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RussianTest {

    private static final int TEST_COUNT = 6;

    @Test
    public void testTranslate() throws Exception {
        List<String> ans1 = new ArrayList<String>(0);
        List<String> ans2 = new ArrayList<String>(0);
        List<String> ans3 = new ArrayList<String>(0);


        long[] diff = {1, 22, 11, 14, 51, 66};

        ans1.add(" минуту");
        ans1.add(" минуты");
        ans1.add(" минут");
        ans1.add(" минут");
        ans1.add(" минуту");
        ans1.add(" минут");

        ans2.add(" час");
        ans2.add(" часа");
        ans2.add(" часов");
        ans2.add(" часов");
        ans2.add(" час");
        ans2.add(" часов");

        ans3.add(" день");
        ans3.add(" дня");
        ans3.add(" дней");
        ans3.add(" дней");
        ans3.add(" день");
        ans3.add(" дней");

        for (int i = 0; i < TEST_COUNT; ++i) {
            String result = Russian.translate(diff[i], "m");
            assertThat(result, is(ans1.get(i)));
        }
        for (int i = 0; i < TEST_COUNT; ++i) {
            String result = Russian.translate(diff[i], "h");
            assertThat(result, is(ans2.get(i)));
        }
        for (int i = 0; i < TEST_COUNT; ++i) {
            String result = Russian.translate(diff[i], "d");
            assertThat(result, is(ans3.get(i)));
        }
    }
}