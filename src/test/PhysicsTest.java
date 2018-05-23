package test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import entities.Body;
import entities.Entity;
import main.Simulation;
import physics.Physics;
import physics.Position;
import physics.XYVector;

public class PhysicsTest {

    @Test
    public void testComputeGravitationalForce() {
        
        // GIVEN an Earth at (0, 0) AND a Moon at (100000, 200000)
        Entity earth = new Entity(
                new Body("", 5.97 * Math.pow(10, 24), 0, null),
                0, 0, 0, 0);
        Entity moon = new Entity(
                new Body("", 7.34 * Math.pow(10, 22), 0, null),
                0, 0, 100000, 200000);
        
        // WHEN I calculate the XYVector gravitational force of the moon upon
        // the earth
        XYVector gravitation = Physics.computeGravitationalForce(earth, moon);
        
        // THEN I receive a vector with components approximately equal to:
        // x = 2.617E26
        // y = 5.231E26
        assert(gravitation.getX() > 2.6 * Math.pow(10, 26));
        assert(gravitation.getX() < 2.7 * Math.pow(10, 26));
        assert(gravitation.getY() > 5.2 * Math.pow(10, 26));
        assert(gravitation.getY() < 5.3 * Math.pow(10, 26));
    }
    
    @Test
    public void testGetGravityMagnitude() {
        
        // GIVEN a pair of Entities whose expected gravitational attraction is
        // equal to Big G
        Entity firstEntity = new Entity(new Body("", 1, 0, null), 0, 0, 0, 0);
        Entity secondEntity = new Entity(new Body("", 1, 0, null), 0, 0, 1, 0);
        
        // WHEN I calculate the attraction from one to the other
        double gravitation = 
                Physics.getGravityMagnitude(firstEntity, secondEntity);
        
        // THEN I get Big G
        assert(gravitation == Physics.BIG_G);
    }
    
    @Test
    public void testCalculateBarycentre() {
        
        // GIVEN a list of two Entities whose barycentre is (1, 1)
        List<Entity> entities = new ArrayList<>();
        Entity firstEntity = new Entity(new Body("", 1, 0, null), 0, 0, 0, 1);
        Entity secondEntity = new Entity(new Body("", 1, 0, null), 0, 0, 2, 1);
        entities.add(firstEntity);
        entities.add(secondEntity);
        
        // WHEN I calculate their barycentre
        Position barycentre = Physics.calculateBarycentre(entities);
        
        // THEN I get (1, 1)
        assert(barycentre.getX() == 1 && barycentre.getY() == 1);                
    }
    
    /**
     * Throws graphics-related errors, possibly due to window not being created.
     */
    @Test
    public void testProjectEntity() {
        
        // GIVEN an Entity located at (0, 0) and moving at (1, 1)
        // AND a Simulation with some timeStep containing this Entity
        List<Entity> entities = new ArrayList<>();
        Entity entity = new Entity(new Body("", 0, 0, null), 1, 1, 0, 0);
        entities.add(entity);
        
        Simulation simulation = new Simulation(entities, 1, 1, 1);
        
        // WHEN I project this Entity for one time step
        Physics.projectEntity(entity);
        
        // THEN its new position will be (timeStep, timeStep)
        assert(entity.getPosition().getX() == Simulation.getTimeStep());
        assert(entity.getPosition().getY() == Simulation.getTimeStep());
    }
    
    /**
     * Throws graphics-related errors, possibly due to window not being created.
     */
    @Test
    public void testApplyForce() {
        
        // GIVEN a force of (1, 1) and an Entity of mass 1 at rest
        // AND a Simulation with some timeStep containing this Entity
        XYVector force = new XYVector(1, 1);
        
        List<Entity> entities = new ArrayList<>();
        Entity entity = new Entity(new Body("", 1, 0, null), 0, 0, 0, 0);
        entities.add(entity);
        
        Simulation simulation = new Simulation(entities, 1, 1, 1);
        
        // WHEN I apply this force to this Entity over this time step
        Physics.applyForce(entity, force);
        
        // THEN the Entity will have X- and Y-velocities equal in magnitude
        // to the time step.
        assert(entity.getXVel() == Simulation.getTimeStep());
        assert(entity.getYVel() == Simulation.getTimeStep());
    }
    
}
