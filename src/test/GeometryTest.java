package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import physics.BearingVector;
import physics.Geometry;
import physics.Position;
import physics.XYVector;

public class GeometryTest {
    
    @Test
    public void testGetDistance() {
        
        // GIVEN two Positions 5 metres apart
        Position here = new Position(0, 0);
        Position there = new Position(3, 4);
        
        // WHEN I retrieve the distance between them
        // THEN I receive 5 metres
        assert(Geometry.getDistance(here, there) == 5);
    }
    
    @Test
    public void testCalculateBearing_UpperRightQuadrant() {
        
    }
    
    @Test
    public void testCalculateBearing_LowerRightQuadrant() {
        
    }
    
    @Test
    public void testCalculateBearing_LowerLeftQuadrant() {
        
    }
    
    @Test
    public void testCalculateBearing_UpperLeftQuadrant() {
        
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

}
