package entities;

import java.awt.Color;

public class Mars extends Entity {
    
    private static final String NAME = "Mars";
    private static final double MASS = 6.42 * Math.pow(10, 23);
    private static final double RADIUS = 3.39 * Math.pow(10, 6);
    private static final Color COLOUR = Color.RED;
    
    public Mars(
            double xVel,
            double yVel,
            double x,
            double y) {
        super(NAME, MASS, RADIUS, xVel, yVel, x, y, COLOUR);
    }
    
}
