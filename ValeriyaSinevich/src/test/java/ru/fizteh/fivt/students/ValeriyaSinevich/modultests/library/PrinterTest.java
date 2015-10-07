package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import twitter4j.QueryResult;
import twitter4j.Status;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PrinterTest {
    private static final int TEST_COUNT = 5;

    @Before
    public void setUp() throws Exception {
        ParametersParser parser = mock(ParametersParser.class);

        for (int i = 0; i < TEST_COUNT; ++i) {
            Status tweet = mock(Status.class);
            when(tweet.getUser()).thenReturn()
        }


        when(twitter.search(argThat(hasProperty("query", equalTo("java")))))
                .thenReturn(queryResult);

    }



    @Test
    public void testPrintTweet() throws Exception {

    }
}