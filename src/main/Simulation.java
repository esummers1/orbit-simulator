package main;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import entities.Body;
import entities.Entity;
import physics.Geometry;
import physics.Physics;
import physics.Position;
import physics.XYVector;

/**
 * Class responsible for governing the flow of the simulation.
 * 
 * @author Eddie Summers
 */
public class Simulation {
    
    private List<Entity> entities;
    private Display display;
    
    // Steps per second
    private static final int FRAME_RATE = 300;
    
    // Number of simulated seconds that pass per simulation step
    private static double timeStep;
    
    // Spatial scale factor with window size taken into account, i.e. m/px
    private static double sizedScaleFactor;
    
    // Entity rendering scale factor (Entities are this many times larger)
    private static double entityDisplayFactor;
    
    public Simulation(
            List<Entity> entities, 
            double timeAcceleration,
            double scaleFactor,
            double entityDisplayFactor) {
        
        this.entities = entities;
        Simulation.timeStep = timeAcceleration / FRAME_RATE;
        Simulation.sizedScaleFactor = scaleFactor / Display.WINDOW_SIZE;
        Simulation.entityDisplayFactor = entityDisplayFactor;
        this.display = new Display(this);
    }
    
    /**
     * Return a list of the names of all entities in the simulaton.
     * @return List<String>
     */
    public List<String> getEntityNames() {
        List<String> names = new ArrayList<>();
        
        for (Entity entity : entities) {
            names.add(entity.getBody().getName());
        }
        
        return names;
    }
    
    public static double getTimeStep() {
        return timeStep;
    }
    
    public static double getSizedScaleFactor() {
        return sizedScaleFactor;
    }
    
    public static double getEntityDisplayFactor() {
        return entityDisplayFactor;
    }
    
    public Display getDisplay() {
        return display;
    }
    
    public List<Entity> getEntities() {
        return entities;
    }
    
    /**
     * Main simulation loop.
     */
    public void run() {
        
        while(true) {
            
            // Do actual simulation work
            updatePhysics();
            render();
            
            // Wait for next step to begin
            try {
                Thread.sleep((long) (1000 / FRAME_RATE));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    /** 
     * Physics operations.
     */
    private void updatePhysics() {
        
        // Calculate gravity 
        for (Entity entity : entities) {
            XYVector resultantGravity = getResultantGravity(entity);
            Physics.applyForce(entity, resultantGravity);
        }
        
        // Move each entity over one time step according to new velocity
        for (Entity entity : entities) {
            Physics.projectEntity(entity);
        }
        
        /**
         * Detect and handle collisions as they occur.
         * 
         * Currently ignores concurrent modification exception, most likely
         * caused by editing the list which the for block is looping through.
         * Have ignored as it does not seem to affect behaviour.
         */
        try {
            for (Entity entity : entities) {
                handleCollisions(entity);
            }
        } catch (ConcurrentModificationException e) {}
        
        // Update camera with new situation
        Camera.setCentreOfFrame(Physics.calculateBarycentre(entities));
    }
    
    /**
     * Detect and handle collisions for the given Entity.
     * @param entity
     */
    private void handleCollisions(Entity entity) {
        
        List<Entity> otherEntities = getAllOtherEntities(entity);
        
        for (Entity otherEntity : otherEntities) {
            if (Physics.detectCollision(entity, otherEntity)) {
                Entity newEntity = mergeEntities(entity, otherEntity);
                
                entities.add(newEntity);
                entities.remove(entity);
                entities.remove(otherEntity);
                
                // Update list of entities for rendering
                display.getPanel().updateEntityList(this.entities);
                
                // Update window title
                String title = Display.createTitle(getEntityNames());
                display.getFrame().setTitle(title);
            }
        }
    }
    
    /**
     * Merge two Entities.
     * @param thisEntity
     * @param otherEntity
     * @return Entity
     */
    private Entity mergeEntities(Entity thisEntity, Entity otherEntity) {
        
        Body newBody = Physics.mergeBodies(
                thisEntity.getBody(), otherEntity.getBody());
        
        XYVector newVelocity = Physics.mergeVelocities(thisEntity, otherEntity);
        
        List<Entity> entities = new ArrayList<>();
        entities.add(thisEntity);
        entities.add(otherEntity);
        Position newPosition = Physics.calculateBarycentre(entities);
        
        return new Entity(
                newBody, 
                newVelocity.getX(), 
                newVelocity.getY(),
                newPosition.getX(),
                newPosition.getY());
    }
    
    /**
     * Return a single XYVector describing the gravitational pull from all other
     * Entities on the passed Entity.
     * @param entity
     * @return XYVector
     */
    private XYVector getResultantGravity(Entity entity) {
        
        List<Entity> otherEntities = getAllOtherEntities(entity);
        List<XYVector> gravitationalForces = 
                getGravitationalForces(entity, otherEntities);
        
        return Geometry.resolveVectors(gravitationalForces);        
    }
    
    /**
     * Return a list of XYVectors describing the gravitational pull from a list 
     * of Entities on a single Entity.
     * @param entity
     * @param otherEntities
     * @return List<XYVector>
     */
    private List<XYVector> getGravitationalForces(Entity entity, 
            List<Entity> otherEntities) {
        
        List<XYVector> gravitationalForces = new ArrayList<>();
        
        for (Entity otherEntity: otherEntities) {
            gravitationalForces.add(
                    Physics.computeGravitationalForce(entity, otherEntity));
        }
        
        return gravitationalForces;
    }
    
    /**
     * Given an Entity, return a list of all other Entities in the simulation.
     * @param entity
     * @return List<Entity>
     */
    private List<Entity> getAllOtherEntities(Entity entity) {
        
        List<Entity> otherEntities = new ArrayList<>();
        
        for (Entity potentialEntity : this.entities) {
            if (!potentialEntity.equals(entity)) {
                otherEntities.add(potentialEntity);
            }
        }
        
        return otherEntities;
    }
    
    /**
     * Render results of this step.
     */
    private void render() {
        display.getPanel().repaint();
    }
    
}
