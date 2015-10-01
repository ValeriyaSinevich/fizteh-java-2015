package ru.fizteh.fivt.students.ValeriyaSinevich.twitterstream;

//import org.apache.http.client.methods.HttpPost;

import twitter4j.*;

import java.io.ByteArrayOutputStream;

import java.net.URL;
import java.net.URLConnection;

import java.util.List;


public class Querist {

    private static final int MAXCOUNT = 3;
    private static final int MAX_BYTE_STREAM = 1024;

    public static double[][] createBox(double lat, double lon) {
        GeoLocation point = GeoLocation.fromDegrees(lat, lon);
        final double radius = 6371.01;
        final double distance = 5;
        GeoLocation[] borders = point.boundingCoordinates(distance, radius);
        double[][] boundingBox = new double[2][2];
        boundingBox[0][0] = borders[0].getLongitudeInDegrees();
        boundingBox[0][1] = borders[0].getLatitudeInDegrees();
        boundingBox[1][0] = borders[1].getLongitudeInDegrees();
        boundingBox[1][1] = borders[1].getLatitudeInDegrees();
        return boundingBox;
    }

    public static double[] findCoordinatesByIp() {
        double lat = 0;
        double lon = 0;
            try {
                URL geoIpUrl = new URL("http://ipinfo.io/json");
                URLConnection conn = geoIpUrl.openConnection();
                ByteArrayOutputStream output = new ByteArrayOutputStream(MAX_BYTE_STREAM);
                org.apache.commons.io.IOUtils.copy(conn.getInputStream(), output);
                output.close();
                String result = output.toString();
                //System.out.println(result);
                twitter4j.JSONObject myGeoLocation = new twitter4j.JSONObject(result);

                /*String myGeoLocationAddr = myGeoLocation.getString("City")
                        + ", "
                        + myGeoLocation.getString("region")
                        + ", "
                        + myGeoLocation.getString("country");*/
                String[] loc = myGeoLocation.getString("loc").split(",");
                System.out.printf(loc[0]);
                lat = Double.parseDouble(loc[0]);
                lon = Double.parseDouble(loc[1]);

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        double[] coordinates = new double[2];
        coordinates[0] = lat;
        coordinates[1] = lon;
        return coordinates;
    }

    public static double[] findCoordinates(String name) {
        double[] pair = new double[2];

        if (name.equals("")) {
            pair = findCoordinatesByIp();
        } else {
            String query = "https://maps.googleapis.com/maps/api/geocode/json?address=";
            String key = "&key=AIzaSyB-_tiO6Z9cJusdLmgQoJ_GOAS7lYy3UHU";
            String finalQuery = query + name + key;
            //org.apache.http.client.HttpClient httpClient = HttpClientBuilder.create().build();

            try {
                URL url = new URL(finalQuery);
                URLConnection conn = url.openConnection();
                ByteArrayOutputStream output = new ByteArrayOutputStream(MAX_BYTE_STREAM);
                org.apache.commons.io.IOUtils.copy(conn.getInputStream(), output);
                output.close();
                String result = output.toString();
                //System.out.println(result);
                twitter4j.JSONObject geoData = new twitter4j.JSONObject(result);
                if (geoData.getString("status").equals("OK")) {
                    twitter4j.JSONArray places = geoData.getJSONArray("results");

                    twitter4j.JSONObject place = places.getJSONObject(0);
                    twitter4j.JSONObject geometry = place.getJSONObject("geometry");
                    twitter4j.JSONObject location = geometry.getJSONObject("location");
                    double lat = Double.parseDouble(location.getString("lat"));
                    double lon = Double.parseDouble(location.getString("lng"));
                    pair[0] = lat;
                    pair[1] = lon;
                    return pair;
                } else {
                    System.err.println("can't find coordinates");
                }

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        return pair;
    }

    public static void getTwitterStream(double[] coordinates, ParametersParser parser, String substring) {
        FilterQuery tweetFilterQuery = new FilterQuery();
        double[][] boundingBox = createBox(coordinates[0], coordinates[1]);
        tweetFilterQuery.locations(boundingBox);
        twitter4j.TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        StatusAdapter listener = new StatusAdapter() {

            @Override
            public void onStatus(Status status) {
                Printer.printTweet(status, parser, true, substring);
            }

            @Override
            public void onException(Exception ex) {
                System.err.println("Internet connection is not available");
            }
        };

        twitterStream.addListener(listener);
        twitterStream.filter(tweetFilterQuery);
    }

    public static void getTweets(double[] coordinates, ParametersParser parser, String substring) {
        Twitter twitter = TwitterFactory.getSingleton();

        String radiusUnit = "km";
        final double distance = 5;

        int count = MAXCOUNT;

        while (count > 0) {
            try {
                twitter4j.GeoLocation loc = new twitter4j.GeoLocation(coordinates[0], coordinates[1]);
                QueryResult result = twitter.search(new Query().geoCode(loc, distance, radiusUnit));
                List<Status> tweets = result.getTweets();
                if (tweets.size() == 0) {
                    System.err.println("no tweets on the given arguments");
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
                System.err.println("Failed to search tweets: " + te.getMessage());
                --count;
            }
        }

        if (count  == 0) {
            System.err.println("Internet connection is not available");
        }
    }


}
