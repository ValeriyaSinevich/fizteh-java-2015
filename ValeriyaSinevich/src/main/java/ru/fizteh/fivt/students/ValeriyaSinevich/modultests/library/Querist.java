package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

//import org.apache.http.client.methods.HttpPost;

import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.*;
import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.GetTweetException;
import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.ParametersParser;
import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.Printer;
import ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.PropertiesException;
import twitter4j.*;
import twitter4j.GeoLocation;
import twitter4j.TwitterStream;

import java.io.ByteArrayOutputStream;

import java.net.URL;
import java.net.URLConnection;

import java.util.List;

public class Querist {
    private static final int MAXCOUNT = 3;
    private static final int MAX_BYTE_STREAM = 1024;

    public static double[][] createBox(double lat, double lon) {
        ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation point = ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation.fromDegrees(lat, lon);
        final double radius = 6371.01;
        final double distance = 5;
        ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.GeoLocation[] borders = point.boundingCoordinates(distance, radius);
        double[][] boundingBox = new double[2][2];
        boundingBox[0][0] = borders[0].getLongitudeInDegrees();
        boundingBox[0][1] = borders[0].getLatitudeInDegrees();
        boundingBox[1][0] = borders[1].getLongitudeInDegrees();
        boundingBox[1][1] = borders[1].getLatitudeInDegrees();
        return boundingBox;
    }

    public static double[] findCoordinatesByIp() throws LocationException {
        double lat;
        double lon;
            try {
                URL geoIpUrl = new URL("http://ipinfo.io/json");
                URLConnection conn = geoIpUrl.openConnection();
                try (ByteArrayOutputStream output = new ByteArrayOutputStream(MAX_BYTE_STREAM)) {
                    org.apache.commons.io.IOUtils.copy(conn.getInputStream(), output);
                    output.close();
                    String result = output.toString();
                    //System.out.println(result);
                    JSONObject myGeoLocation = new JSONObject(result);

                    /*String myGeoLocationAddr = myGeoLocation.getString("City")
                            + ", "
                            + myGeoLocation.getString("region")
                            + ", "
                            + myGeoLocation.getString("country");*/
                    String[] loc = myGeoLocation.getString("loc").split(",");
                    //System.out.printf(loc[0]);
                    lat = Double.parseDouble(loc[0]);
                    lon = Double.parseDouble(loc[1]);
                } catch (Exception ex) {
                    throw new LocationException(ex.getMessage());
                }

            } catch (Exception ex) {
                throw new LocationException(ex.getMessage());
            }
        double[] coordinates = new double[]{lat, lon};
        return coordinates;
    }

    public static double[] findCoordinates(String name) throws LocationException, PropertiesException {
        double lat, lon;
        String key;

        if (name.equals("")) {
            return findCoordinatesByIp();
        } else {
            PropertiesLoader prop = new PropertiesLoader();
            try {
                key = "&key=" + prop.loadKey().split("\"")[1];
            } catch (PropertiesException e) {
                throw new PropertiesException(e.getMessage());
            }
            String query = "https://maps.googleapis.com/maps/api/geocode/json?address=";
            String finalQuery = query + name + key;
            try {
                URL url = new URL(finalQuery);
                URLConnection conn = url.openConnection();
                try (ByteArrayOutputStream output = new ByteArrayOutputStream(MAX_BYTE_STREAM)) {
                    org.apache.commons.io.IOUtils.copy(conn.getInputStream(), output);
                    output.close();
                    String result = output.toString();
                    //System.out.println(result);
                    JSONObject geoData = new JSONObject(result);
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

            } catch (Exception ex) {
                throw new LocationException(ex.getMessage());
            }
        }
        return new double[]{lat, lon};
    }

    public static void getTwitterStream(double[] coordinates, ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.ParametersParser parser, String substring) {
        FilterQuery tweetFilterQuery = new FilterQuery();
        double[][] boundingBox = createBox(coordinates[0], coordinates[1]);
        tweetFilterQuery.locations(boundingBox);
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        StatusAdapter listener = new StatusAdapter() {

            @Override
            public void onStatus(Status status) {
                Printer.printTweet(status, parser, true, substring);
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

    public static void getTweets(double[] coordinates, ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream.ParametersParser parser, String substring)
            throws GetTweetException {
        Twitter twitter = TwitterFactory.getSingleton();

        String radiusUnit = "km";
        final double distance = 5;

        int count = MAXCOUNT;

        while (count > 0) {
            try {
                GeoLocation loc = new GeoLocation(coordinates[0], coordinates[1]);
                QueryResult result = twitter.search(new Query().geoCode(loc, distance, radiusUnit));
                List<Status> tweets = result.getTweets();
                if (tweets.size() == 0) {
                    throw new GetTweetException("no tweets on the given arguments");
                }


                int limits = parser.getNumber();
                int i;
                for (i = 0; i < Integer.min(limits, tweets.size()); ++i) {
                    Status tweet = tweets.get(i);
                    Printer.printTweet(tweet, parser, false, substring);
                }
                if (i == Integer.min(limits, tweets.size())) {
                    break;
                }

            } catch (TwitterException te) {
                te.printStackTrace();
                --count;
                //throw new GetTweetException("Failed to search tweets: " + te.getMessage());
            }
        }

        if (count  == 0) {
            throw new GetTweetException("Internet connection is not available");
        }
    }


}
