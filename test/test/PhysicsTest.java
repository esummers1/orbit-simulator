package test;

import main.Scenario;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import main.Camera;
import main.Simulation;
import entities.Body;
import entities.Entity;
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

    @Test
    public void testProjectEntity() {

        // GIVEN an Entity located at (0, 0) and moving at (1, 1)
        // AND a Simulation with some timeStep containing this Entity
        List<Entity> entities = new ArrayList<>();
        Entity entity = new Entity(new Body("", 0, 0, null), 1, 1, 0, 0);
        entities.add(entity);

        Simulation simulation =
                new Simulation(new Scenario("", entities, 1, 1));

        // WHEN I project this Entity for one time step
        Physics.projectEntity(entity);

        // THEN its new position will be (timeStep, timeStep)
        assert(entity.getPosition().getX() == Simulation.getTimeStep());
        assert(entity.getPosition().getY() == Simulation.getTimeStep());
    }

    @Test
    public void testApplyForce() {

        // GIVEN a force of (1, 1) and an Entity of mass 1 at rest
        // AND a Simulation with some timeStep containing this Entity
        XYVector force = new XYVector(1, 1);

        List<Entity> entities = new ArrayList<>();
        Entity entity = new Entity(new Body("", 1, 0, null), 0, 0, 0, 0);
        entities.add(entity);

        Simulation simulation =
                new Simulation(new Scenario("", entities, 1, 1));

        // WHEN I apply this force to this Entity over this time step
        Physics.applyForce(entity, force);

        // THEN the Entity will have X- and Y-velocities equal in magnitude
        // to the time step.
        assert(entity.getVelocity().getX() == Simulation.getTimeStep());
        assert(entity.getVelocity().getY() == Simulation.getTimeStep());
    }

    @Test
    public void testDetectCollision_NoCollision() {

        // GIVEN two Entities with a radius of 1 which are 10 metres apart
        Entity thisEntity = new Entity(new Body("", 1, 1, null), 0, 0, 0, 0);
        Entity otherEntity = new Entity(new Body("", 1, 1, null), 0, 0, 10, 0);

        // WHEN I detect whether they have collided
        // THEN I find they have not
        assert(!Physics.detectCollision(thisEntity, otherEntity));
    }

    @Test
    public void testDetectCollision_WhenCollided() {

        // GIVEN two Entities with a radius of 10 which are 1 metre apart
        Entity thisEntity = new Entity(new Body("", 1, 10, null), 0, 0, 0, 0);
        Entity otherEntity = new Entity(new Body("", 1, 10, null), 0, 0, 1, 0);

        // WHEN I detect whether they have collided
        // THEN I find they have
        assert(Physics.detectCollision(thisEntity, otherEntity));
    }

    @Test
    public void testMergeBodies() {

        // GIVEN a white Body and a black Body, named by colour, of masses 1 and
        // 2 respectively and radii of 1
        Body whiteBody = new Body("white", 1, 1, new Color(255, 255, 255));
        Body blackBody = new Body("black", 2, 1, new Color(0, 0, 0));

        // WHEN I merge them
        Body greyBody = Physics.mergeBodies(whiteBody, blackBody);

        // THEN I receive a single grey Body (85, 85, 85) of mass 3, name
        // "black + white" and radius of approximately 1.26
        assert(greyBody.getColour().getRed() == 85);
        assert(greyBody.getColour().getGreen() == 85);
        assert(greyBody.getColour().getBlue() == 85);
        assert(greyBody.getMass() == 3);
        assert(greyBody.getName().equals("black + white"));
        assert(Math.abs(greyBody.getRadius() - 1.26) < 0.01);
    }

    @Test
    public void testCalculateRadiusOfMergedBodies() {

        // GIVEN I have two Bodies of radius 1
        Body thisBody = new Body("", 0, 1, null);
        Body otherBody = new Body("", 0, 1, null);

        // WHEN I calculate the radius they would have when merged
        double newRadius =
                Physics.calculateRadiusOfMergedBodies(thisBody, otherBody);

        // THEN I receive approximately 1.26
        assert(Math.abs(newRadius - 1.26) < 0.01);
    }

    @Test
    public void testMergeVelocities() {

        // GIVEN a pair of Entities with masses 1 and 2, travelling with
        // velocity vectors (2, 4) and (3.5, 4) respectively
        Entity thisEntity = new Entity(new Body("", 1, 1, null), 2, 4, 0, 0);
        Entity otherEntity = new Entity(new Body("", 2, 1, null), 3.5, 4, 0, 0);

        // WHEN I merge their velocities (preserving total momentum)
        XYVector newVelocity = Physics.mergeVelocities(thisEntity, otherEntity);

        // THEN I get a vector of (3, 4)
        assert(newVelocity.getX() == 3);
        assert(newVelocity.getY() == 4);
    }

    @Test
    public void testCalculateMomentum() {

        // GIVEN an Entity of mass 2 and velocity (1, 1)
        Entity entity = new Entity(new Body("", 2, 1, null), 1, 1, 0, 0);

        // WHEN I calculate its momentum
        XYVector momentum = Physics.calculateMomentum(entity);

        // THEN I receive an XYVector of (2, 2)
        assert(momentum.getX() == 2);
        assert(momentum.getY() == 2);
    }

    @Test
    public void testCalculateAppropriateScaleFactor_OneEntity() {

        // GIVEN a list containing one Entity of radius 10
        List<Entity> entities = new ArrayList<>();
        entities.add(new Entity(new Body("", 1, 10, null), 0, 0, 0, 0));

        // WHEN I calculate an appropriate viewing scale factor
        // THEN I receive 100
        assert(Physics.calculateAppropriateScaleFactor(entities) == 100);
    }

    @Test
    public void testCalculateAppropriateScaleFactor_ManyEntities() {

        // GIVEN a list of two Entities separated by 10 metres vertically and 9
        // metres horizontally
        List<Entity> entities = new ArrayList<>();
        entities.add(new Entity(Body.EARTH, 0, 0, 0, 0));
        entities.add(new Entity(Body.EARTH, 0, 0, 9, 10));

        // WHEN I calculate an appropriate viewing scale factor
        // THEN I receive 30
        assert(Physics.calculateAppropriateScaleFactor(entities) == 30);
    }

}
