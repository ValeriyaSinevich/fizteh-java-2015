package ru.fizteh.fivt.students.ValeriyaSinevich.CQL.impl.Functions;

import java.util.List;
import java.util.function.Function;

public interface AggregationFunction<T, E> extends Function<T, E> {
        E applyOnList(List<T> list);
}

