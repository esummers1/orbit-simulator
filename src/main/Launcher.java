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
        
        Entity earth = new Entity(earthBody, 0, 0, 0, 0);
        entities.add(earth);
        
        Entity moon = new Entity(
                moonBody, 1.02 * Math.pow(10, 3), 0, 0, -4 * Math.pow(10, 8));
        entities.add(moon);
        
        // Update camera with entity starting positions
        Camera.setCentreOfFrame(Physics.calculateBarycentre(entities));
        
        // Define time acceleration factor (values > 5*10^5 not recommended)
        double timeAcceleration = Math.pow(10, 5.5);
        
        // Define simulation display factor
        double scaleFactor = Math.pow(10, 9);
        
        /**
         *  Define how many times larger than their actual scale bodies will be
         *  drawn
         */
        double entityDisplayFactor = 3;
        
        // Begin simulation
        Simulation sim = new Simulation(
                entities, 
                timeAcceleration, 
                scaleFactor,
                entityDisplayFactor);
        
        sim.run();
        
    }
    
}
