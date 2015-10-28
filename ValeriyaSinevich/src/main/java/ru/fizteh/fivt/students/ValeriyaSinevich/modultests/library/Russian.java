package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import java.util.ArrayList;
import java.util.HashMap;

public class Russian {
    public static final int DECADE = 10;
    public static final int DECADE_TWO = 20;
    public static final int CHECK = 100;
    public static final int EXCEPTION = 11;
    public static final int SECOND_FORM_BOUND = 4;
    public static final int FIRST_FORM_BOUND = 1;

    private  static HashMap<String, ArrayList<String>> russian = new HashMap<String, ArrayList<String>>();

    public static String translate(long diff, String unit) {
        ArrayList<String> add1 = new ArrayList<String>();
        ArrayList<String> add2 = new ArrayList<String>();
        ArrayList<String> add3 = new ArrayList<String>();
        add1.add(" минут");
        add1.add(" минуту");
        add1.add(" минуты");
        add2.add(" часов");
        add2.add(" час");
        add2.add(" часа");
        add3.add(" дней");
        add3.add(" день");
        add3.add(" дня");
        russian.put("m", add1);
        russian.put("h", add2);
        russian.put("d", add3);
        if ((DECADE < diff % CHECK && diff % CHECK < DECADE_TWO)
                || diff % DECADE > SECOND_FORM_BOUND) {
            return russian.get(unit).get(0);
        } else if (diff % DECADE == FIRST_FORM_BOUND && diff % CHECK != EXCEPTION) {
            return russian.get(unit).get(1);
        } else {
            return russian.get(unit).get(2);
        }
    }

}
