package entities;

import main.Camera;
import main.Display;
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
     * @return Entity.EntityForRendering
     */
    public static Entity.EntityForRendering constructEntityForRendering(
            Entity entity) {
        
        double entityDisplayFactor = Simulation.getEntityDisplayFactor();
        double sizedScaleFactor = Simulation.getSizedScaleFactor();
        double windowSize = Display.WINDOW_SIZE;
        
        double radius = entity.getBody().getRadius();
        double xEntity = entity.getPosition().getX();
        double yEntity = entity.getPosition().getY();
        
        double xCamera = Camera.getFocus().getX();
        double yCamera = Camera.getFocus().getY();
        
        int xForRendering = (int) (
                ((xEntity - xCamera - radius * entityDisplayFactor) /
                sizedScaleFactor) + windowSize / 2);
        
        int yForRendering = (int) (
                ((yEntity - yCamera - radius * entityDisplayFactor) /
                sizedScaleFactor) + windowSize / 2);
        
        int diameterForRendering = (int) (
                radius * 2 * entityDisplayFactor / sizedScaleFactor);
        
        return new Entity.EntityForRendering(
                xForRendering, yForRendering, diameterForRendering);
    }
    
}
