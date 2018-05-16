package entities;

import java.awt.Color;

public class Earth extends Entity {
    
    private static final String NAME = "Earth";
    private static final double MASS = 5.97 * Math.pow(10, 24);
    private static final double RADIUS = 6.37 * Math.pow(10, 6);
    private static final Color COLOUR = Color.BLUE;
    
    public Earth(
            double xVel,
            double yVel,
            double x,
            double y) {
        super(NAME, MASS, RADIUS, xVel, yVel, x, y, COLOUR);
    }
    
}
