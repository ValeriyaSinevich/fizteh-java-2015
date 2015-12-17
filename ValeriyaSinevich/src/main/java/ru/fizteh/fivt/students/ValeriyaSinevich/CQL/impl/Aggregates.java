package ru.fizteh.fivt.students.ValeriyaSinevich.CQL.impl;



import ru.fizteh.fivt.students.ValeriyaSinevich.CQL.impl.Functions.Avg;
import ru.fizteh.fivt.students.ValeriyaSinevich.CQL.impl.Functions.Count;
import ru.fizteh.fivt.students.ValeriyaSinevich.CQL.impl.Functions.Max;
import ru.fizteh.fivt.students.ValeriyaSinevich.CQL.impl.Functions.Min;

import java.util.function.Function;


public class Aggregates {

    public static <C, T extends Comparable<T>> Function<C, T> max(Function<C, T> expression) {
        return new Max<>(expression);
    }

    public static <C, T extends Comparable<T>> Function<C, T> min(Function<C, T> expression) {
        return new Min<>(expression);
    }


    public static <C> Function<C, Integer> count(Function<C, ?> expression) {
        return new Count<C>();
    }

    public static <C> Function<C, Double> avg(Function<C, ? extends Number> expression) {
        return new Avg<>(expression);
    }

}
