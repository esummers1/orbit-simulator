package main;

import java.util.List;

import entities.Entity;
import physics.Geometry;
import physics.Physics;

/**
 * Class containing the input arguments for the simulation and the main method.
 * 
 * @author Eddie Summers
 */
public class Launcher {
    
    public static void main(String[] args) {
        
        List<Scenario> scenarios = 
                ScenarioRepository.retrieveAllScenarios().getScenarios();
        
        // TODO: Prompt user to select scenario in console
        List<Entity> entities = scenarios.get(0).getEntities();
        double timeAcceleration = scenarios.get(0).getTimeAcceleration();
        
        // Calculate starting scale factor using the positions of the entities
        double initialScaleFactor = 
                Physics.calculateAppropriateScaleFactor(entities);
        
        // Create main camera using entity starting positions and window size
        Camera camera = new Camera(
                Physics.calculateBarycentre(entities), Display.WINDOW_SIZE);
        
        // Begin simulation
        Simulation sim = new Simulation(
                entities, 
                timeAcceleration, 
                initialScaleFactor,
                camera);
        
        sim.run();
        
    }
    
}
