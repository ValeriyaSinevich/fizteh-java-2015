package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.fizteh.fivt.students.akormushin.moduletests.library.TwitterServiceImpl;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class QueristTest {

    @Mock
    private Twitter twitter;

    @Mock
    private TwitterStream twitterStream;

    @InjectMocks
    private TwitterServiceImpl twitterService;

    public static List<Status> statuses;

    @BeforeClass
    public static void loadSampleData() {
        statuses = Twitter4jTestUtils.tweetsFromJson("/search-java-response.json");
    }

    @Before
    public void setUp() throws Exception {
        QueryResult queryResult = mock(QueryResult.class);
        when(queryResult.getTweets()).thenReturn(statuses);

        when(twitter.search(argThat(hasProperty("query", equalTo("java")))))
                .thenReturn(queryResult);

        QueryResult emptyQueryResult = mock(QueryResult.class);
        when(emptyQueryResult.getTweets()).thenReturn(Collections.emptyList());


    }

    private QueryResult mock(Class<QueryResult> queryResultClass) {
        return null;
    }


    @org.junit.Test
    public void testCreateBox() throws Exception {

    }

    @org.junit.Test
    public void testFindCoordinatesByIp() throws Exception {

    }

    @org.junit.Test
    public void testFindCoordinates() throws Exception {

    }

    @org.junit.Test
    public void testGetTwitterStream() throws Exception {

    }

    @org.junit.Test
    public void testGetTweets() throws Exception {

    }
}