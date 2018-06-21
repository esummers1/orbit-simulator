package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
public class Simulation implements KeyListener {
    
    private List<Entity> entities;
    private Display display;
    private char currentKey;
    private Camera camera;
    
    private boolean isCyclingForwards = false;
    private boolean isCyclingBackwards = false;
    private static boolean isDrawingOverlay = false;
    
    // Time fields used for determining which steps to render.
    private long accumulatedTime;
    private long currentTime;
    
    /*
     * Entity which is the current focus of the Camera. If set to null, the
     * Camera will look at the simulation's barycentre.
     */
    private static Entity currentFocus;
    
    // Number of simulated seconds that pass per simulation step
    private static double timeStep;
    
    // Spatial scale factor with window size taken into account, i.e. m/px
    private static double sizedScaleFactor;
    
    // Entity rendering scale factor (Entities are this many times larger)
    private static double entityDisplayFactor;
    
    // Steps per second
    private static final int FRAME_RATE = 500;
    
    // Delay (in milliseconds) that simulation leaves between renderings.
    private static final double FRAME_DELAY = 1000 / 120;
    
    /*
     * The factor by which sizedScaleFactor is multiplied or divided when zoom
     * input is given.
     */
    private static final double SCALE_FACTOR_INCREMENT = 1.01;
    
    // Key constants
    private static final char CYCLE_FORWARD_KEY = ']';
    private static final char CYCLE_BACKWARD_KEY = '[';
    private static final char CENTRE_KEY = 'c';
    private static final char ZOOM_IN_KEY = '+';
    private static final char ZOOM_OUT_KEY = '-';
    private static final char DRAW_OVERLAY_KEY = 'o';
    
    public Simulation(
            List<Entity> entities, 
            double timeAcceleration,
            double scaleFactor,
            double entityDisplayFactor,
            Camera camera) {
        
        this.entities = entities;
        Simulation.timeStep = timeAcceleration / FRAME_RATE;
        Simulation.sizedScaleFactor = scaleFactor / Display.WINDOW_SIZE;
        Simulation.entityDisplayFactor = entityDisplayFactor;
        this.camera = camera;
        
        this.display = new Display(this);
        this.accumulatedTime = 0;
        this.currentTime = System.currentTimeMillis();
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
    
    public static Entity getCurrentFocus() {
        return Simulation.currentFocus;
    }
    
    public Camera getCamera() {
        return camera;
    }
    
    public static boolean getIsDrawingOverlay() {
        return isDrawingOverlay;
    }
    
    /**
     * Main simulation loop.
     */
    public void run() {
        
        while(true) {
            
            // Do actions required based on keyboard input
            handleInput();
            
            // Do actual simulation work
            updatePhysics();
            
            // Render, if this is a step that should be rendered
            if (checkRendering()) {
                render();
            }
            
            // Wait for next step to begin
            try {
                Thread.sleep((long) (1000 / FRAME_RATE));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    /**
     * Respond to user key inputs.
     */
    private void handleInput() {
        
        if (isCyclingForwards) {
            currentFocus = retrieveNextEntityInList(currentFocus, entities);
            isCyclingForwards = false;
            updateSimulationTitle();
        }
        
        if (isCyclingBackwards) {
            currentFocus = retrievePreviousEntityInList(currentFocus, entities);
            isCyclingBackwards = false;
            updateSimulationTitle();
        }
        
        if (currentKey == CENTRE_KEY) {
            currentFocus = null;
            updateSimulationTitle();
        }
        
        if (currentKey == ZOOM_IN_KEY) {
            sizedScaleFactor /= SCALE_FACTOR_INCREMENT;
        }
        
        if (currentKey == ZOOM_OUT_KEY) {
            sizedScaleFactor *= SCALE_FACTOR_INCREMENT;
        }
        
        if (currentKey == DRAW_OVERLAY_KEY) {
            isDrawingOverlay = true;
        } else {
            isDrawingOverlay = false;
        }
    }
    
    
    /**
     * Given an Entity and a list of Entities, return the Entity after the given
     * one in the list, unless:
     *  - It does not appear
     *  - It is the last element
     *  
     * In either of these cases, return the first element.
     * 
     * @param entity
     * @param entities
     * @return Entity
     */
    private Entity retrieveNextEntityInList(
            Entity entity, List<Entity> entities) {
        
        if (entities.contains(entity) && 
                entities.indexOf(entity) < (entities.size() - 1)) {
            return entities.get(entities.indexOf(entity) + 1);
        }
        
        return entities.get(0);
    }
    
    /**
     * Given an Entity and a list of Entities, return the Entity before the 
     * given one in the list, unless:
     *  - It does not appear
     *  - It is the first element
     *  
     * In either of these cases, return the last element.
     * 
     * @param entity
     * @param entities
     * @return Entity
     */
    private Entity retrievePreviousEntityInList(
            Entity entity, List<Entity> entities) {
        
        if (entities.contains(entity) && entities.indexOf(entity) > 0) {
            return entities.get(entities.indexOf(entity) - 1);
        }
        
        return entities.get(entities.size() - 1);
    }
    
    /**
     * Update the current title of the window.
     * - Existing Entities
     * - Current focused Entity
     */
    private void updateSimulationTitle() {
        String title = Display.createTitle(getEntityNames());
        display.getFrame().setTitle(title);
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
        
        // If the current focus Entity has been merged, reset focus to centre
        if (!entities.contains(currentFocus)) {
            currentFocus = null;
            updateSimulationTitle();
        }
        
        // Update camera with new situation
        if (currentFocus == null) {
            camera.setFocus(Physics.calculateBarycentre(entities));
        } else {
            camera.setFocus(currentFocus.getPosition());
        }
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
                
                updateSimulationTitle();
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
     * Check if the simulation should be rendered in the current step.
     * @return boolean
     */
    private boolean checkRendering() {
        
        long newCurrentTime = System.currentTimeMillis();
        accumulatedTime += newCurrentTime - currentTime;
        currentTime = newCurrentTime;
        
        if (accumulatedTime >= FRAME_DELAY) {
            accumulatedTime = 0;
            return true;
        }
        
        return false;
    }
    
    /**
     * Render results of this step.
     */
    private void render() {
        display.getPanel().repaint();
    }
    
    /**
     * If the pressed key is one of the keys which can meaningfully be 'held'
     * for multiple frames, store it as the current key.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        
        char key = e.getKeyChar();
        
        if (
                key == ZOOM_IN_KEY ||
                key == ZOOM_OUT_KEY ||
                key == CENTRE_KEY ||
                key == DRAW_OVERLAY_KEY) {
            
            currentKey = key;
        }
        
    }
    
    /**
     * Detect focus inputs, or otherwise set the current key to an unused
     * character (hacky).
     */
    @Override
    public void keyReleased(KeyEvent e) {
        
        char key = e.getKeyChar();
        
        if (key == CYCLE_FORWARD_KEY) {
            isCyclingForwards = true;
        } else if (key == CYCLE_BACKWARD_KEY) {
            isCyclingBackwards = true;
        } else if (key == CENTRE_KEY){
            currentKey = key;
        } else {
            currentKey = '?';
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
}
