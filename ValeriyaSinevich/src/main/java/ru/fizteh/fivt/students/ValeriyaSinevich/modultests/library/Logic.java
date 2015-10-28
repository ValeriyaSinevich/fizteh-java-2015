package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import com.beust.jcommander.JCommander;
import twitter4j.*;

import java.util.LinkedList;
import java.util.List;

public class Logic {
    private JCommander p1;
    private ParametersParser parser;
    private Querist querist;

    public Logic(JCommander p, ParametersParser pars, Querist quer) {
        p1 = p;
        parser = pars;
        querist = quer;
    }

    public final List<String> mainLogic(String[] args) throws Exception {
        List<String> tweetsToPrint = new LinkedList<>();
            if (parser.isHelp()) {
                p1.usage();
                return tweetsToPrint;
            } else {
                try {
                    return twitterLogic();
                } catch (Exception ex) {
                    throw new Exception(ex.getMessage());
                }
            }
    }


    public final List<String> twitterLogic() throws Exception {
        List<String> tweetsToPrint = new LinkedList<>();
        double[] coordinates;
        if (parser.getPlace().equals("") || parser.getPlace().equals("nearby")) {
            try {
                //String geoUrl = "http://ipinfo.io/json";
                ConnectionHandler ch = new ConnectionHandler();
                coordinates = querist.findCoordinatesByIp(ch);
            } catch (LocationException ex) {
                throw new LocationException(ex.getMessage());
            }
        } else {
            try {
                PropertiesLoader prop = new PropertiesLoader();
                ConnectionHandler ch = new ConnectionHandler();
                coordinates = querist.findCoordinates(prop, ch, parser.getPlace());
            } catch (LocationException | PropertiesException ex) {
                throw new Exception(ex.getMessage());
            }
        }
        if (parser.isStream()) {
            querist.getTwitterStream(coordinates, parser, parser.getSubstring());
        } else {
            try {
                //ConfigurationBuilder cb = new ConfigurationBuilder();
                //cb.setJSONStoreEnabled(true);

                //Twitter twitter = new TwitterFactory(cb.build()).getInstance();
                Twitter twitter = TwitterFactory.getSingleton();
                String radiusUnit = "km";
                final double distance = 5;
                twitter4j.GeoLocation loc = new twitter4j.GeoLocation(coordinates[0], coordinates[1]);
                return querist.getTweets(twitter, coordinates, parser, parser.getSubstring());
            } catch (GetTweetException ex) {
                throw new GetTweetException(ex.getMessage());
            }
        }
        return tweetsToPrint;
    }

}
