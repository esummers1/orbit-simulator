package main;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;

public class Simulation {
    
    private List<Entity> entities;
    private Display display;
    
    public Simulation(List<Entity> entities) {
        this.entities = entities;
        
        display = new Display(this);
    }
    
    public List<String> getEntityNames() {
        List<String> names = new ArrayList<>();
        
        for (Entity entity : entities) {
            names.add(entity.getName());
        }
        
        return names;
    }
    
    /**
     * Main simulation loop.
     */
    public void run() {
        
        while(true) {
            long startTime = System.currentTimeMillis();
            
            // Do actual simulation work
            updatePhysics();
            render();
            
            // Wait for next step to begin
            long endTime = System.currentTimeMillis();
            long deltaTime = endTime - startTime;
            
            try {
                Thread.sleep(
                        (long) ((1000 / Constants.FRAME_RATE) - deltaTime));
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
            Force resultantGravity = getResultantGravity(entity);
            Physics.applyForce(entity, resultantGravity);
        }
        
        // Move each entity over one time step according to new velocity.
        for (Entity entity : entities) {
            Physics.projectEntity(entity);
        }
    }
    
    /**
     * Return a single Force describing the gravitational pull from all other
     * Entities on the passed Entity.
     * @param entity
     * @return Force
     */
    private Force getResultantGravity(Entity entity) {
        
        List<Entity> otherEntities = getAllOtherEntities(entity);
        List<Force> gravitationalForces = 
                getGravitationalForces(entity, otherEntities);
        
        return Physics.resolveForces(gravitationalForces);        
    }
    
    /**
     * Return a list of Forces describing the gravitational pull from a list of
     * Entities on a single Entity.
     * @param entity
     * @param otherEntities
     * @return List<Force>
     */
    private List<Force> getGravitationalForces(Entity entity, 
            List<Entity> otherEntities) {
        
        List<Force> gravitationalForces = new ArrayList<>();
        
        for (Entity otherEntity: otherEntities) {
            gravitationalForces.add(
                    Physics.computeGravitationalForce(entity, otherEntity));
        }
        
        return gravitationalForces;
    }
    
    /**
     * Given an Entity, return a list of all other Entities in the simulation.
     * @param entity
     * @return Entity
     */
    private List<Entity> getAllOtherEntities(Entity entity) {
        
        List<Entity> otherEntities = new ArrayList<>();
        
        for (Entity potentialEntity : entities) {
            if (!potentialEntity.equals(entity)) {
                otherEntities.add(potentialEntity);
            }
        }
        
        return otherEntities;
    }
    
    /**
     * Render next frame.
     */
    private void render() {
        display.getPanel().setObjects(entities);
        display.getPanel().repaint();
    }
    
}
