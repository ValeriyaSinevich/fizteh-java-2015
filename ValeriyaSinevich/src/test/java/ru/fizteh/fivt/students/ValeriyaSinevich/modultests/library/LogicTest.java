package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import com.beust.jcommander.JCommander;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;


import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class LogicTest {

    @Rule
    public ExpectedException thrown= ExpectedException.none();


    @Test
    public void testMainLogic() throws Exception {
        ParametersParser parser = mock(ParametersParser.class);
        JCommander jc = mock(JCommander.class);
        Querist querist = new Querist();

        when(parser.getPlace()).thenReturn("Moscow");
        when(parser.isStream()).thenReturn(false);
        when(parser.getSubstring()).thenReturn("");

        List<String> tweets = new LinkedList<>();

        when(parser.isHelp()).thenReturn(true);
        Logic logic = new Logic(jc, parser, querist);
        logic.mainLogic(tweets::add);
        assertThat(tweets, hasSize(0));
        verify(jc).usage();

        when(parser.isHelp()).thenReturn(false);
        Logic logic2 = spy(logic);
        logic2.mainLogic(tweets::add);
        verify(logic2).twitterLogic(tweets::add);
    }


    @Test
    public void testTwitterLogic() throws Exception {
        String geoUrl = "http://ipinfo.io/json";
        ConnectionHandler ch = new ConnectionHandler();
        Twitter twitter = TwitterFactory.getSingleton();;

        ParametersParser parser = mock(ParametersParser.class);
        Querist querist = mock(Querist.class);
        JCommander jc = new JCommander();
        PropertiesLoader prop = new PropertiesLoader();

        List<String> tweets = new LinkedList<>();

        double[] coordinates = {55.755826, 37.6173};
        when(querist.findCoordinates(any(PropertiesLoader.class), any(ConnectionHandler.class), anyString())).thenReturn(coordinates);
        when(querist.findCoordinatesByIp(any(ConnectionHandler.class))).thenReturn(coordinates);
        when(parser.getPlace()).thenReturn("Moscow");
        when(parser.isStream()).thenReturn(true);
        when(parser.getSubstring()).thenReturn("");
        Logic logic = new Logic(jc, parser, querist);
        logic.twitterLogic(tweets::add);
        verify(querist).findCoordinates(any(PropertiesLoader.class), any(ConnectionHandler.class), anyString());
        verify(querist).getTwitterStream(any(twitter4j.TwitterStream.class), any(), any(ParametersParser.class), anyString(), any());

        when(parser.isStream()).thenReturn(false);
        logic = new Logic(jc, parser, querist);
        logic.twitterLogic(tweets::add);
        verify(querist).getTweets(any(twitter4j.Twitter.class), any(), any(ParametersParser.class), anyString(), any());

        when(querist.findCoordinates(any(), any(), any())).thenThrow(new LocationException("can't find Location"));
        logic = new Logic(jc, parser, querist);
        thrown.expect(Exception.class);
        logic.twitterLogic(tweets::add);

        when(querist.findCoordinates(any(), any(), any())).thenThrow(new PropertiesException("can't load key"));
        logic = new Logic(jc, parser, querist);
        thrown.expect(Exception.class);
        logic.twitterLogic(tweets::add);

        when(querist.findCoordinates(any(), any(), any())).thenReturn(coordinates);
        doThrow(new GetTweetException("can't get tweets")).when(querist).getTweets(any(twitter4j.Twitter.class),
                coordinates, any(ParametersParser.class), anyString(), tweets::add);
        logic = new Logic(jc, parser, querist);
        thrown.expect(GetTweetException.class);
        logic.twitterLogic(tweets::add);

        when(parser.getPlace()).thenReturn("");
        logic = new Logic(jc, parser, querist);
        logic.twitterLogic(tweets::add);
        verify(querist).findCoordinatesByIp(any());
        verify(querist).getTweets(any(twitter4j.Twitter.class), any(), any(ParametersParser.class), anyString(), any());
    }
}