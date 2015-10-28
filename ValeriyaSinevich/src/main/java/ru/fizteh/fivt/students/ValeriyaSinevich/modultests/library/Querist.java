package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import twitter4j.*;
import twitter4j.TwitterStream;

import twitter4j.json.DataObjectFactory;


import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class Querist {
    private static final int MAXCOUNT = 3;

    public double[][] createBox(double lat, double lon) {
        ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation geo =
                new ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation();
        geo.fromDegrees(lat, lon);
        final double radius = 6371.01;
        final double distance = 5;
        ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation[] borders
                = geo.boundingCoordinates(distance, radius);
        double[][] boundingBox = new double[2][2];
        boundingBox[0][0] = borders[0].getLongitudeInDegrees();
        boundingBox[0][1] = borders[0].getLatitudeInDegrees();
        boundingBox[1][0] = borders[1].getLongitudeInDegrees();
        boundingBox[1][1] = borders[1].getLatitudeInDegrees();
        return boundingBox;
    }

    public double[] findCoordinatesByIp(ConnectionHandler ch) throws LocationException {
        double lat;
        double lon;
        String[] loc;
        String geoUrl = "http://ipinfo.io/json";
        try {
            JSONObject myGeoLocation = ch.returnJsonString(geoUrl);
            try {
                loc = myGeoLocation.getString("loc").split(",");
            } catch (Exception ex) {
                throw new LocationException("can't find coordinates");
            }
            lat = Double.parseDouble(loc[0]);
            lon = Double.parseDouble(loc[1]);
        } catch (LocationException ex) {
            throw ex;
        }
        double[] coordinates = new double[]{lat, lon};
        return coordinates;
    }

    public double[] findCoordinates(PropertiesLoader prop, ConnectionHandler ch, String name) throws LocationException,
            ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.PropertiesException {
        double lat, lon;
        String key;

        if (name.equals("")) {
            return findCoordinatesByIp(ch);
        } else {
            try {
                Properties googleKeys = new Properties();
                key = "&key=" + prop.loadKey(googleKeys).split("\"")[1];
            } catch (ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.PropertiesException e) {
                throw new
                        ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.PropertiesException(e.getMessage());
            }
            JSONObject geoData;
            String query = "https://maps.googleapis.com/maps/api/geocode/json?address=";
            String finalQuery = query + name + key;
            try {
                geoData = ch.returnJsonString(finalQuery);
            } catch (LocationException ex) {
                throw ex;
            }
            try {
                if (geoData.getString("status").equals("OK")) {
                    JSONArray places = geoData.getJSONArray("results");
                    JSONObject place = places.getJSONObject(0);
                    JSONObject geometry = place.getJSONObject("geometry");
                    JSONObject location = geometry.getJSONObject("location");
                    lat = Double.parseDouble(location.getString("lat"));
                    lon = Double.parseDouble(location.getString("lng"));
                    } else {
                        throw new LocationException("can't find coordinates");
                    }
            } catch (Exception ex) {
                throw new LocationException(ex.getMessage());
            }
        }
        return new double[]{lat, lon};
    }

    public void getTwitterStream(double[] coordinates, ParametersParser parser, String substring) {
        FilterQuery tweetFilterQuery = new FilterQuery();
        double[][] boundingBox = createBox(coordinates[0], coordinates[1]);
        tweetFilterQuery.locations(boundingBox);
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        StatusAdapter listener = new StatusAdapter() {

            @Override
            public void onStatus(Status status) {
                Now now = new Now();
                Printer.printTweet(status, now, parser, true, substring);
            }

            @Override
            public void onException(Exception ex) {
                System.err.println("Internet connection is not available" + ex.getMessage());
                // throw new GetTweetException("Internet connection is not available" + ex.getMessage());
            }
        };

        twitterStream.addListener(listener);
        twitterStream.filter(tweetFilterQuery);
    }

    public List<String> tweetsDealer(List<Status> tweets, ParametersParser parser, String substring)
            throws GetTweetException {
        if (tweets.size() == 0) {
            throw new GetTweetException("no tweets on the given arguments");
        }
        int limits = parser.getNumber();
        int i;
        List<String> tweetsToPrint = new LinkedList<>();
        //JSONArray halva = new JSONArray();
        for (i = 0; i < Integer.min(limits, tweets.size()); ++i) {
            Status tweet = tweets.get(i);
            //String json = DataObjectFactory.getRawJSON(tweet);
            //JSONObject obj = new JSONObject();
            //try {
              //  obj = new JSONObject(json);
           // } catch (Exception e) {

           // }
           // halva.put(obj);
            //System.out.println(prettyJsonString);
            Now now = new Now();
            tweetsToPrint.add(Printer.printTweet(tweet, now, parser, false, substring));
        }
       // String str = halva.toString();
       // Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //JsonParser jp = new JsonParser();
        //JsonElement je = jp.parse(str);
        //String prettyJsonString = gson.toJson(je);

        return tweetsToPrint;
    }

    public List<String> getTweets(Twitter twitter, double[] coordinates,
                                  ParametersParser parser, String substring)
            throws GetTweetException {

        List<String> tweetsToPrint = new LinkedList<>();

        int count = MAXCOUNT;

        while (count > 0) {
            try {
                String radiusUnit = "km";
                final double distance = 5;
                twitter4j.GeoLocation loc = new twitter4j.GeoLocation(coordinates[0], coordinates[1]);
                QueryResult result = twitter.search(new Query().geoCode(loc, distance, radiusUnit));
                List<Status> tweets = result.getTweets();
                tweetsToPrint = tweetsDealer(tweets, parser, substring);
                return tweetsToPrint;
            } catch (TwitterException te) {
                --count;
                if (count  == 0) {
                    throw new GetTweetException("Internet connection is not available");
                }
            }
        }
        return tweetsToPrint;
    }

}
