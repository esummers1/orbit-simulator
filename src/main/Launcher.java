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

        // Begin simulation
        Simulation sim = new Simulation(scenario);
        sim.run();
        
    }
    
}
