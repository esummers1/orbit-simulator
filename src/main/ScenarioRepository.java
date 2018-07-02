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
        allScenarios.add(createEarthMarsVenusCollisionScenario());
        
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

    private static Scenario createEarthMarsVenusCollisionScenario() {

        String name = "Collision of Earth, Mars and Venus";

        List<Entity> entities = new ArrayList<>();
        entities.add(new Entity(Body.EARTH, 0, 0, 0, 0));
        entities.add(new Entity(
                Body.VENUS, -1 * Math.pow(10, 3), 0, 0, 4 * Math.pow(10, 7.5)));
        entities.add(new Entity(
                Body.MARS, 0, -1 * Math.pow(10, 3), 4 * Math.pow(10, 7.5), 0));

        double timeAcceleration = Math.pow(10, 4.2);
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
