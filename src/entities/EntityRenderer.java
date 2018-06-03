package entities;

import main.Camera;
import main.Simulation;

/**
 * Utility class reponsible for producing EntityForRendering objects.
 * 
 * @author Eddie Summers
 */
public abstract class EntityRenderer {
    
    /**
     * Create an EntityForRendering object for the panel component to draw.
     * @param entity
     * @param scale
     * @param camera
     * @return Entity.EntityForRendering
     */
    public static Entity.EntityForRendering constructEntityForRendering(
            Entity entity, double scale, Camera camera) {
        
        double entityDisplayFactor = Simulation.getEntityDisplayFactor();
        
        double radius = entity.getBody().getRadius();
        double xEntity = entity.getPosition().getX();
        double yEntity = entity.getPosition().getY();
        
        double xCamera = camera.getFocus().getX();
        double yCamera = camera.getFocus().getY();
        
        // Translate Entity position based on camera location
        xEntity -= xCamera;
        yEntity -= yCamera;
        
        int xForRendering = (int) (
                ((xEntity - radius * entityDisplayFactor) /
                scale) + camera.getTargetSize() / 2);
        
        int yForRendering = (int) (
                ((yEntity - radius * entityDisplayFactor) /
                scale) + camera.getTargetSize() / 2);
        
        int diameterForRendering = (int) (
                radius * 2 * entityDisplayFactor / scale);
        
        return new Entity.EntityForRendering(
                xForRendering, yForRendering, diameterForRendering);
    }
    
}
