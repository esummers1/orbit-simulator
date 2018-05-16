package entities;

import java.awt.Color;

public class Moon extends Entity {
    
    private static final String NAME = "Moon";
    private static final double MASS = 7.34 * Math.pow(10, 22);
    private static final double RADIUS = 1.74 * Math.pow(10, 6);
    private static final Color COLOUR = Color.GRAY;
    
    public Moon(
            double xVel,
            double yVel,
            double x,
            double y) {
        super(NAME, MASS, RADIUS, xVel, yVel, x, y, COLOUR);
    }
    
}
