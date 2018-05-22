package physics;

import java.util.List;

import entities.Body;
import entities.Entity;
import main.Simulation;

public abstract class Physics {
    
 // Universal gravitational constant
    public static final double BIG_G = 6.674 * Math.pow(10, -11);
    
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
        
        return convertToForceVector(new BearingVector(gravity, bearing));        
    }
    
    /**
     * Given a vector in the form of a magnitude and a bearing, return a Force 
     * object containing the x- and y- components of that vector.
     * @param vector
     * @return Force
     */
    public static Force convertToForceVector(BearingVector vector) {
        
        return new Force(
                vector.getMagnitude() * Math.sin(vector.getBearing()), 
                vector.getMagnitude() * -1 * Math.cos(vector.getBearing()));
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
        
        // Compensate for ambiguity in tangent function.
        if (thisEntity.getPosition().getY() > 
                otherEntity.getPosition().getX()) {
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
        
        return BIG_G * 
                thisEntity.getBody().getMass() * 
                otherEntity.getBody().getMass() /
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
     * Apply a force to an entity over the time step.
     * @param entity
     * @param force
     */
    public static void applyForce(Entity entity, Force force) {
        
        double mass = entity.getBody().getMass();
        
        double xAcc = force.getX() / mass;
        double yAcc = force.getY() / mass;
        
        entity.setXVel(entity.getXVel() + xAcc * Simulation.getTimeStep());
        entity.setYVel(entity.getYVel() + yAcc * Simulation.getTimeStep());
    }
    
    /**
     * Recalculate and apply an entity's position if it moves under its current
     * velocity for one time step.
     * @param entity
     */
    public static void projectEntity(Entity entity) {
        
        double xVel = entity.getXVel();
        double yVel = entity.getYVel();
        entity.setPositionDirectly(
                    entity.getPosition().getX() + 
                    xVel * Simulation.getTimeStep(),
                    entity.getPosition().getY() + 
                    yVel * Simulation.getTimeStep());
    }
    
    /**
     * For a list of entities, calculate their current shared centre of mass
     * (i.e. their orbital barycentre).
     * @param entities
     * @return Position
     */
    public static Position calculateBarycentre(List<Entity> entities) {
        
        Entity combinedEntity = entities.get(0);
        
        for (Entity entity : entities) {
            combinedEntity = getCentreOfMass(combinedEntity, entity);
        }
        
        return combinedEntity.getPosition();
    }
    
    /**
     * Given a pair of entities, return an Entity representing their shared
     * centre of mass.
     * @param thisEntity
     * @param otherEntity
     * @return Entity
     */
    public static Entity getCentreOfMass(Entity thisEntity, 
            Entity otherEntity) {
        
        double thisMass = thisEntity.getBody().getMass();
        double thisX = thisEntity.getPosition().getX();
        double thisY = thisEntity.getPosition().getY();
        
        double otherMass = otherEntity.getBody().getMass();
        double otherX = otherEntity.getPosition().getX();
        double otherY = otherEntity.getPosition().getY();
        
        // Get components of centre of mass using torque-summing technique
        double xComponent = ((thisMass * thisX) + (otherMass * otherX)) /
                (thisMass + otherMass);
        
        double yComponent = ((thisMass * thisY) + (otherMass * otherY)) /
                (thisMass + otherMass);
        
        Position centreOfMass = new Position(xComponent, yComponent);
        
        return new Entity(
                new Body("", thisMass + otherMass, 0, null),
                0,
                0,
                centreOfMass.getX(),
                centreOfMass.getY());
    }
    
}
