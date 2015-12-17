package ru.fizteh.fivt.students.ValeriyaSinevich.CQL.impl.Functions;


import java.util.List;
import java.util.function.Function;


public class Avg<T> implements AggregationFunction<T, Double> {

    private Function<T, ? extends Number> converter;

    public Avg(Function<T, ? extends Number> converter) {
        this.converter = converter;
    }

    @Override
    public Double applyOnList(List<T> list) {
        Double result = 0d;
        for (T element : list) {
            result += converter.apply(element).doubleValue();
        }
        result /= list.size();
        return result;
    }

    @Override
    public Double apply(T t) {
        return null;
    }
}
