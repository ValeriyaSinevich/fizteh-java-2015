package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import com.beust.jcommander.JCommander;
import twitter4j.*;

import java.util.function.Consumer;

public class Logic {
    private JCommander p1;
    private ParametersParser parser;
    private Querist querist;

    public Logic(JCommander p, ParametersParser pars, Querist quer) {
        p1 = p;
        parser = pars;
        querist = quer;
    }

    public final void mainLogic(Consumer<String> consumer) throws Exception {
            if (parser.isHelp()) {
                p1.usage();
            } else {
                try {
                    twitterLogic(consumer);
                } catch (Exception ex) {
                    throw new Exception(ex.getMessage());
                }
            }
    }


    public final void twitterLogic(Consumer<String> consumer) throws Exception {
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
            twitter4j.TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
            querist.getTwitterStream(twitterStream, coordinates, parser, parser.getSubstring(), consumer);
        } else {
            try {
                //ConfigurationBuilder cb = new ConfigurationBuilder();
                //cb.setJSONStoreEnabled(true);

                //Twitter twitter = new TwitterFactory(cb.build()).getInstance();
                Twitter twitter = TwitterFactory.getSingleton();
                twitter4j.GeoLocation loc = new twitter4j.GeoLocation(coordinates[0], coordinates[1]);
                querist.getTweets(twitter, coordinates, parser, parser.getSubstring(), consumer);
            } catch (GetTweetException ex) {
                throw new GetTweetException(ex.getMessage());
            }
        }
    }

}
