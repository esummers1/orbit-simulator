package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import entities.Body;
import entities.Entity;
import entities.EntityShooter;
import entities.EntityShot;
import physics.Geometry;
import physics.Physics;
import physics.Position;
import physics.XYVector;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

/**
 * Class responsible for governing the flow of the simulation.
 * 
 * @author Eddie Summers
 */
public class Simulation extends MouseInputAdapter implements KeyListener {
    
    private List<Entity> entities;
    private List<Body> availableBodies;
    private Display display;
    private char currentKey;
    private Camera camera;
    private double overlayZoomFactor;
    
    private boolean isCyclingFocusForwards = false;
    private boolean isCyclingFocusBackwards = false;
    private boolean isCyclingBodyForwards = false;
    private boolean isCyclingBodyBackwards = false;
    private static boolean isDrawingOverlay = false;
    private static boolean isDrawingNameLabels = true;
    
    // Time fields used for determining which steps to render.
    private long accumulatedTime;
    private long currentTime;

    // Fields used for taking input for the Entity shooting feature.
    private Point startLocation;
    private Point endLocation;
    private long dragStartTime;
    private long dragEndTime;
    
    /*
     * Entity which is the current focus of the Camera. If set to null, the
     * Camera will look at the simulation's barycentre.
     */
    private Entity currentFocus;

    // Currently selected Body for the Entity shooting feature.
    private Body currentBodyForShooting;
    
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
     * The factor by which scale factors are multiplied or divided when zoom
     * input is given.
     */
    private static final double SCALE_FACTOR_INCREMENT = 1.01;
    
    // Key constants
    private static final char CYCLE_FOCUS_FORWARD_KEY = ']';
    private static final char CYCLE_FOCUS_BACKWARD_KEY = '[';
    private static final char CYCLE_BODY_FORWARD_KEY = '.';
    private static final char CYCLE_BODY_BACKWARD_KEY = ',';
    private static final char ENTITY_ENLARGE_KEY = '}';
    private static final char ENTITY_DIMINISH_KEY = '{';
    private static final char ENTITY_SCALE_RESET_KEY = 'r';
    private static final char CENTRE_KEY = 'c';
    private static final char ZOOM_IN_KEY = '+';
    private static final char ZOOM_OUT_KEY = '-';
    private static final char RESET_ZOOM_KEY = 'z';
    private static final char DRAW_OVERLAY_KEY = 'o';
    private static final char DRAW_NAME_LABEL_KEY = 'n';
    
    public Simulation(Scenario scenario) {

        this.entities = scenario.getEntities();
        this.availableBodies = Body.getDefaultBodies();
        this.overlayZoomFactor = scenario.getOverlayZoomFactor();
        this.currentBodyForShooting = availableBodies.get(0);

        Simulation.timeStep = scenario.getTimeAcceleration() / FRAME_RATE;
        Simulation.sizedScaleFactor =
                Physics.calculateAppropriateScaleFactor(entities) /
                Display.WINDOW_SIZE;
        Simulation.entityDisplayFactor = 1;

        this.camera = new Camera(
                Physics.calculateBarycentre(entities), Display.WINDOW_SIZE);
        this.display = new Display(this);

        this.accumulatedTime = 0;
        this.currentTime = System.currentTimeMillis();
    }
    
