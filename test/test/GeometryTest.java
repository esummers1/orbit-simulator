package test;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import entities.Body;
import entities.Entity;
import physics.BearingVector;
import physics.Geometry;
import physics.Position;
import physics.XYVector;

public class GeometryTest {

    @Test
    public void testGetDistance() {

        // GIVEN two Positions five metres apart
        Position here = new Position(0, 0);
        Position there = new Position(3, 4);

        // WHEN I retrieve the distance between them
        // THEN I receive 5 metres
        assert(Geometry.getDistance(here, there) == 5);
    }

    @Test
    public void testCalculateBearing_UpperRightQuadrant() {

        // GIVEN I have two positions such that the latter is at a bearing of
        // 45 degrees from the first
        Position here = new Position(0, 0);
        Position there = new Position(1, -1);

        // WHEN I calculate the bearing from the former to the latter
        double bearing = Geometry.calculateBearing(here, there);
        bearing *= (180 / Math.PI);

        // THEN I receive approx. 45 degrees
        assert(bearing > 44 && bearing < 46);
    }

    @Test
    public void testCalculateBearing_LowerRightQuadrant() {

        // GIVEN I have two positions such that the latter is at a bearing of
        // 135 degrees from the first
        Position here = new Position(0, 0);
        Position there = new Position(1, 1);

        // WHEN I calculate the bearing from the former to the latter
        double bearing = Geometry.calculateBearing(here, there);
        bearing *= (180 / Math.PI);

        // THEN I receive approx. 135 degrees
        assert(bearing > 134 && bearing < 136);
    }

    @Test
    public void testCalculateBearing_LowerLeftQuadrant() {

        // GIVEN I have two positions such that the latter is at a bearing of
        // 225 degrees from the first
        Position here = new Position(0, 0);
        Position there = new Position(-1, 1);

        // WHEN I calculate the bearing from the former to the latter
        double bearing = Geometry.calculateBearing(here, there);
        bearing *= (180 / Math.PI);

        // THEN I receive approx. 225 degrees
        assert(bearing > 224 && bearing < 226);
    }

    @Test
    public void testCalculateBearing_UpperLeftQuadrant() {

        // GIVEN I have two positions such that the latter is at a bearing of
        // 315 degrees from the first
        Position here = new Position(0, 0);
        Position there = new Position(-1, -1);

        // WHEN I calculate the bearing from the former to the latter
        double bearing = Geometry.calculateBearing(here, there);
        bearing *= (180 / Math.PI);

        // THEN I receive approx. 315 degrees
        assert(bearing > 314 && bearing < 316);
    }

    @Test
    public void testCalculateBearing_DirectlyRight() {

        // GIVEN I have two positions such that the latter is at a bearing of
        // 90 degrees from the first
        Position here = new Position(0, 0);
        Position there = new Position(1, 0);

        // WHEN I calculate the bearing from the former to the latter
        double bearing = Geometry.calculateBearing(here, there);
        bearing *= (180 / Math.PI);

        // THEN I receive approx. 90 degrees
        assert(bearing > 89 && bearing < 91);
    }

    @Test
    public void testResolveVectors() {

        // GIVEN a list containing two XYVectors of (1, 1) and (1, 2)
        List<XYVector> vectors = new ArrayList<>();
        vectors.add(new XYVector(1, 1));
        vectors.add(new XYVector(1, 2));

        // WHEN I resolve this list into a single XYVector
        XYVector resultantVector = Geometry.resolveVectors(vectors);

        // THEN the resulting XYVector is (2, 3)
        assert(resultantVector.getX() == 2 && resultantVector.getY() == 3);
    }

    @Test
    public void testConvertToXYVector() {

        // GIVEN a BearingVector of magnitude 1 and bearing 90 degrees
        BearingVector bearingVector = new BearingVector(1, Math.PI/2);

        // WHEN I convert it to an XYVector
        XYVector xyVector = Geometry.convertToXYVector(bearingVector);

        // THEN the resulting XYVector is (1, 0), allowing for rounding errors
        assert(xyVector.getX() == 1 && Math.abs(xyVector.getY()) < 0.000001);
    }

    @Test
    public void testCalculateVolumeFromRadius() {

        // GIVEN a radius of 2
        // WHEN I calculate the volume of a sphere of this radius
        double volume = Geometry.calculateVolumeFromRadius(2);

        // THEN the volume I receive is approximately 33.51
        assert(Math.abs(volume - 33.51) < 0.01);
    }

    @Test
    public void testCalculateRadiusFromVolume() {

        // GIVEN a volume of 33.51
        // WHEN I calculate the radius of a sphere of this volume
        double radius = Geometry.calculateRadiusFromVolume(33.51);

        // THEN the radius I receive is approximately 2
        assert(Math.abs(radius - 2) < 0.01);
    }

    @Test
    public void testFindGreatestCardinalSeparation() {

        // GIVEN a list of two Entities separated by 10 metres vertically and 9
        // metres horizontally
        List<Entity> entities = new ArrayList<>();
        entities.add(new Entity(Body.EARTH, 0, 0, 0, 0));
        entities.add(new Entity(Body.EARTH, 0, 0, 9, 10));

        // WHEN I find the greatest cardinal distance between any pair in the
        // list
        // THEN I receive a distance of 10
        assert(Geometry.findGreatestCardinalSeparation(entities) == 10);
    }

}
