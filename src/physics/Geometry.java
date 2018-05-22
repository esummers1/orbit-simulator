package physics;

import entities.Entity;

/**
 * Class containing geometric utility methods (e.g. those involving angles and
 * co-ordinates, rather than forces and accelerations).
 * 
 * @author Eddie Summers
 */
public abstract class Geometry {

    /**
     * Given a pair of entities, calculate the absolute distance between them.
     * @param thisEntity
     * @param otherEntity
     * @return double
     */
    public static double getDistance(Entity thisEntity, Entity otherEntity) {
        
        return Math.sqrt(
                Math.pow(Geometry.getXDistance(thisEntity, otherEntity), 2) +
                Math.pow(Geometry.getYDistance(thisEntity, otherEntity), 2));
    }

    /**
     * Given a pair of entities, calculate the X-component of the distance
     * between them.
     * @param thisEntity
     * @param otherEntity
     * @return double
     */
    public static double getXDistance(Entity thisEntity, Entity otherEntity) {
        
        double thisX = thisEntity.getPosition().getX();
        double otherX = otherEntity.getPosition().getX();
        
        return otherX - thisX;
    }

    /**
     * Given a pair of entities, calculate the Y-component of the distance
     * between them.
     * @param thisEntity
     * @param otherEntity
     * @return double
     */
    public static double getYDistance(Entity thisEntity, Entity otherEntity) {
        
        double thisY = thisEntity.getPosition().getY();
        double otherY = otherEntity.getPosition().getY();
        
        return otherY - thisY;
    }

    /**
     * Given a pair of entities, calculate the bearing (from negative-y, i.e.
     * upwards on-screen) of the 'other' from the 'this', in radians.
     * @param thisEntity
     * @param otherEntity
     * @return double
     */
    public static double getBearing(Entity thisEntity, Entity otherEntity) {
        
        double theta = Math.PI + Math.atan(
                (otherEntity.getPosition().getX() - 
                thisEntity.getPosition().getX())/
                (thisEntity.getPosition().getY() - 
                otherEntity.getPosition().getY()));
        
        // Compensate for ambiguity in tangent function
        if (thisEntity.getPosition().getY() > 
                otherEntity.getPosition().getX()) {
           theta += Math.PI;
        }
        
        // Resolve to bearing below 2 PI if it's gone all the way round
        return theta % (2 * Math.PI);
    }
    
}
