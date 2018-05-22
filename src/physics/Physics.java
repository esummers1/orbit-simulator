package physics;

import java.util.List;

import entities.Entity;
import main.Simulation;

/**
 * Class containing physics utility methods.
 * 
 * @author Eddie Summers
 */
public abstract class Physics {
    
    // Universal gravitational constant
    public static final double BIG_G = 6.674 * Math.pow(10, -11);
    
    /**
     * Calculate the gravitational attraction force from otherEntity upon
     * thisEntity, returned as an XYVector.
     * @param thisEntity
     * @param otherEntity
     * @return XYVector
     */
    public static XYVector computeGravitationalForce(Entity thisEntity, 
            Entity otherEntity) {
        
        double gravity = getGravityMagnitude(thisEntity, otherEntity);
        
        double bearing = Geometry.calculateBearing(
                thisEntity.getPosition(), otherEntity.getPosition());
        
        return Geometry.convertToXYVector(new BearingVector(gravity, bearing));        
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
        
        double distance = Geometry.getDistance(
                thisEntity.getPosition(), otherEntity.getPosition());
        
        return BIG_G * 
                thisEntity.getBody().getMass() * 
                otherEntity.getBody().getMass() /
                Math.pow(distance, 2);
    }
    
    /**
     * For a list of entities, calculate their current shared centre of mass
     * (i.e. their orbital barycentre).
     * @param entities
     * @return Position
     */
    public static Position calculateBarycentre(List<Entity> entities) {
        
        double xTorques = 0;
        double yTorques = 0;
        double masses = 0;
        
        for (Entity entity : entities) {
            xTorques += 
                    (entity.getBody().getMass() * entity.getPosition().getX());
            yTorques +=
                    (entity.getBody().getMass() * entity.getPosition().getY());
            masses += entity.getBody().getMass();
        }
        
        return new Position(xTorques / masses, yTorques / masses);
    }
    
    /**
     * Recalculate and apply an Entity's position if it moves under its current
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
     * Apply a force in the form of an XYVector to an Entity over one time step.
     * @param entity
     * @param force
     */
    public static void applyForce(Entity entity, XYVector force) {
        
        double mass = entity.getBody().getMass();
        
        double xAcc = force.getX() / mass;
        double yAcc = force.getY() / mass;
        
        entity.setXVel(entity.getXVel() + xAcc * Simulation.getTimeStep());
        entity.setYVel(entity.getYVel() + yAcc * Simulation.getTimeStep());
    }
    
}
