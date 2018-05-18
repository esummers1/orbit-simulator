package main;

import java.util.ArrayList;
import java.util.List;

import entities.Earth;
import entities.Entity;
import entities.Moon;
import physics.Constants;
import physics.Physics;

public class Launcher {
    
    public static void main(String[] args) {
        
        // Create simultation entities
        List<Entity> entities = new ArrayList<>();
        
        Entity earth = new Earth(
                0,
                0,
                0.5 * Constants.WINDOW_SIZE * Constants.SCALE_FACTOR, 
                0.5 * Constants.WINDOW_SIZE * Constants.SCALE_FACTOR);
        entities.add(earth);
        
        Entity moon = new Moon(
                0,
                1.02 * Math.pow(10, 3),
                earth.getPosition().getX() + 3.84 * Math.pow(10, 8),
                earth.getPosition().getY());
        entities.add(moon);
        
        // Update camera with entity starting positions
        Physics.updateCamera(entities);
        
        // Define time acceleration factor
        double timeAcceleration = 100000;
        
        // Begin simulation
        Simulation sim = new Simulation(entities, timeAcceleration);
        sim.run();
        
    }
    
}
