package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

public final class GeoLocation {

    private double radLat;  // latitude in radians
    private double radLon;  // longitude in radians

    private double degLat;  // latitude in degrees
    private double degLon;  // longitude in degrees

    private static final double MIN_LAT = Math.toRadians(-90d);  // -PI/2
    private static final double MAX_LAT = Math.toRadians(90d);   //  PI/2
    private static final double MIN_LON = Math.toRadians(-180d); // -PI
    private static final double MAX_LON = Math.toRadians(180d);  //  PI

    public GeoLocation() {
    }

    /**
     * @param latitude the latitude, in degrees.
     * @param longitude the longitude, in degrees.
     */
    public void fromDegrees(double latitude, double longitude) {
        //ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation result
        // = new ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation();
        radLat = Math.toRadians(latitude);
        radLon = Math.toRadians(longitude);
        degLat = latitude;
        degLon = longitude;
        checkBounds();
    }

    /**
     * @param latitude the latitude, in radians.
     * @param longitude the longitude, in radians.
     */
    public void fromRadians(double latitude, double longitude) {
        //ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation result
        // = new ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation();
        radLat = latitude;
        radLon = longitude;
        degLat = Math.toDegrees(latitude);
        degLon = Math.toDegrees(longitude);
        checkBounds();
    }

    private void checkBounds() {
        if (radLat < MIN_LAT || radLat > MAX_LAT
                || radLon < MIN_LON || radLon > MAX_LON) {
            throw new IllegalArgumentException();
        }
    }

    public ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation[]
    boundingCoordinates(double distance, double radius) {
        if (radius < 0d || distance < 0d) {
            throw new IllegalArgumentException();
        }

        // angular distance in radians on a great circle
        double radDist = distance / radius;

        double minLat = radLat - radDist;
        double maxLat = radLat + radDist;

        double minLon, maxLon;
        if (minLat > MIN_LAT && maxLat < MAX_LAT) {
            double deltaLon = Math.asin(Math.sin(radDist)
                    / Math.cos(radLat));
            minLon = radLon - deltaLon;
            if (minLon < MIN_LON) {
                minLon += 2d * Math.PI;
            }
            maxLon = radLon + deltaLon;
            if (maxLon > MAX_LON) {
                maxLon -= 2d * Math.PI;
            }
        } else {
            // a pole is within the distance
            minLat = Math.max(minLat, MIN_LAT);
            maxLat = Math.min(maxLat, MAX_LAT);
            minLon = MIN_LON;
            maxLon = MAX_LON;
        }

        GeoLocation min = new GeoLocation();
        GeoLocation max = new GeoLocation();
        min.fromRadians(minLat, minLon);
        max.fromRadians(maxLat, maxLon);
        ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation[] bounds = {min, max};
        return bounds;
    }

    public double getLatitudeInDegrees() {
        return degLat;
    }

    /**
     * @return the longitude, in degrees.
     */
    public double getLongitudeInDegrees() {
        return degLon;
    }

}
