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

        // Retrieve all default Scenarios
        List<Scenario> scenarios = 
                ScenarioRepository.retrieveAllScenarios().getScenarios();

        // Prompt user to select Scenario
        InputProvider provider = new InputProvider();
        Scenario scenario = provider.selectScenario(scenarios);

        // Read Simulation data from Scenario
        List<Entity> entities = scenario.getEntities();
        double timeAcceleration = scenario.getTimeAcceleration();
        double overlayZoomFactor = scenario.getOverlayZoomFactor();
        double initialScaleFactor =
                Physics.calculateAppropriateScaleFactor(scenario.getEntities());

        // Create main camera using entity starting positions and window size
        Camera camera = new Camera(
                Physics.calculateBarycentre(entities), Display.WINDOW_SIZE);
        
        // Begin simulation
        Simulation sim = new Simulation(
                entities, 
                timeAcceleration, 
                initialScaleFactor,
                overlayZoomFactor,
                camera);

        sim.run();
        
    }
    
}
