package entities;

import java.awt.Color;

import physics.Position;

public class PhysicsEntity extends Entity {
    
    private static final String NAME = "";
    private static final double RADIUS = 0;
    private static final Color COLOUR = null;
    
    public PhysicsEntity(double mass, Position position) {
        
        super(NAME, 
                mass, 
                RADIUS, 
                0, 
                0, 
                position.getX(), 
                position.getY(), 
                COLOUR);
    }
    
}
