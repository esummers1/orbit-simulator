package test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import entities.Earth;
import entities.Entity;
import entities.Moon;
import physics.BearingVector;
import physics.Constants;
import physics.Force;
import physics.Physics;

public class PhysicsTest {
    
    @Test
    public void testResolveForces() {
        
        // GIVEN a list of two Forces of (1, 1)
        List<Force> forces = new ArrayList<>();
        forces.add(new Force(1, 1));
        forces.add(new Force(1, 1));
        
        // WHEN I resolve this list into a single Force
        Force resultantForce = Physics.resolveForces(forces);
        
        // THEN the Force I receive has components (2, 2)
        assert(resultantForce.getX() == 2);
        assert(resultantForce.getY() == 2);
    }
    
    @Test
    public void testComputeGravitationalForce() {
                    
    }
    
    @Test
    public void testConvertToForceVector_UpperRightQuadrant() {
        
        // GIVEN a vector of magnitude 1 and bearing 45 degrees
        // WHEN I convert this vector to a Force
        BearingVector vector = new BearingVector(1, 45 * Math.PI / 180);
        Force force = Physics.convertToForceVector(vector);
        
        // THEN I receive a Force of (0.7, -0.7)
        double xError = Math.abs(force.getX() - 0.7);
        double yError = Math.abs(force.getY() - (-0.7));
        
        assert (xError < 0.1 && yError < 0.1);
    }
    
    @Test
    public void testConvertToForceVector_LowerRightQuadrant() {
        
        // GIVEN a vector of magnitude 1 and bearing 135 degrees
        // WHEN I convert this vector to a Force
        BearingVector vector = new BearingVector(1, 135 * Math.PI / 180);
        Force force = Physics.convertToForceVector(vector);
        
        // THEN I receive a Force of (0.7, 0.7)
        double xError = Math.abs(force.getX() - 0.7);
        double yError = Math.abs(force.getY() - 0.7);
        
        assert (xError < 0.1 && yError < 0.1);
    }
    
    @Test
    public void testConvertToForceVector_LowerLeftQuadrant() {
        
        // GIVEN a vector of magnitude 1 and bearing 225 degrees
        // WHEN I convert this vector to a Force
        BearingVector vector = new BearingVector(1, 225 * Math.PI / 180);
        Force force = Physics.convertToForceVector(vector);
        
        // THEN I receive a Force of (-0.7, 0.7)
        double xError = Math.abs(force.getX() - (-0.7));
        double yError = Math.abs(force.getY() - 0.7);
        
        assert (xError < 0.1 && yError < 0.1);
    }
    
    @Test
    public void testConvertToForceVector_UpperLeftQuadrant() {
        
        // GIVEN a vector of magnitude 1 and bearing 315 degrees
        // WHEN I convert this vector to a Force
        BearingVector vector = new BearingVector(1, 315 * Math.PI / 180);
        Force force = Physics.convertToForceVector(vector);
        
        // THEN I receive a Force of (-0.7, -0.7)
        double xError = Math.abs(force.getX() - (-0.7));
        double yError = Math.abs(force.getY() - (-0.7));
        
        assert (xError < 0.1 && yError < 0.1);
    }
    
    @Test
    public void testGetBearing_UpperRightQuadrant() {
        
        // GIVEN two Entities, where the second is at a bearing of 30 degrees
        // from the first
        Entity earth = new Earth(0, 0, 0, 0);
        Entity moon = new Moon(0, 0, 10, -17.3);
        
        // WHEN I calculate this bearing
        double bearing = Physics.getBearing(earth, moon);
        double bearingInDegrees = (bearing / Math.PI * 180) % 360;
        
        // THEN the bearing is approximately 30 degrees.
        System.out.println(bearingInDegrees);
        assert(bearingInDegrees > 29 && bearingInDegrees < 31);
    }
    
    @Test
    public void testGetBearing_LowerRightQuadrant() {
        
        // GIVEN two Entities, where the second is at a bearing of 100 degrees
        // from the first
        Entity earth = new Earth(0, 0, 0, 0);
        Entity moon = new Moon(0, 0, 40, 7.05);
        
        // WHEN I calculate this bearing
        double bearing = Physics.getBearing(earth, moon);
        double bearingInDegrees = (bearing / Math.PI * 180) % 360;
        
        // THEN the bearing is approximately 100 degrees.
        assert(bearingInDegrees > 99 && bearingInDegrees < 101);
    }
    
