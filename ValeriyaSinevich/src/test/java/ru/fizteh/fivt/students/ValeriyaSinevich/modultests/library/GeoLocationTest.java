package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class GeoLocationTest {

    @Test
    public void testFromDegrees() throws Exception {
        GeoLocation geo = new GeoLocation();
        double[] coordinates = {55.7558, 37.6173};
        geo.fromDegrees(coordinates[0], coordinates[1]);
        double a = geo.getLatitudeInDegrees();
        double b = geo.getLongitudeInDegrees();
        assertThat(a, is(coordinates[0]));
        assertThat(b, is(coordinates[1]));
    }

    @Test
    public void testFromRadians() throws Exception {
        GeoLocation geo = new GeoLocation();
        double[] coordinatesRad = {0.9723379359480191, 0.6551510792893198};
        double[] coordinatesDeg = {55.71085999028327, 37.537391786718786};
        geo.fromRadians(coordinatesRad[0], coordinatesRad[1]);
        double a = geo.getLatitudeInDegrees();
        double b = geo.getLongitudeInDegrees();
        assertThat(a, is(coordinatesDeg[0]));
        assertThat(b, is(coordinatesDeg[1]));
    }

    @Test
    public void testBoundingCoordinates() throws Exception {
        final double radius = 6371.01;
        final double distance = 5;
        double[] coordinates = {55.7558, 37.6173};
        double[] maxCoordinates = {37.69720816001288, 55.80076600971674};
        double[] minCoordinates = {37.537391839987116, 55.71083399028326};

        GeoLocation geo = new GeoLocation();
        geo.fromDegrees(coordinates[0], coordinates[1]);
        GeoLocation[] borders = geo.boundingCoordinates(distance, radius);
        double[][] boundingBox = new double[2][2];
        boundingBox[0][0] = borders[0].getLongitudeInDegrees();
        boundingBox[0][1] = borders[0].getLatitudeInDegrees();
        boundingBox[1][0] = borders[1].getLongitudeInDegrees();
        boundingBox[1][1] = borders[1].getLatitudeInDegrees();

        assertThat(boundingBox[0][0], is(minCoordinates[0]));
        assertThat(boundingBox[0][1], is(minCoordinates[1]));
        assertThat(boundingBox[1][0], is(maxCoordinates[0]));
        assertThat(boundingBox[1][1], is(maxCoordinates[1]));

    }

    @Test
    public void testGetLatitudeInDegrees() throws Exception {
        GeoLocation geo = new GeoLocation();
        double[] coordinates = {55.7558, 37.6173};
        geo.fromDegrees(coordinates[0], coordinates[1]);
        double a = geo.getLatitudeInDegrees();
        assertThat(a, is(coordinates[0]));
    }

    @Test
    public void testGetLongitudeInDegrees() throws Exception {
        GeoLocation geo = new GeoLocation();
        double[] coordinates = {55.7558, 37.6173};
        geo.fromDegrees(coordinates[0], coordinates[1]);
        double b = geo.getLongitudeInDegrees();
        assertThat(b, is(coordinates[1]));
    }
}