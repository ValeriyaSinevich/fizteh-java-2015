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
import twitter4j.User;

import java.io.FileReader;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
                         = this.getClass().getResourceAsStream("/tweetExamples.json")) {
                String theString = IOUtils.toString(inputStream, "UTF-8");
                JSONArray tweets = new JSONArray(theString);
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

        Date tTweet = mock(Date.class);
        DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss Z yyyy");

        User user = mock(User.class);

       boolean isRetweet;

        for (int i = 0; i < TEST_COUNT; ++i) {

            Now now = mock(Now.class);
            String str = "2015-10-07 12:30";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
            when(now.getTime()).thenReturn(dateTime);

            switch (i) {
                case 0:
                    isRetweet = tweetFromFile(0).has("retweeted_status");
                    when(tweet.isRetweet()).thenReturn(isRetweet);
                    when(parser.isHide()).thenReturn(false);

                    String CreatedAt = tweetFromFile(0).getString("created_at");
                    Date date =  df.parse(CreatedAt);
                    when(tweet.getCreatedAt()).thenReturn(date);

                    String usr = tweetFromFile(0).getJSONObject("user").getString("screen_name");
                    when(tweet.getUser()).thenReturn(user);
                    when(user.getName()).thenReturn(usr);

                    String text = tweetFromFile(0).getString("text");
                    when(tweet.getText()).thenReturn(text);

                    int retweeted = tweetFromFile(0).getInt("retweet_count");
                    when(tweet.isRetweeted()).thenReturn(retweeted > 0);
                    when(tweet.getRetweetCount()).thenReturn(retweeted);

                    String result = Printer.printTweet(tweet, now, parser, false, "nosuchword");
                    assertThat(result, is(""));

                    break;

                case 1:
                    CreatedAt = tweetFromFile(1).getString("created_at");
                    date =  df.parse(CreatedAt);
                    when(tweet.getCreatedAt()).thenReturn(date);

                    usr = tweetFromFile(1).getJSONObject("user").getString("screen_name");
                    when(tweet.getUser()).thenReturn(user);
                    when(user.getName()).thenReturn(usr);

                    text = tweetFromFile(1).getString("text");
                    when(tweet.getText()).thenReturn(text);

                    isRetweet = tweetFromFile(1).has("retweeted_status");
                    when(tweet.isRetweet()).thenReturn(isRetweet);
                    when(parser.isHide()).thenReturn(true);

                    retweeted = tweetFromFile(1).getInt("retweet_count");
                    when(tweet.isRetweeted()).thenReturn(retweeted > 0);
                    when(tweet.getRetweetCount()).thenReturn(retweeted);

                    result = Printer.printTweet(tweet, now, parser, false, "University");
                    assertThat(result, is(""));

                    //verify(TimeFormatter.formatTime(tweet.getCreatedAt()));
                    break;

                case 2:
                    CreatedAt = tweetFromFile(0).getString("created_at");
                    date =  df.parse(CreatedAt);
                    when(tweet.getCreatedAt()).thenReturn(date);

                    usr = tweetFromFile(0).getJSONObject("user").getString("screen_name");
                    when(tweet.getUser()).thenReturn(user);
                    when(user.getName()).thenReturn(usr);

                    text = tweetFromFile(0).getString("text");
                    when(tweet.getText()).thenReturn(text);

                    isRetweet = tweetFromFile(0).has("retweeted_status");
                    when(tweet.isRetweet()).thenReturn(isRetweet);
                    when(parser.isHide()).thenReturn(false);

                    retweeted = tweetFromFile(0).getInt("retweet_count");
                    when(tweet.isRetweeted()).thenReturn(retweeted > 0);
                    when(tweet.getRetweetCount()).thenReturn(retweeted);

                    result = Printer.printTweet(tweet, now, parser, true, "");
                    assertThat(result, is("@MariaZelinskay : #pizza @ Plekhanov Russian University of Economics https://t.co/PDxKWvYk5Q"));

                   // verify(TimeFormatter.formatTime(tweet.getCreatedAt()));
                    break;

                case 3:
                    CreatedAt = tweetFromFile(0).getString("created_at");
                    date =  df.parse(CreatedAt);
                    when(tweet.getCreatedAt()).thenReturn(date);

                    usr = tweetFromFile(0).getJSONObject("user").getString("screen_name");
                    when(tweet.getUser()).thenReturn(user);
                    when(user.getName()).thenReturn(usr);

                    text = tweetFromFile(0).getString("text");
                    when(tweet.getText()).thenReturn(text);

                    isRetweet = tweetFromFile(0).has("retweeted_status");
                    when(tweet.isRetweet()).thenReturn(isRetweet);
                    when(parser.isHide()).thenReturn(false);

                    retweeted = tweetFromFile(0).getInt("retweet_count");
                    when(tweet.isRetweeted()).thenReturn(retweeted > 0);
                    when(tweet.getRetweetCount()).thenReturn(retweeted);

                    result = Printer.printTweet(tweet, now, parser, false, "");
                    assertThat(result,
                            is("Just now @MariaZelinskay : #pizza @ Plekhanov Russian University of Economics https://t.co/PDxKWvYk5Q"));

                    //verify(TimeFormatter.formatTime(tweet.getCreatedAt()));
                    break;

                case 4:

                    CreatedAt = tweetFromFile(0).getString("created_at");
                    date =  df.parse(CreatedAt);
                    when(tweet.getCreatedAt()).thenReturn(date);

                    usr = tweetFromFile(0).getJSONObject("user").getString("screen_name");
                    when(tweet.getUser()).thenReturn(user);
                    when(user.getName()).thenReturn(usr);

                    text = tweetFromFile(0).getString("text");
                    when(tweet.getText()).thenReturn(text);

                    isRetweet = tweetFromFile(0).has("retweeted_status");
                    when(tweet.isRetweet()).thenReturn(isRetweet);
                    when(parser.isHide()).thenReturn(false);

                    retweeted = tweetFromFile(0).getInt("retweet_count");
                    when(tweet.isRetweeted()).thenReturn(retweeted > 0);
                    when(tweet.getRetweetCount()).thenReturn(retweeted);

                    result = Printer.printTweet(tweet, now, parser, false, "");
                    assertThat(result, is("Just now @MariaZelinskay : #pizza @ Plekhanov Russian University of Economics https://t.co/PDxKWvYk5Q"));
                   // verify(TimeFormatter.formatTime(tweet.getCreatedAt()));
                    break;

                case 5:

                    CreatedAt = tweetFromFile(1).getString("created_at");
                    date =  df.parse(CreatedAt);
                    when(tweet.getCreatedAt()).thenReturn(date);

                    usr = tweetFromFile(1).getJSONObject("user").getString("screen_name");
                    when(tweet.getUser()).thenReturn(user);
                    when(user.getName()).thenReturn(usr);

                    text = tweetFromFile(1).getString("text");
                    when(tweet.getText()).thenReturn(text);

                    isRetweet = tweetFromFile(1).has("retweeted_status");
                    when(tweet.isRetweet()).thenReturn(isRetweet);
                    when(parser.isHide()).thenReturn(false);

                    retweeted = tweetFromFile(1).getInt("retweet_count");
                    when(tweet.isRetweeted()).thenReturn(retweeted > 0);
                    when(tweet.getRetweetCount()).thenReturn(retweeted);

                    result = Printer.printTweet(tweet, now, parser, false, "");
                    assertThat(result, is("Just now @havenivor01 retweeted @mckeatingphoto: .@ellmot7 scoring at the kells end again  soon ! @OfficialHavenRl http://t.co/PqbaE0N250 retweeted 6 times"));

                    //verify(TimeFormatter.formatTime(tweet.getCreatedAt()));
                    break;
                case 6:

                    CreatedAt = tweetFromFile(0).getString("created_at");
                    date =  df.parse(CreatedAt);
                    when(tweet.getCreatedAt()).thenReturn(date);

                    usr = tweetFromFile(0).getJSONObject("user").getString("screen_name");
                    when(tweet.getUser()).thenReturn(user);
                    when(user.getName()).thenReturn(usr);

                    text = tweetFromFile(0).getString("text");
                    when(tweet.getText()).thenReturn(text);

                    isRetweet = tweetFromFile(0).has("retweeted_status");
                    when(tweet.isRetweet()).thenReturn(isRetweet);
                    when(parser.isHide()).thenReturn(false);

                    retweeted = tweetFromFile(0).getInt("retweet_count");
                    when(tweet.isRetweeted()).thenReturn(retweeted > 0);
                    when(tweet.getRetweetCount()).thenReturn(retweeted);

                    result = Printer.printTweet(tweet, now, parser, false, "");
                    assertThat(result, is("Just now @MariaZelinskay : #pizza @ Plekhanov Russian University of Economics https://t.co/PDxKWvYk5Q"));

                   // verify(TimeFormatter.formatTime(tweet.getCreatedAt()));
                    break;

                case 7:

                    CreatedAt = tweetFromFile(0).getString("created_at");
                    date =  df.parse(CreatedAt);
                    when(tweet.getCreatedAt()).thenReturn(date);

                    usr = tweetFromFile(0).getJSONObject("user").getString("screen_name");
                    when(tweet.getUser()).thenReturn(user);
                    when(user.getName()).thenReturn(usr);

                    text = tweetFromFile(0).getString("text");
                    when(tweet.getText()).thenReturn(text);

                    isRetweet = tweetFromFile(0).has("retweeted_status");
                    when(tweet.isRetweet()).thenReturn(isRetweet);
                    when(parser.isHide()).thenReturn(false);

                    retweeted = tweetFromFile(0).getInt("retweet_count");
                    when(tweet.isRetweeted()).thenReturn(retweeted > 0);
                    when(tweet.getRetweetCount()).thenReturn(retweeted);

                    result = Printer.printTweet(tweet, now, parser, false, "");
                    assertThat(result, is("Just now @MariaZelinskay : #pizza @ Plekhanov Russian University of Economics https://t.co/PDxKWvYk5Q"));

                    //verify(TimeFormatter.formatTime(tweet.getCreatedAt()));
                    break;

                default:
                    break;
            }
        }

    }
}