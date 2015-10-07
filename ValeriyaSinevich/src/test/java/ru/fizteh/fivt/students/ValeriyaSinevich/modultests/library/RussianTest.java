package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import twitter4j.QueryResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RussianTest {

    @Before
    public void setUp() throws Exception {
        Russian translator = mock(Russian.class);

        when(translator.translateMinutes()).thenReturn(statuses);

        when(twitter.search(argThat(hasProperty("query", equalTo("java")))))
                .thenReturn(queryResult);

        QueryResult emptyQueryResult = mock(QueryResult.class);
        when(emptyQueryResult.getTweets()).thenReturn(Collections.emptyList());

        when(twitter.search(argThat(hasProperty("query", not(equalTo("java"))))))
                .thenReturn(emptyQueryResult);
    }

    @Test
    public void testTranslateMinutes() throws Exception {

    }

    @Test
    public void testTranslateHours() throws Exception {

    }

    @Test
    public void testTranslateDays() throws Exception {

    }
}