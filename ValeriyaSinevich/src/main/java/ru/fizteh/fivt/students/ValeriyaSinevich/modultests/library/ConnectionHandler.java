package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;


import twitter4j.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.net.URLConnection;

public class ConnectionHandler {
    private static final int MAX_BYTE_STREAM = 1024;


    public JSONObject returnJsonString(String url) throws LocationException {
        JSONObject myGeoLocation;
        try {
            URL geoIpUrl = new URL(url);
            URLConnection conn = geoIpUrl.openConnection();
            try (ByteArrayOutputStream output = new ByteArrayOutputStream(MAX_BYTE_STREAM)) {
                org.apache.commons.io.IOUtils.copy(conn.getInputStream(), output);
                output.close();
                String result = output.toString();
                myGeoLocation = new JSONObject(result);
            } catch (Exception ex) {
                throw new LocationException("Can't find coordinates");
            }
        } catch (Exception ex) {
            throw new LocationException("Can't find coordinates");
        }
        return myGeoLocation;
    }
}
