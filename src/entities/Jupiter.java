package entities;

import java.awt.Color;

public class Jupiter extends Entity {

    private static final String NAME = "Jupiter";
    private static final double MASS = 1.90 * Math.pow(10, 27);
    private static final double RADIUS = 7.15 * Math.pow(10, 7);
    private static final Color COLOUR = Color.ORANGE;
    
    public Jupiter(
            double xVel,
            double yVel,
            double x,
            double y) {
        super(NAME, MASS, RADIUS, xVel, yVel, x, y, COLOUR);
    
    }
}
