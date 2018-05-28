package main;

import physics.Position;

/**
 * Class responsible for storing the point in the simulated worldspace around 
 * which the simulation frame should be rendered (i.e. the middle of the view).
 * 
 * @author Eddie Summers
 */
public abstract class Camera {
    
    private static Position focus;

    public static Position getFocus() {
        return focus;
    }

    public static void setFocus(Position focus) {
        Camera.focus = focus;
    }
    
}
