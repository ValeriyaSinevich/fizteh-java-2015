package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import java.util.List;

public class TwitterStream {

    public static void main(String[] args) {
        ParametersParser parser = new ParametersParser();
        Querist querist = new Querist();
        try {
            JCommander p1 = new JCommander(parser, args);
            Logic logic = new Logic(p1, parser, querist);
            try {
                List<String> tweets = logic.mainLogic(args);
                for (int i = 0; i < tweets.size(); ++i) {
                    System.out.println(tweets.get(i));
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } catch (ParameterException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
