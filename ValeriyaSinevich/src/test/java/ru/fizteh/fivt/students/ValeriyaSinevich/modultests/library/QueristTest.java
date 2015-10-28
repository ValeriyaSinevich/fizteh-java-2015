package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;


import org.apache.commons.io.IOUtils;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import twitter4j.JSONObject;
import twitter4j.Status;
import twitter4j.Twitter;


import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import twitter4j.*;


public class QueristTest {

    public List<String> tweetsToPrintFromFile() {
        List<String> l = new LinkedList<>();
        try (InputStream inputStream
                     = this.getClass().getResourceAsStream("/mockedTweetsToPrint.json")) {
            String theString = IOUtils.toString(inputStream, "UTF-8");
            JSONObject s = new JSONObject(theString);
            JSONArray array = s.getJSONArray("tweets");
            for (int i = 0; i< array.length(); ++i) {
                String si = array.get(i).toString();
                l.add(si);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return l;
    }

    public JSONObject fromFile(boolean ip) {
        try {
            if (ip) {
                try (InputStream inputStream
                             = this.getClass().getResourceAsStream("/coordinatesByIp.json")) {
                    String theString = IOUtils.toString(inputStream, "UTF-8");
                    JSONObject s = new JSONObject(theString);
                    return s;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                try (InputStream inputStream
                             = this.getClass().getResourceAsStream("/coordinates.json")) {
                    String theString = IOUtils.toString(inputStream, "UTF-8");
                    JSONObject s = new JSONObject(theString);
                    return s;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void testCreateBox() throws Exception {
        double[][] boundingBox = new double[2][2];
        double[] coordinates = {55.7558, 37.6173};
        Querist querist = new Querist();

        double[][] bounds = querist.createBox(coordinates[0], coordinates[1]);
        boundingBox[0][0] = 37.537391839987116;
        boundingBox[0][1] = 55.71083399028326;
        boundingBox[1][0] = 37.69720816001288;
        boundingBox[1][1] = 55.80076600971674;

        assertThat(bounds[0][0], is(boundingBox[0][0]));
        assertThat(bounds[0][1], is(boundingBox[0][1]));
        assertThat(bounds[1][0], is(boundingBox[1][0]));
        assertThat(bounds[0][1], is(boundingBox[0][1]));
    }

    @Test
    public void testFindCoordinates() throws Exception {
        Querist querist = new Querist();
        ConnectionHandler ch = mock(ConnectionHandler.class);
        PropertiesLoader prop = mock(PropertiesLoader.class);
        Properties googleKeys = new Properties();
        when(prop.loadKey(googleKeys)).thenReturn("AIzaSyB-_tiO6Z9cJusdLmgQoJ_GOAS7lYy3UHU");

        String finalQuery = "https://maps.googleapis.com/maps/api/geocode/json?address=Moscow&key=AIzaSyB-_tiO6Z9cJusdLmgQoJ_GOAS7lYy3UHU";
        JSONObject s = fromFile(false);
        when(ch.returnJsonString(finalQuery)).thenReturn(s);
        double[] loc = querist.findCoordinates(prop, ch, "Moscow");
        double[] location = {56.009657, 37.9456611};
        assertThat(loc, is(location));

        when(ch.returnJsonString(finalQuery)).thenThrow(new LocationException("can't find coordinates"));
        thrown.expect(LocationException.class);
        querist.findCoordinates(prop, ch, "Moscow");

        when(ch.returnJsonString(finalQuery)).thenReturn(s);
        when(prop.loadKey(googleKeys)).thenThrow(PropertiesException.class);
        thrown.expect(PropertiesException.class);
        querist.findCoordinates(prop, ch, "Moscow");
    }

    @Test
    public void testFindCoordinatesByIp() throws Exception {
        Querist querist = new Querist();
        ConnectionHandler ch = mock(ConnectionHandler.class);
        JSONObject s = fromFile(true);
        when(ch.returnJsonString("http://ipinfo.io/json")).thenReturn(s);
        double[] loc = querist.findCoordinatesByIp(ch);
        double[] location = {55.9041, 37.5606};
        assertThat(loc, is(location));

        when(ch.returnJsonString("http://ipinfo.io/json")).thenThrow(new LocationException("can't find coordinates"));
        thrown.expect(LocationException.class);
        querist.findCoordinatesByIp(ch);
    }


    @Test
    public void testGetTwitterStream() throws Exception {
        twitter4j.TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        //Use ArgumentCaptor to remember argument between different stub invocations
        ArgumentCaptor<StatusListener> statusListener = ArgumentCaptor.forClass(StatusListener.class);
        List<Status> statuses = Twitter4jTestUtils.tweetsFromJson("/mockedTweets.json");
        //Mocking void method
        doNothing().when(twitterStream).addListener((StatusListener) statusListener.capture());
        doAnswer(i -> {
            statuses.forEach(s -> statusListener.getValue().onStatus(s));
            return null;
        }).when(twitterStream).filter(any(FilterQuery.class));

        ParametersParser parser = mock(ParametersParser.class);
        when(parser.isHide()).thenReturn(false);

        double[] coordinates = {55.755826, 37.6173};
        Querist querist = new Querist();
        List<String> tweetsToPrint = new LinkedList<>();
        querist.getTwitterStream(twitterStream, coordinates, parser, "", tweetsToPrint::add);
        assertThat(tweetsToPrint, hasSize(15));
        assertThat(tweetsToPrint,
                hasItem("10 часов назад @yulia cosmos : \"- Это водка? - " +
                                "слабо спросила Маргарита. Кот подпрыгнул на стуле от обиды. - Помилуйте, королева, " +
                                "-… https://t.co/xGlINIRfPL"
        ));
        verify(twitterStream).addListener((StatusListener) any(StatusAdapter.class));
        verify(twitterStream).filter(any(FilterQuery.class));
    }

    @Test
    public void testGetTweets() throws Exception {
        String radiusUnit = "km";
        final double distance = 5;
        double[] coordinates = {55.755826, 37.6173};
        List<Status> tweets;
        List <String> tweetsToPrint = new LinkedList<>();
        List <String> mockedTweetsToPrint = tweetsToPrintFromFile();
        Twitter twitter = mock(Twitter.class);
        Querist q = new Querist();
        Querist querist = spy(q);
        ParametersParser parser = mock(ParametersParser.class);
        when(parser.isHide()).thenReturn(false);
        when(parser.getNumber()).thenReturn(15);
        when(parser.getSubstring()).thenReturn("");
        tweets = Twitter4jTestUtils.tweetsFromJson("/mockedTweets.json");
        twitter4j.GeoLocation loc = new twitter4j.GeoLocation(coordinates[0], coordinates[1]);
        QueryResult queryResult = mock(QueryResult.class);
        when(twitter.search(new Query().geoCode(loc, distance, radiusUnit))).thenReturn(queryResult);
       //doReturn(mockedTweetsToPrint).when(querist).tweetsDealer(argThat(hasSize(15)));
        doReturn(mockedTweetsToPrint).when(querist).tweetsDealer(anyList(), parser, "", tweetsToPrint::add);

        when(queryResult.getTweets()).thenReturn(tweets);
        querist.getTweets(twitter, coordinates,
                parser, "", tweetsToPrint::add);
        assertThat(tweetsToPrint, hasSize(15));
        assertThat(tweetsToPrint, hasItem(
                "10 часа назад @Варя : Зато я в 500 злобных карт выиграла\uD83D\uDE08 Ну, я конечно пятая, после парней.. Но у девочек-то выиграла\uD83D\uDE02 @… https://t.co/14gxTXyNcr"));


        tweets = new LinkedList<>();
        when(queryResult.getTweets()).thenReturn(tweets);
        doThrow(GetTweetException.class).when(querist).tweetsDealer(anyList(), parser, "", anyObject());
        thrown.expect(GetTweetException.class);
        querist.getTweets(twitter, coordinates,
                parser, "", tweetsToPrint::add);

        when(twitter.search(new Query().geoCode(loc, distance, radiusUnit))).thenThrow(TwitterException.class);
        thrown.expect(GetTweetException.class);
        querist.getTweets(twitter, coordinates,
                parser, "",tweetsToPrint::add);

        //when(twitter.search(argThat(hasProperty("geocode"), equalTo("55.755826,37.6173,5.0km"))))
                //.thenReturn(queryResult);
    }

}