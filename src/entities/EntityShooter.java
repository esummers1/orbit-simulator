package entities;

import main.Camera;
import physics.BearingVector;
import physics.Geometry;
import physics.Position;
import physics.XYVector;

/**
 * Class responsible for creating Entities in the Simulation according to a
 * mouse-drag input.
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

        // Obtain actual simulated positions
        Position start = convertDisplayedPositionToSimulation(
                entityShot.getStart(),
                entityShot.getCamera(),
                entityShot.getScaleFactor());
        Position end = convertDisplayedPositionToSimulation(
                entityShot.getEnd(),
                entityShot.getCamera(),
                entityShot.getScaleFactor());

        // Derive velocity of new Entity
        double dragDistance = Geometry.getDistance(start, end);
        double bearing = Geometry.calculateBearing(start, end);

        BearingVector velocity = new BearingVector(
                dragDistance / entityShot.getDuration(), bearing);
        XYVector xyVelocity = Geometry.convertToXYVector(velocity);

        // Create Entity
        return new Entity(entityShot.getBody(), xyVelocity, end);
    }

    /**
     * Given a Position on the screen in the Simulation window, return the
     * Position representing the corresponding location in the simulated
     * worldspace.
     * @param position
     * @param camera
     * @param scaleFactor
     * @return Position
     */
    private static Position convertDisplayedPositionToSimulation(
            Position position, Camera camera, double scaleFactor) {

        Position scaledPosition =
                scaleDisplayedPositionToSimulation(position, scaleFactor);

        Position correctedScaledPosition =
                correctForCameraFocusOffset(scaledPosition, camera);

        return correctedScaledPosition;
    }

    /**
     * Given a Position measured in pixels, return a Position measured in
     * metres, i.e. one at Simulation scale. This assumes the Simulation is
     * being displayed with the top-left corner being 0, 0 metres.
     * @param position
     * @return Position
     */
    private static Position scaleDisplayedPositionToSimulation(
            Position position, double scaleFactor) {

        return new Position(
                position.getX() * scaleFactor,
                position.getY() * scaleFactor);
    }

    /**
     * Given a Position measured in metres and a Camera, return a new Position
     * offset from the original by the distance of the Camera's focus from the
     * Simulation's origin.
     * @param position
     * @param camera
     * @return Position
     */
    private static Position correctForCameraFocusOffset(
            Position position, Camera camera) {

        return new Position(
                position.getX() + camera.getFocus().getX(),
                position.getY() + camera.getFocus().getY());
    }

}
