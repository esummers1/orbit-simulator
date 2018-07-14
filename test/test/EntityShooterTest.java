package test;

import entities.Body;
import entities.Entity;
import entities.EntityShooter;
import entities.EntityShot;
import main.Camera;
import main.Display;
import org.junit.jupiter.api.Test;
import physics.Position;

import java.awt.*;

public class EntityShooterTest {

    @Test
    public void testCreateEntityForShooting() {

        /*
         * GIVEN an EntityShot with these properties:
         * - a Body of the moon
         * - a start Point of (0, 0)
         * - an end Point of (WS/2, 0)
         * - a Camera with a focus Position of (WS*6, WS*6)
         * - a drag duration of 1000 ms
         * - a scaleFactor of 2
         *
         * AND a time acceleration factor of 2
         */
        Position focus = new Position(
                Display.WINDOW_SIZE * 6, Display.WINDOW_SIZE * 6);

        EntityShot shot = new EntityShot(
                Body.MOON,
                new Point(0, 0),
                new Point(Display.WINDOW_SIZE / 2, 0),
                new Camera(focus, Display.WINDOW_SIZE),
                1000,
                2);
        double timeAcceleration = 2;

        // WHEN I create an Entity from this EntityShot
        Entity entity =
                EntityShooter.createEntityForShooting(shot, timeAcceleration);

        /*
         * THEN I receive an Entity with these properties:
         * - a Body of the moon
         * - a velocity of (WS/2, 0)
         * - a Position of (WS*6, WS*5)
         */
        assert(entity.getBody().getName().equals("Moon"));
        assert(entity.getVelocity().getX() == Display.WINDOW_SIZE / 2);

        // Correct for noise in trig function
        assert(Math.abs(entity.getVelocity().getY()) < 0.001);

        assert(entity.getPosition().getX() == Display.WINDOW_SIZE * 6);
        assert(entity.getPosition().getY() == Display.WINDOW_SIZE * 5);
    }

    @Test
    public void testCalculatePositionInSimulation() {

        // GIVEN a Point at (0, 0)
        // AND a Camera with a focus Position of (WS*6, WS*6)
        // AND a scale factor of 2
        Point point = new Point(0, 0);

        Position focus = new Position(
                Display.WINDOW_SIZE * 6, Display.WINDOW_SIZE * 6);
        Camera camera = new Camera(focus, Display.WINDOW_SIZE);

        double scaleFactor = 2;

        // WHEN I calculate the Point's position
        Position position = EntityShooter.calculatePositionInSimulation(
                point, camera, scaleFactor);

        // THEN I get a Position of (WS*5, WS*5)
        // (as camera focus is by definition (WS/2, WS/2)
        assert(position.getX() == Display.WINDOW_SIZE * 5);
        assert(position.getY() == Display.WINDOW_SIZE * 5);
    }
}
