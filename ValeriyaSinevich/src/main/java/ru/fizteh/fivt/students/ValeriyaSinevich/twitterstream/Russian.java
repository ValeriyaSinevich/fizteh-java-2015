package ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream;

public class Russian {
    public static final int DECADE = 10;
    public static final int DECADE_TWO = 20;
    public static final int CHECK = 100;
    public static final int EXCEPTION = 11;
    public static final int SECOND_FORM_BOUND = 4;
    public static final int FIRST_FORM_BOUND = 1;

    public static String translateMinutes(long diff) {
        if ((DECADE < diff % CHECK && diff % CHECK < DECADE_TWO)
                || diff % DECADE > SECOND_FORM_BOUND) {
            return " минут";
        } else if (diff % DECADE == FIRST_FORM_BOUND && diff % CHECK != EXCEPTION) {
            return " минутy";
        } else {
            return " минуты";
        }
    }

    public static String translateHours(long diff) {
        if ((DECADE < diff % CHECK && diff % CHECK < DECADE_TWO)
                || diff % DECADE > SECOND_FORM_BOUND) {
            return " часов";
        } else if (diff % DECADE == FIRST_FORM_BOUND && diff % CHECK != EXCEPTION) {
            return " час";
        } else {
            return " часа";
        }
    }

    public static String translateDays(long diff) {
        if ((DECADE < diff % CHECK && diff % CHECK < DECADE_TWO)
                || diff % DECADE > SECOND_FORM_BOUND) {
            return " дней";
        } else if (diff % DECADE == FIRST_FORM_BOUND && diff % CHECK != EXCEPTION) {
            return " день";
        } else {
            return " дня";
        }
    }

}
