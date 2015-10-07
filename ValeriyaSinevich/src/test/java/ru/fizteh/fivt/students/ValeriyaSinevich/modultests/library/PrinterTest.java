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
    @Before
    public void setUp() throws Exception {
        ParametersParser parser = mock(ParametersParser.class);
        Status tweet = mock(Status.class);
        List<Status> = mock(List.class);

        when(translator.translateMinutes()).thenReturn(statuses);

        when(twitter.search(argThat(hasProperty("query", equalTo("java")))))
                .thenReturn(queryResult);

        QueryResult emptyQueryResult = mock(QueryResult.class);
        when(emptyQueryResult.getTweets()).thenReturn(Collections.emptyList());

        when(twitter.search(argThat(hasProperty("query", not(equalTo("java"))))))
                .thenReturn(emptyQueryResult);
    }



    @Test
    public void testPrintTweet() throws Exception {

    }
}