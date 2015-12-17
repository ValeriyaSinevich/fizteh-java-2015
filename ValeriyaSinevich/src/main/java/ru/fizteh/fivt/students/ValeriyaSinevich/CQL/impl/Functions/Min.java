package ru.fizteh.fivt.students.ValeriyaSinevich.CQL.impl.Functions;


import java.util.List;
import java.util.function.Function;


public class Min<T, E extends Comparable<E>> implements AggregationFunction<T, E> {

    private Function<T, E> converter;

    public Min(Function<T, E> converter) {
        this.converter = converter;
    }

    @Override
    public E apply(T t) {
        return null;
    }

    @Override
    public E applyOnList(List<T> list) {
        E result = null;
        for (T element : list) {
            if (result == null) {
                result = converter.apply(element);
            } else {
                E currentResult = converter.apply(element);
                if (currentResult.compareTo(result) < 0) {
                    result = currentResult;
                }
            }
        }
        return result;
    }
}

