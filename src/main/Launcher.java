package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import entities.Body;
import entities.Entity;
import physics.Physics;

/**
 * Class containing the input arguments for the simulation and the main method.
 * 
 * @author Eddie Summers
 */
public class Launcher {
    
    public static void main(String[] args) {
        
        /**
         * Body library for use in entity creation
         * TODO: Revise responsibility for this
         */
        Body earthBody = new Body("Earth", 5.97 * Math.pow(10, 24), 
                6.37 * Math.pow(10, 6), Color.BLUE);
        Body marsBody = new Body("Mars", 6.42 * Math.pow(10, 23),
                3.39 * Math.pow(10, 6), Color.RED);
        Body moonBody = new Body("Moon", 7.34 * Math.pow(10, 22),
                1.74 * Math.pow(10, 6), Color.GRAY);
        Body jupiterBody = new Body("Jupiter", 1.90 * Math.pow(10, 27),
                7.15 * Math.pow(10, 7), Color.ORANGE);
        Body venusBody = new Body("Venus", 4.87 * Math.pow(10, 24),
                6.05 * Math.pow(10, 6), Color.YELLOW);
        Body sunBody = new Body("Sun", 1.99 * Math.pow(10, 30),
                6.96 * Math.pow(10, 8), Color.WHITE);
        
        // Create simulation entities
        List<Entity> entities = new ArrayList<>();
        entities.add(new Entity(earthBody, 0, 0, 0, 0));
        entities.add(new Entity(venusBody, 
                Math.pow(10, 3.3), 0, 0, Math.pow(10, 8)));
        entities.add(new Entity(moonBody,
                0, Math.pow(10, 3.2), -1 * Math.pow(10, 8.3), 0));
        entities.add(new Entity(marsBody,
                0, -1 * Math.pow(10, 3.3), Math.pow(10, 8.3), 0));
        
        // Create main camera using entity starting positions and window size
        Camera camera = new Camera(
                Physics.calculateBarycentre(entities), Display.WINDOW_SIZE);
        
        // Define time acceleration factor (values > 10^5.2 not recommended)
        double timeAcceleration = Math.pow(10, 4.8);
        
        // Define initial scale factor
        double initialScaleFactor = Math.pow(10, 9);
        
        // Define how many times larger than their actual scale bodies will be drawn
        double entityDisplayFactor = 1;
        
        // Begin simulation
        Simulation sim = new Simulation(
                entities, 
                timeAcceleration, 
                initialScaleFactor,
                entityDisplayFactor,
                camera);
        
        sim.run();
        
    }
    
}
