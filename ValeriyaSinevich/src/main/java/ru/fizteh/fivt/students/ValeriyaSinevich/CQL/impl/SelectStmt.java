package ru.fizteh.fivt.students.ValeriyaSinevich.CQL.impl;


import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class SelectStmt<T, R> {
    private List<T> elements;
    private Class<R> resultClass;
    private Function<T, ?>[] constructorFunctions;
    private Function<T, ?> groupByFunction;

    private Predicate<T> wherePredicate;
    private Predicate<R> havingPredicate;

    // private HereComparator<R> hereComparator;

    private boolean distinct;

    // private int limitRange = Integer.MAX_VALUE;
    @SafeVarargs
    SelectStmt(List<T> givenElements,
               Class<R> givenResultClass,
               boolean givenDistinct,
               Function<T, ?>... newConstructorFunctions
    ) {
        elements = givenElements;
        resultClass = givenResultClass;
        constructorFunctions = newConstructorFunctions;
        distinct = givenDistinct;
    }

    public SelectStmt<T, R> groupBy(Function<T, ?> newGroupByFunction) {
        this.groupByFunction = newGroupByFunction;
        this.distinct = true;
        return this;
    }

    public SelectStmt<T, R> having(Predicate<R> predicate) {
        havingPredicate = predicate;
        return this;
    }

    public SelectStmt<T, R> where(Predicate<T> predicate) {
        wherePredicate = predicate;
        return this;
        //throw new UnsupportedOperationException();
    }

    public Iterable<R> execute() {
        Map<R, ArrayList<R>> groups = new HashMap<>();
        if (groupByFunction == null) {
            groupByFunction = Function.identity();
        }
        List<R> returnList = new LinkedList<>();
        for (T element : elements) {
            if (!wherePredicate.test(element))
                continue;

            if (!groups.containsKey(groupByFunction.apply(element))) {

            }



            Object[] args = new Object[constructorFunctions.length];
            Class[] argTypes = new Class[constructorFunctions.length];
            for (int i = 0; i < args.length; ++i) {
                args[i] = constructorFunctions[i].apply(element);
                argTypes[i] = args[i].getClass();
            }
            try {
                R resultElement = (R) resultClass.getConstructor(argTypes).newInstance(args);
                if (!distinct || !returnList.contains(resultElement))
                    returnList.add(resultElement);
            } catch (InstantiationException
                    | IllegalAccessException
                    | NoSuchMethodException | InvocationTargetException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
//        if (distinct) {
//            Set<R> returnListDistinct = new HashSet<>();
//            for (R e : returnList) {
//                returnListDistinct.add(e);
//            }
//            returnList.clear();
//            for (R e : returnListDistinct) {
//                returnList.add(e);
//            }
//        }
        return returnList;
    }
}


