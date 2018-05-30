package main;

import physics.Position;

/**
 * Class responsible for storing the point in the simulated worldspace around 
 * which the simulation frame should be rendered (i.e. the middle of the view).
 * 
 * @author Eddie Summers
 */
public class Camera {
    
    private Position focus;
    private int targetSize;

    public Camera(Position focus, int targetSize) {
        this.focus = focus;
        this.targetSize = targetSize;
    }
    
    public Position getFocus() {
        return focus;
    }

    public void setFocus(Position focus) {
        this.focus = focus;
    }

    public int getTargetSize() {
        return targetSize;
    }
    
}
