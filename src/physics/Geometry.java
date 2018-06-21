package physics;

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
     * Find the X-position of the Entity furthest on the negative X-axis in a
     * list of Entities.
     * @param entities
     * @return Entity
     */
    public static double findLowestXPosition(List<Entity> entities) {
        
        double minimumX = entities.get(0).getPosition().getX();
        
        for (Entity entity : entities) {
            if (entity.getPosition().getX() < minimumX) {
                minimumX = entity.getPosition().getX();
            }
        }
        
        return minimumX;
    }
    
    /**
     * Find the X-position of the Entity furthest on the positive X-axis in a
     * list of Entities.
     * @param entities
     * @return Entity
     */
    public static double findHighestXPosition(List<Entity> entities) {
        
        double maximumX = entities.get(0).getPosition().getX();
        
        for (Entity entity : entities) {
            if (entity.getPosition().getX() > maximumX) {
                maximumX = entity.getPosition().getX();
            }
        }
        
        return maximumX;
    }
    
    /**
     * Find the Y-position of the Entity furthest on the negative Y-axis in a
     * list of Entities.
     * @param entities
     * @return Entity
     */
    public static double findLowestYPosition(List<Entity> entities) {
        
        double minimumY = entities.get(0).getPosition().getY();
        
        for (Entity entity : entities) {
            if (entity.getPosition().getY() < minimumY) {
                minimumY = entity.getPosition().getY();
            }
        }
        
        return minimumY;
    }
    
    /**
     * Find the Y-position of the Entity furthest on the positive Y-axis in a
     * list of Entities.
     * @param entities
     * @return Entity
     */
    public static double findHighestYPosition(List<Entity> entities) {
        
        double maximumY = entities.get(0).getPosition().getY();
        
        for (Entity entity : entities) {
            if (entity.getPosition().getY() > maximumY) {
                maximumY = entity.getPosition().getY();
            }
        }
        
        return maximumY;
    }
    
    /**
     * Given a list of Entities, calculate the largest cardinal-direction
     * separation between any pair (i.e. directly along the X or Y axes).
     * @param entities
     * @return double
     */
    public static double findGreatestCardinalSeparation(List<Entity> entities) {
        
        double xSeparation = Math.abs(
                findHighestXPosition(entities) - findLowestXPosition(entities));
        double ySeparation = Math.abs(
                findHighestYPosition(entities) - findLowestYPosition(entities));
        
        return ySeparation > xSeparation ? ySeparation : xSeparation;
    }
    
}
