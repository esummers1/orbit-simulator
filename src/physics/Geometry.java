package physics;

import java.awt.*;
import java.util.List;

import entities.Entity;

/**
 * Class containing geometric utility methods (those involving angles and
 * co-ordinates, rather than forces and accelerations).
 * 
 * @author Eddie Summers
 */
public abstract class Geometry {

    /**
     * Given a pair of Positions, calculate the absolute distance between them.
     * @param here
     * @param there
     * @return double
     */
    public static double getDistance(Position here, Position there) {
        
        return Math.sqrt(
                Math.pow(there.getX() - here.getX(), 2) +
                Math.pow(there.getY() - here.getY(), 2));
    }

    /**
     * Given a pair of Positions, calculate the bearing (from negative-y, i.e.
     * upwards on-screen) of 'there' as observed from 'here', in radians.
     * @param here
     * @param there
     * @return double
     */
    public static double calculateBearing(Position here, Position there) {
        
        double deltaX = there.getX() - here.getX();
        double deltaY = there.getY() - here.getY();
        
        double theta = Math.PI / 2 + Math.atan2(deltaY, deltaX);
        
        // Not strictly necessary - makes testing clearer
        if (theta < 0) {
            theta += Math.PI * 2;
        }
        
        return theta;
    }

    /**
     * Given a list of XYVectors, resolve them into a single resultant XYVector.
     * @param vectors
     * @return XYVector
     */
    public static XYVector resolveVectors(List<XYVector> vectors) {
        
        XYVector resultantVector = new XYVector(0, 0);
        
        for (XYVector vector : vectors) {
            resultantVector.setX(resultantVector.getX() + vector.getX());
            resultantVector.setY(resultantVector.getY() + vector.getY());
        }
        
        return resultantVector;
    }

    /**
     * Convert a BearingVector to an XYVector.
     * @param vector
     * @return XYVector
     */
    public static XYVector convertToXYVector(BearingVector vector) {
        
        return new XYVector(
                vector.getMagnitude() * Math.sin(vector.getBearing()), 
                vector.getMagnitude() * -1 * Math.cos(vector.getBearing()));
    }
    
    /**
     * Calculate the volume of a sphere from its radius.
     * @param radius
     * @return double
     */
    public static double calculateVolumeFromRadius(double radius) {
        return ((4f / 3f) * Math.PI * Math.pow(radius, 3));
    }
    
    /**
     * Calculate the radius of a sphere from its volume.
     * @param volume
     * @return double
     */
    public static double calculateRadiusFromVolume(double volume) {
        return Math.pow((3f * volume) / (4f * Math.PI), 1f/3f);
    }
    
    /**
     * Given a list of Entities, calculate the largest cardinal-direction
     * separation between any pair (i.e. directly along the X or Y axes).
     * @param entities
     * @return double
     */
    public static double findGreatestCardinalSeparation(List<Entity> entities) {
        
        Position basePosition = entities.get(0).getPosition();
        double minimumX = basePosition.getX();
        double maximumX = basePosition.getX();
        double minimumY = basePosition.getY();
        double maximumY = basePosition.getY();
        
        for (Entity entity : entities) {
            
            if (entity.getPosition().getX() < minimumX) {
                minimumX = entity.getPosition().getX();
            }
            
            if (entity.getPosition().getX() > maximumX) {
                    maximumX = entity.getPosition().getX();
            }
            
            if (entity.getPosition().getY() < minimumY) {
                minimumY = entity.getPosition().getY();
            }
            
            if (entity.getPosition().getY() > maximumY) {
                maximumY = entity.getPosition().getY();
            }
            
        }
        
        double xSeparation = maximumX - minimumX;
        double ySeparation = maximumY - minimumY;
        
        return ySeparation > xSeparation ? ySeparation : xSeparation;
    }

    /**
     * Add an XYVector to a Position (i.e. a displacement) and return the new
     * Position.
     * @param vector
     * @param position
     * @return Position
     */
    public static Position addXYVectorToPosition(
            XYVector vector, Position position) {

        return new Position(
                position.getX() + vector.getX(),
                position.getY() + vector.getY());
    }

    /**
     * Subtract an XYVector from a Position (i.e. a negative displacement) and
     * return the new Position.
     * @param vector
     * @param position
     * @return Position
     */
    public static Position subtractXYVectorFromPosition(
            XYVector vector, Position position) {

        return new Position(
                position.getX() - vector.getX(),
                position.getY() - vector.getY());
    }

    /**
     * Multiply an XYVector by some scalar quantity and return it.
     * @param vector
     * @param scalar
     * @return XYVector
     */
    public static XYVector multiplyXYVectorByScalar(
            XYVector vector, double scalar) {

        return new XYVector(vector.getX() * scalar, vector.getY() * scalar);
    }

    /**
     * For a pair of Points (i.e. locations on the screen, measured in pixels),
     * create an XYVector representing the displacement of the second from the
     * first.
     * @param first
     * @param second
     * @return XYVector
     */
    public static XYVector findDisplacementBetweenPoints(
            Point first, Point second) {

        return new XYVector(second.x - first.x, second.y - first.y);
    }

}
