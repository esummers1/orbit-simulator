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
    
    // Time fields used for determining which steps to render.
    private long accumulatedTime;
    private long currentTime;
    
    // Keys used for selecting Entities to focus on.
    private static final char[] FOCUS_KEYS = 
        {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    
    private static final char CENTRE_KEY = 'c';
    
    private static final char ZOOM_IN_KEY = '+';
    
    private static final char ZOOM_OUT_KEY = '-';
    
    // Steps per second
    private static final int FRAME_RATE = 500;
    
    // Delay (in milliseconds) that simulations leaves between renderings.
    private static final double FRAME_DELAY = 1000 / 120;
    
    /*
     * The factor by which sizedScaleFactor is multiplied or divided when zoom
     * input is given.
     */
    private static final double SCALE_FACTOR_INCREMENT = 1.01;
    
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
        
        for (char key : FOCUS_KEYS) {
            if (currentKey == key) {
                int entityIndex = key - 49;
                
                // Try to select as focus the Entity of this number
                try {
                    currentFocus = entities.get(entityIndex);
                } catch (Exception e) {}
            }
            
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
     * Detect key press and store the key's character.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        currentKey = e.getKeyChar();
    }
    
    /**
     * (Hacky) set current key to an unused char on key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        currentKey = '?';
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
}
