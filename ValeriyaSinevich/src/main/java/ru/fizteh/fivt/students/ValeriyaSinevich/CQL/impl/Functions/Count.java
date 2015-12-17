package ru.fizteh.fivt.students.ValeriyaSinevich.CQL.impl.Functions;

import java.util.List;

/**
 * Created by alexander on 13.12.15.
 */
public class Count<T> implements AggregationFunction<T, Integer> {
    @Override
    public Integer applyOnList(List<T> list) {
        return list.size();
    }

    @Override
    public Integer apply(T t) {
        return null;
    }
}
