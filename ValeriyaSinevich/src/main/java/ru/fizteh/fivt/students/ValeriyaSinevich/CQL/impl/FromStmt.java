package ru.fizteh.fivt.students.ValeriyaSinevich.CQL.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class FromStmt<T> {
    private List<T> elements;

    private FromStmt(Iterable<T> iterable) {
        elements = StreamSupport.stream(iterable.spliterator(), true)
                .collect(Collectors.toList());
    }

    public static <T> FromStmt<T> from(Iterable<T> iterable) {
        return new FromStmt<T>(iterable);
        //throw new UnsupportedOperationException();
    }

    public <R> SelectStmt<T, R> select(Class<R> resultClass,
                                       Function<T, ?> constructorFunctions) {
        return new SelectStmt<>(elements, resultClass, false,
                constructorFunctions);
    }

    public <R> SelectStmt<T, R> selectDistinct(Class<R> resultClass,
                                               Function<T, ?> constructorFunctions) {
        return new SelectStmt<>(elements, resultClass, true,
                constructorFunctions);
    }
}