    @Test
    public void testGetBearing_LowerLeftQuadrant() {
        
        // GIVEN two Entities, where the second is at a bearing of 225 degrees
        // from the first
        Entity earth = new Earth(0, 0, 0, 0);
        Entity moon = new Moon(0, 0, -20, 20);
        
        // WHEN I calculate this bearing
        double bearing = Physics.getBearing(earth, moon);
        double bearingInDegrees = (bearing / Math.PI * 180) % 360;
        
        // THEN I receive approx. 225 degrees
        System.out.println(bearingInDegrees);
        assert(bearingInDegrees > 224 && bearingInDegrees < 226);
    }
    
    @Test
    public void testGetBearing_UpperLeftQuadrant() {
        
        // GIVEN two Entities, where the second is at a bearing of 345 degrees
        // from the first
        Entity earth = new Earth(0, 0, 0, 0);
        Entity moon = new Moon(0, 0, -50, -137.4);
        
        // WHEN I calculate this bearing
        double bearing = Physics.getBearing(earth, moon);
        double bearingInDegrees = (bearing / Math.PI * 180) % 360;
        
        // THEN I receive approx. 345 degrees
        assert(bearingInDegrees > 340 && bearingInDegrees < 350);
    }
    
    @Test
    public void testGetGravityMagnitude() {
        
        // GIVEN two Entities, the Earth and the Moon, separated by 100,000 km
        Entity earth = new Earth(0, 0, 0, 0);
        Entity moon = new Moon(0, 0, Math.pow(10, 8), 0);
        
        // WHEN I calculate the magnitude of the gravitational force on the
        // Earth from the Moon
        double gravitationalForce = Physics.getGravityMagnitude(earth, moon);
        
        // THEN I get approx. 2.925 x 10 ^ 21
        double lowerBound = 2.9 * Math.pow(10, 21);
        double upperBound = 2.95 * Math.pow(10, 21);
        
        assert(lowerBound < gravitationalForce);
        assert(gravitationalForce < upperBound);
    }
    
    @Test
    public void testGetDistance() {
        
        // GIVEN a pair of entities 5000 metres apart
        Entity thisEntity = new Earth(0, 0, 0, 0);
        Entity otherEntity = new Moon(0, 0, 3000, 4000);
        
        // WHEN I calculate their separation distance
        double distance = Physics.getDistance(thisEntity, otherEntity);
        
        // THEN I receive 5000 metres
        assert(distance == 5000);
    }
    
    @Test
    public void testGetXDistance_PositiveDirection() {
        
        // GIVEN two Entities where the nominative is 1 metre to the west
        Entity thisEntity = new Earth(0, 0, 0, 0);
        Entity otherEntity = new Moon(0, 0, 1, 0);
        
        // WHEN I get the X-distance from the nominative to the accusative
        double xDistance = Physics.getXDistance(thisEntity, otherEntity);
        
        // THEN the distance I get is positive 1
        assert(xDistance == 1);
    }
    
    @Test
    public void testGetYDistance_NegativeDirection() {
        
        
        // GIVEN two Entities where the nominative is 1 metre to the south
        Entity thisEntity = new Earth(0, 0, 0, 1);
        Entity otherEntity = new Moon(0, 0, 0, 0);
        
        // WHEN I get the Y-distance from the nominative to the accusative
        double yDistance = Physics.getYDistance(thisEntity, otherEntity);
        
        // THEN the distance I get is -1
        assert(yDistance == -1);
    }
    
    @Test
    public void testApplyForce_NegativeDirection() {
        
        // GIVEN an Entity of mass 1 at rest
        Entity entity = new Entity("", 1, 1, 0, 0, 0, 0, Color.WHITE);
        
        // AND a Force vector of (-1, -1)
        Force force = new Force(-1, -1);
        
        // WHEN I apply this Force to this Entity
        Physics.applyForce(entity, force);
        
        // THEN the Entity's new velocity vector is (- time step, - time step)
        assert(entity.getXVel() == -1 * Constants.TIME_STEP);
        assert(entity.getYVel() == -1 * Constants.TIME_STEP);
    }
    
    @Test
    public void testProjectEntity() {
        
        // GIVEN an Entity at (0, 0) moving at a velocity of (1, 1)
        Entity entity = new Entity("", 1, 1, 1, 1, 0, 0, Color.WHITE);
        
        // WHEN I project it over one time step
        Physics.projectEntity(entity);
        
        // THEN its new position is (time step, time step)
        assert(entity.getPosition().getX() == Constants.TIME_STEP);
        assert(entity.getPosition().getY() == Constants.TIME_STEP);
    }
    
}
