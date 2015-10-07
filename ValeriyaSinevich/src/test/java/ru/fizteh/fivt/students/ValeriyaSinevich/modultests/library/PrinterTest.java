package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import twitter4j.QueryResult;
import twitter4j.Status;

import java.io.FileReader;
import java.io.InputStream;
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

    public static org.json.JSONObject tweetFromFile(int i) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("/tweetExamples.json"));
            JSONArray tweets = (JSONArray)obj;
            return tweets.getJSONObject(i);
        } catch (Exception e) {
            System.out.println("knagkge");
        }
    }

    @Before
    public void setUp() throws Exception {
        ParametersParser parser = mock(ParametersParser.class);

        for (int i = 0; i < TEST_COUNT; ++i) {
            Status tweet = mock(Status.class);
            String user = tweetFromFile(i).getJSONObject("user").getString("screen_name");
            when(tweet.getUser().getName()).thenReturn(user);

            String CreatedAt = tweetFromFile(i).getString("created_at");
            when(tweet.getCreatedAt().toString()).thenReturn(CreatedAt);

            Boolean isRetweet = Boolean.parseBoolean(tweetFromFile(i).getString("retweeted"));
            when(tweet.isRetweet()).thenReturn(isRetweet);

            int retweeted = Integer.parseInt(tweetFromFile(i).getString("retweet_count"));
            when(tweet.isRetweeted()).thenReturn(retweeted > 0);
            when(tweet.getRetweetCount()).thenReturn(retweeted);
        }


    }



    @Test
    public void testPrintTweet() throws Exception {

    }
}