    /**
     * Return a list of the names of all entities in the simulation.
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
    
    public List<Entity> getEntities() {
        return entities;
    }

    public double getOverlayZoomFactor() {
        return overlayZoomFactor;
    }
    
    public Entity getCurrentFocus() {
        return currentFocus;
    }

    public Body getCurrentBodyForShooting() {
        return currentBodyForShooting;
    }
    
    public Camera getCamera() {
        return camera;
    }
    
    public static boolean getIsDrawingOverlay() {
        return isDrawingOverlay;
    }

    public static boolean getIsDrawingNameLabels() {
        return isDrawingNameLabels;
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
        
        if (isCyclingFocusForwards) {
            currentFocus = retrieveNextEntityInList(currentFocus, entities);
            isCyclingFocusForwards = false;
            updateSimulationTitle(this);
        }
        
        if (isCyclingFocusBackwards) {
            currentFocus = retrievePreviousEntityInList(currentFocus, entities);
            isCyclingFocusBackwards = false;
            updateSimulationTitle(this);
        }

        if (isCyclingBodyForwards) {
            currentBodyForShooting = retrieveNextBodyInList(
                    currentBodyForShooting, availableBodies);
            isCyclingBodyForwards = false;
            updateSimulationTitle(this);
        }

        if (isCyclingBodyBackwards) {
            currentBodyForShooting = retrievePreviousBodyInList(
                    currentBodyForShooting, availableBodies);
            isCyclingBodyBackwards = false;
            updateSimulationTitle(this);
        }
        
        if (currentKey == CENTRE_KEY) {
            currentFocus = null;
            updateSimulationTitle(this);
            resetCurrentKey();
        }
        
        if (currentKey == ZOOM_IN_KEY) {
            sizedScaleFactor /= SCALE_FACTOR_INCREMENT;
        }
        
        if (currentKey == ZOOM_OUT_KEY) {
            sizedScaleFactor *= SCALE_FACTOR_INCREMENT;
        }
        
        if (currentKey == RESET_ZOOM_KEY) {
            sizedScaleFactor =
                    Physics.calculateAppropriateScaleFactor(entities) /
                    Display.WINDOW_SIZE;
            resetCurrentKey();
        }
        
        if (currentKey == ENTITY_ENLARGE_KEY) {
            entityDisplayFactor *= SCALE_FACTOR_INCREMENT;
        }
        
        if (currentKey == ENTITY_DIMINISH_KEY) {
            entityDisplayFactor /= SCALE_FACTOR_INCREMENT;
        }
        
        if (currentKey == ENTITY_SCALE_RESET_KEY) {
            entityDisplayFactor = 1;
            resetCurrentKey();
        }
        
        if (currentKey == DRAW_OVERLAY_KEY) {
            isDrawingOverlay = true;
        } else {
            isDrawingOverlay = false;
        }

        if (currentKey == DRAW_NAME_LABEL_KEY) {
            isDrawingNameLabels = !isDrawingNameLabels;
            resetCurrentKey();
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

        if (entities.size() == 0) {
            return entity;
        }

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

        if (entities.size() == 0) {
            return entity;
        }
        if (entities.contains(entity) && entities.indexOf(entity) > 0) {
            return entities.get(entities.indexOf(entity) - 1);
        }
        
        return entities.get(entities.size() - 1);
    }

    /**
     * Given a Body and a list of Bodies, return the Body after the given one
     * in the list, unless:
     *  - It does not appear
     *  - It is the last element
     *
     * In either of these cases, return the first element.
     *
     * @param body
     * @param bodies
     * @return Body
     */
    private Body retrieveNextBodyInList(Body body, List<Body> bodies) {

        if (bodies.size() == 0) {
            return body;
        }

        if (bodies.contains(body) &&
                bodies.indexOf(body) < (bodies.size() - 1)) {
            return bodies.get(bodies.indexOf(body) + 1);
        }

        return bodies.get(0);
    }

    /**
     * Given a Body and a list of Bodies, return the Body before the given one
     * in the list, unless:
     *  - It does not appear
     *  - It is the first element
     *
     * In either of these cases, return the last element.
     *
     * @param body
     * @param bodies
     * @return Body
     */
    private Body retrievePreviousBodyInList(Body body, List<Body> bodies) {

        if (bodies.size() == 0) {
            return body;
        }

        if (bodies.contains(body) && bodies.indexOf(body) > 0) {
            return bodies.get(bodies.indexOf(body) - 1);
        }

        return bodies.get(bodies.size() - 1);
    }
    
    /**
     * Update the current title of the window.
     * - Existing Entities
     * - Current focused Entity
     * - Current selected Body for shooting
     */
    private void updateSimulationTitle(Simulation simulation) {
        String title = Display.createTitle(simulation);
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
            updateSimulationTitle(this);
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
                
                updateSimulationTitle(this);
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
     * Given a mouse-drag input, create and project an Entity using the
     * currently-selected Body. The mouse drag is converted to a scaled velocity
     * and the Entity is delivered at the point at which the drag ended.
     * @param start
     * @param end
     */
    private void shootEntity(Point start, Point end, long duration) {

        EntityShot shot = new EntityShot(
                currentBodyForShooting,
                start,
                end,
                camera,
                duration,
                sizedScaleFactor);

        double timeAcceleration = timeStep * FRAME_RATE;

        Entity entity =
                EntityShooter.createEntityForShooting(shot, timeAcceleration);

        entities.add(entity);
        updateSimulationTitle(this);
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
                key == ENTITY_ENLARGE_KEY ||
                key == ENTITY_DIMINISH_KEY ||
                key == DRAW_OVERLAY_KEY) {
            
            currentKey = key;
        }
        
    }
    
    /**
     * Detect "switch" type inputs, i.e. those that cannot be meaningfully held.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        
        char key = e.getKeyChar();
        
        if (key == CYCLE_FOCUS_FORWARD_KEY) {

            isCyclingFocusForwards = true;

        } else if (key == CYCLE_FOCUS_BACKWARD_KEY) {

            isCyclingFocusBackwards = true;

        } else if (key == CYCLE_BODY_FORWARD_KEY) {

            isCyclingBodyForwards = true;

        } else if (key == CYCLE_BODY_BACKWARD_KEY) {

            isCyclingBodyBackwards = true;

        } else if (
                key == CENTRE_KEY ||
                key == ENTITY_SCALE_RESET_KEY || 
                key == RESET_ZOOM_KEY ||
                key == DRAW_NAME_LABEL_KEY) {

            currentKey = key;

        } else {

            // Reset key for next sampling
            resetCurrentKey();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        dragStartTime = System.currentTimeMillis();

        startLocation = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(
                startLocation, display.getPanel());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        dragEndTime = System.currentTimeMillis();

        endLocation = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(
                endLocation, display.getPanel());

        long duration = (dragEndTime - dragStartTime);

        shootEntity(startLocation, endLocation, duration);
    }

    /**
     * Hacky - set current key to an unused character.
     */
    private void resetCurrentKey() {
        currentKey = '?';
    }
    
}
