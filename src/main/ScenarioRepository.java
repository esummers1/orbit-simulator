package main;

import java.util.ArrayList;
import java.util.List;

import entities.Body;
import entities.Entity;

/**
 * Class responsible for storing and providing Scenarios.
 * 
 * @author Eddie Summers
 */
public class ScenarioRepository {
    
    private List<Scenario> scenarios;
    
    public ScenarioRepository(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }
    
    /**
     * Return a ScenarioRepository containing all pre-loaded Scenarios.
     * @return ScenarioRepository
     */
    public static ScenarioRepository retrieveAllScenarios() {
        
        List<Scenario> allScenarios = new ArrayList<>();
        allScenarios.add(createEarthMoonScenario());
        
        return new ScenarioRepository(allScenarios);
    }
    
    /**
     * Create a Scenario representing the Earth-Moon system.
     * @return Scenario
     */
    private static Scenario createEarthMoonScenario() {
        
        String name = "Earth and Moon";
        
        List<Entity> entities = new ArrayList<>();
        entities.add(new Entity(Body.EARTH, 0, 0, 0, 0));
        entities.add(new Entity(
                Body.MOON, 1.022 * Math.pow(10, 3), 0, 0, 4 * Math.pow(10, 8)));
        
        double timeAcceleration = Math.pow(10, 4.8);
        double overlayZoomFactor = 3;
        
        return new Scenario(
                name, entities, timeAcceleration, overlayZoomFactor);
    }
    
    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }
    
}
