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

    private GeoLocation() {
    }

    /**
     * @param latitude the latitude, in degrees.
     * @param longitude the longitude, in degrees.
     */
    public static ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation fromDegrees(double latitude, double longitude) {
        ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation result = new ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation();
        result.radLat = Math.toRadians(latitude);
        result.radLon = Math.toRadians(longitude);
        result.degLat = latitude;
        result.degLon = longitude;
        result.checkBounds();
        return result;
    }

    /**
     * @param latitude the latitude, in radians.
     * @param longitude the longitude, in radians.
     */
    public static ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation fromRadians(double latitude, double longitude) {
        ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation result = new ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation();
        result.radLat = latitude;
        result.radLon = longitude;
        result.degLat = Math.toDegrees(latitude);
        result.degLon = Math.toDegrees(longitude);
        result.checkBounds();
        return result;
    }

    private void checkBounds() {
        if (radLat < MIN_LAT || radLat > MAX_LAT
                || radLon < MIN_LON || radLon > MAX_LON) {
            throw new IllegalArgumentException();
        }
    }

    public ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation[] boundingCoordinates(double distance, double radius) {
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

        return new ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library.GeoLocation[]{fromRadians(minLat, minLon),
                fromRadians(maxLat, maxLon)};
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
