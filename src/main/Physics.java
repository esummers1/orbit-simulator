package main;

import java.util.List;

import entities.Entity;

public abstract class Physics {
    
    /**
     * Given a list of Force objects, resolve them into a single resultant
     * Force.
     * @param forces
     * @return Force
     */
    public static Force resolveForces(List<Force> forces) {
        
        Force resultantForce = new Force(0, 0);
        
        for (Force force : forces) {
            double x = resultantForce.getX();
            double y = resultantForce.getY();
            
            resultantForce.setX(x + force.getX());
            resultantForce.setY(y + force.getY());
        }
        
        return resultantForce;
    }
    
    /**
     * Calculate the gravitational attraction Force from otherEntity to
     * thisEntity.
     * @param thisEntity
     * @param otherEntity
     * @return Force
     */
    public static Force computeGravitationalForce(Entity thisEntity, 
            Entity otherEntity) {
        
        double gravity = getGravityMagnitude(thisEntity, otherEntity);
        double bearing = getBearing(thisEntity, otherEntity);
        
        return convertToForceVector(gravity, bearing);        
    }
    
    /**
     * Given a vector in the form of a magnitude and a bearing, return a Force 
     * object containing the x- and y- components of that vector.
     * @param magnitude
     * @param bearing
     * @return Force
     */
    public static Force convertToForceVector(double magnitude, double bearing) {
        
        return new Force(
                magnitude * Math.sin(bearing), 
                magnitude * -1 * Math.cos(bearing));
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
                (otherEntity.getX() - thisEntity.getX())/
                (thisEntity.getY() - otherEntity.getY()));
        
        // Compensate for ambiguity in tangent function.
        if (thisEntity.getY() > otherEntity.getX()) {
           theta += Math.PI;
        }
        
        // Resolve to bearing below 2 PI if it's gone all the way round.
        return theta % (2 * Math.PI);
    }
    
    /**
     * Given a pair of entities, calculate the magnitude of the gravitational
     * force that is exerted on the 'this' by the 'other'.
     * @param thisEntity
     * @param otherEntity
     * @return double
     */
    public static double getGravityMagnitude(Entity thisEntity, 
            Entity otherEntity) {
        
        return Constants.BIG_G * 
                thisEntity.getMass() * 
                otherEntity.getMass() /
                Math.pow(getDistance(thisEntity, otherEntity), 2);
    }
    
    /**
     * Given a pair of entities, calculate the absolute distance between them.
     * @param thisEntity
     * @param otherEntity
     * @return double
     */
    public static double getDistance(Entity thisEntity, Entity otherEntity) {
        
        return Math.sqrt(
                Math.pow(getXDistance(thisEntity, otherEntity), 2) +
                Math.pow(getYDistance(thisEntity, otherEntity), 2));
    }
    
    /**
     * Given a pair of entities, calculate the X-component of the distance
     * between them.
     * @param thisEntity
     * @param otherEntity
     * @return double
     */
    public static double getXDistance(Entity thisEntity, Entity otherEntity) {
        
        double thisX = thisEntity.getX();
        double otherX = otherEntity.getX();
        
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
        
        double thisY = thisEntity.getY();
        double otherY = otherEntity.getY();
        
        return otherY - thisY;
    }
    
    /**
     * Apply a force to an entity over the time step.
     * @param entity
     * @param force
     */
    public static void applyForce(Entity entity, Force force) {
        
        double mass = entity.getMass();
        
        double xAcc = force.getX() / mass;
        double yAcc = force.getY() / mass;
        
        entity.setXVel(entity.getXVel() + xAcc * Constants.TIME_STEP);
        entity.setYVel(entity.getYVel() + yAcc * Constants.TIME_STEP);
    }
    
    /**
     * Recalculate and apply an entity's position if it moves under its current
     * velocity for one time step.
     * @param entity
     */
    public static void projectEntity(Entity entity) {
        
        double xVel = entity.getXVel();
        double yVel = entity.getYVel();
        
        entity.setX(entity.getX() + xVel * Constants.TIME_STEP);
        entity.setY(entity.getY() + yVel * Constants.TIME_STEP);
    }
    
}
