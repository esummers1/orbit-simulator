package main;

import java.util.ArrayList;
import java.util.List;

import entities.Earth;
import entities.Entity;
import entities.Mars;
import entities.Moon;

public class Launcher {
    
    public static void main(String[] args) {
        
        List<Entity> entities = new ArrayList<>();
        
        /**
         * Create entities.
         * TODO: Ask user for this in console?
         */
        Entity earth = new Earth(
                0,
                0.1 * Math.pow(10, 3),
                0.5 * Constants.WINDOW_SIZE * Constants.SCALE_FACTOR, 
                0.5 * Constants.WINDOW_SIZE * Constants.SCALE_FACTOR);
        
        entities.add(earth);
        
        Entity mars = new Mars(
                0,
                -1.02 * Math.pow(10, 3),
                earth.getX() - 3.84 * Math.pow(10, 8),
                earth.getY());
        
        entities.add(mars);
        
        Simulation sim = new Simulation(entities);
        sim.run();
        
    }
    
}
