package entities;

import main.Camera;
import main.Display;
import physics.BearingVector;
import physics.Geometry;
import physics.Position;
import physics.XYVector;

import java.awt.*;

/**
 * Class responsible for creating Entities in the Simulation, using an
 * EntityShot object.
 *
 * @author Eddie Summers
 */
public class EntityShooter {

    /**
     * Given an EntityShot and the current scale factor, map its properties to
     * an Entity object and return that object.
     * @param entityShot
     * @return Entity
     */
    public static Entity createEntityForShooting(EntityShot entityShot) {

        // Create new positions for start and end using this distance
        Position start = calculatePositionInSimulation(
                entityShot.getStart(),
                entityShot.getCamera(),
                entityShot.getScaleFactor());
        Position end = calculatePositionInSimulation(
                entityShot.getEnd(),
                entityShot.getCamera(),
                entityShot.getScaleFactor());

        // Derive velocity of new Entity
        double dragDistance = Geometry.getDistance(start, end);
        double bearing = Geometry.calculateBearing(start, end);

        // Convert duration to seconds
        double secondsDuration = ((double) entityShot.getDuration()) / 1000;
        BearingVector velocity = new BearingVector(
                dragDistance / secondsDuration, bearing);
        XYVector xyVelocity = Geometry.convertToXYVector(velocity);

        // Create Entity
        Entity entity = new Entity(entityShot.getBody(), xyVelocity, end);

        return entity;
    }

    /**
     * Given some Point, a Camera and a scale factor, calculate the actual
     * location of the Point in the simulated worldspace, using its distance
     * from the Camera's focus point.
     * @param point
     * @param camera
     * @param scaleFactor
     * @return Position
     */
    public static Position calculatePositionInSimulation(
            Point point, Camera camera, double scaleFactor) {

        Point cameraFocusPoint = new Point(
                Display.WINDOW_SIZE / 2, Display.WINDOW_SIZE / 2);

        XYVector pointOffset =
                Geometry.findDisplacementBetweenPoints(point, cameraFocusPoint);

        XYVector positionOffset =
                Geometry.multiplyXYVectorByScalar(pointOffset, scaleFactor);

        return Geometry.subtractXYVectorFromPosition(
                positionOffset, camera.getFocus());
    }

}
