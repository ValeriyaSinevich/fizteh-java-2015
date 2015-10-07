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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PrinterTest {
    private static final int TEST_COUNT = 8;

    public org.json.JSONObject tweetFromFile(int i) {
        JSONParser parser = new JSONParser();
        try {
            try (InputStream inputStream
                         = Thread.currentThread().getContextClassLoader().getResourceAsStream("/tweetExamples.json")) {
                String theString = IOUtils.toString(inputStream, "UTF-8");
                Object obj = parser.parse(theString);
                JSONArray tweets = (JSONArray)obj;
                return tweets.getJSONObject(i);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }



    @Test
    public void testPrintTweet() throws Exception {
        ParametersParser parser = mock(ParametersParser.class);
        Status tweet = mock(Status.class);

       boolean isRetweet;

        for (int i = 0; i < TEST_COUNT; ++i) {

            switch (i) {
                case 0:
                    String user = tweetFromFile(0).getJSONObject("user").getString("screen_name");
                    when(tweet.getUser().getName()).thenReturn(user);

                    String CreatedAt = tweetFromFile(0).getString("created_at");
                    when(tweet.getCreatedAt().toString()).thenReturn(CreatedAt);

                    isRetweet = tweetFromFile(0).has("retweet_status");
                    when(tweet.isRetweet()).thenReturn(isRetweet);

                    int retweeted = Integer.parseInt(tweetFromFile(0).getString("retweet_count"));
                    when(tweet.isRetweeted()).thenReturn(retweeted > 0);
                    when(tweet.getRetweetCount()).thenReturn(retweeted);

                    String result = Printer.printTweet(tweet, parser, false, "nosuchword");
                    assertThat(result, is(""));

                    //verify(TimeFormatter.formatTime(tweet.getCreatedAt(), now));
                    break;

                case 1:
                    user = tweetFromFile(0).getJSONObject("user").getString("screen_name");
                    when(tweet.getUser().getName()).thenReturn(user);

                    CreatedAt = tweetFromFile(0).getString("created_at");
                    when(tweet.getCreatedAt().toString()).thenReturn(CreatedAt);

                    isRetweet = tweetFromFile(0).has("retweet_status");
                    when(tweet.isRetweet()).thenReturn(isRetweet);

                    retweeted = Integer.parseInt(tweetFromFile(0).getString("retweeted"));
                    when(tweet.isRetweeted()).thenReturn(retweeted > 0);
                    when(tweet.getRetweetCount()).thenReturn(retweeted);

                    result = Printer.printTweet(tweet, parser, false, "University");
                    assertThat(result, is(""));

                    //verify(TimeFormatter.formatTime(tweet.getCreatedAt()));
                    break;

                case 2:
                    user = tweetFromFile(0).getJSONObject("user").getString("screen_name");
                    when(tweet.getUser().getName()).thenReturn(user);

                    CreatedAt = tweetFromFile(0).getString("created_at");
                    when(tweet.getCreatedAt().toString()).thenReturn(CreatedAt);

                    isRetweet = tweetFromFile(0).has("retweet_status");
                    when(tweet.isRetweet()).thenReturn(isRetweet);

                    retweeted = Integer.parseInt(tweetFromFile(0).getString("retweeted"));
                    when(tweet.isRetweeted()).thenReturn(retweeted > 0);
                    when(tweet.getRetweetCount()).thenReturn(retweeted);

                    result = Printer.printTweet(tweet, parser, true, "");
                    assertThat(result, is(""));

                   // verify(TimeFormatter.formatTime(tweet.getCreatedAt()));
                    break;

                case 3:
                    user = tweetFromFile(0).getJSONObject("user").getString("screen_name");
                    when(tweet.getUser().getName()).thenReturn(user);

                    CreatedAt = tweetFromFile(0).getString("created_at");
                    when(tweet.getCreatedAt().toString()).thenReturn(CreatedAt);

                    isRetweet = tweetFromFile(0).has("retweet_status");
                    when(tweet.isRetweet()).thenReturn(isRetweet);

                    retweeted = Integer.parseInt(tweetFromFile(0).getString("retweeted"));
                    when(tweet.isRetweeted()).thenReturn(retweeted > 0);
                    when(tweet.getRetweetCount()).thenReturn(retweeted);

                    result = Printer.printTweet(tweet, parser, false, "");
                    assertThat(result, is(""));

                    //verify(TimeFormatter.formatTime(tweet.getCreatedAt()));
                    break;

                case 4:

                    user = tweetFromFile(2).getJSONObject("user").getString("screen_name");
                    when(tweet.getUser().getName()).thenReturn(user);

                    CreatedAt = tweetFromFile(2).getString("created_at");
                    when(tweet.getCreatedAt().toString()).thenReturn(CreatedAt);

                    isRetweet = tweetFromFile(0).has("retweet_status");
                    when(tweet.isRetweet()).thenReturn(isRetweet);

                    retweeted = Integer.parseInt(tweetFromFile(2).getString("retweeted"));
                    when(tweet.isRetweeted()).thenReturn(retweeted > 0);
                    when(tweet.getRetweetCount()).thenReturn(retweeted);

                    result = Printer.printTweet(tweet, parser, false, "");
                    assertThat(result, is(""));
                   // verify(TimeFormatter.formatTime(tweet.getCreatedAt()));
                    break;

                case 5:

                    user = tweetFromFile(0).getJSONObject("user").getString("screen_name");
                    when(tweet.getUser().getName()).thenReturn(user);

                    CreatedAt = tweetFromFile(0).getString("created_at");
                    when(tweet.getCreatedAt().toString()).thenReturn(CreatedAt);

                    isRetweet = Boolean.parseBoolean(tweetFromFile(0).getString("retweet_status"));
                    when(tweet.isRetweet()).thenReturn(isRetweet);

                    retweeted = Integer.parseInt(tweetFromFile(0).getString("retweeted"));
                    when(tweet.isRetweeted()).thenReturn(retweeted > 0);
                    when(tweet.getRetweetCount()).thenReturn(retweeted);

                    result = Printer.printTweet(tweet, parser, false, "");
                    assertThat(result, is(""));

                    //verify(TimeFormatter.formatTime(tweet.getCreatedAt()));
                    break;
                case 6:

                    user = tweetFromFile(1).getJSONObject("user").getString("screen_name");
                    when(tweet.getUser().getName()).thenReturn(user);

                    CreatedAt = tweetFromFile(1).getString("created_at");
                    when(tweet.getCreatedAt().toString()).thenReturn(CreatedAt);

                    isRetweet = Boolean.parseBoolean(tweetFromFile(1).getString("retweet_status"));
                    when(tweet.isRetweet()).thenReturn(isRetweet);

                    retweeted = Integer.parseInt(tweetFromFile(1).getString("retweeted"));
                    when(tweet.isRetweeted()).thenReturn(retweeted > 0);
                    when(tweet.getRetweetCount()).thenReturn(retweeted);

                    result = Printer.printTweet(tweet, parser, false, "");
                    assertThat(result, is(""));

                   // verify(TimeFormatter.formatTime(tweet.getCreatedAt()));
                    break;

                case 7:

                    user = tweetFromFile(0).getJSONObject("user").getString("screen_name");
                    when(tweet.getUser().getName()).thenReturn(user);

                    CreatedAt = tweetFromFile(0).getString("created_at");
                    when(tweet.getCreatedAt().toString()).thenReturn(CreatedAt);

                    isRetweet = Boolean.parseBoolean(tweetFromFile(0).getString("retweet_status"));
                    when(tweet.isRetweet()).thenReturn(isRetweet);

                    retweeted = Integer.parseInt(tweetFromFile(0).getString("retweeted"));
                    when(tweet.isRetweeted()).thenReturn(retweeted > 0);
                    when(tweet.getRetweetCount()).thenReturn(retweeted);

                    result = Printer.printTweet(tweet, parser, false, "");
                    assertThat(result, is(""));

                    //verify(TimeFormatter.formatTime(tweet.getCreatedAt()));
                    break;

                default:
                    break;
            }
        }

    }
}