package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class TwitterStream {

    public static void main(String[] args) {
        ParametersParser parser = new ParametersParser();
        Querist querist = new Querist();
        try {
            JCommander p1 = new JCommander(parser, args);
            Logic logic = new Logic(p1, parser, querist);
            try {
               // List<String> tweets = new LinkedList<>();
                logic.mainLogic(System.out::println);
                /*ListIterator<String> listIterator = tweets.listIterator();
                while (true) {
                    while (listIterator.hasNext()) {
                        System.out.println(listIterator.next());
                    }
                }*/
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } catch (ParameterException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
