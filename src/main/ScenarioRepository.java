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
        allScenarios.add(createInnerPlanetsScenario());
        
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
                Body.MOON, 1.02e3, 0, 0, 4.00e8));
        
        double timeAcceleration = 6.00e4;
        double overlayZoomFactor = 3;
        
        return new Scenario(
                name, entities, timeAcceleration, overlayZoomFactor);
    }

    /**
     * Create a fictional Scenario representing Earth, Mars and Venus orbiting
     * in close proximity and coalescing.
     * @return Scenario
     */
    private static Scenario createEarthMarsVenusCollisionScenario() {

        String name = "Collision of Earth, Mars and Venus";

        List<Entity> entities = new ArrayList<>();
        entities.add(new Entity(Body.EARTH, 0, 0, 0, 0));
        entities.add(new Entity(
                Body.VENUS, -1.00e3, 0, 0, 1.26e8));
        entities.add(new Entity(
                Body.MARS, 0, -1.00e3, 1.26e8, 0));

        double timeAcceleration = 1.60e4;
        double overlayZoomFactor = 3;

        return new Scenario(
                name, entities, timeAcceleration, overlayZoomFactor);
    }

    /**
     * Create a semi-realistic Scenario representing the Sun, the inner planets
     * and their moons (orbital phase angles not correct).
     * @return Scenario
     */
    private static Scenario createInnerPlanetsScenario() {

        String name = "Inner planets and moons";

        List<Entity> entities = new ArrayList<>();
        entities.add(new Entity(Body.SUN, 0, 0, 0, 0));
        entities.add(new Entity(Body.MERCURY, 0, -5.66e4, 4.60e10, 0));
        entities.add(new Entity(Body.VENUS, 3.50e4, 0, 0, 1.08e11));
        entities.add(new Entity(Body.EARTH, -2.98e4, 0, 0, -1.50e11));
        entities.add(new Entity(Body.MOON, -2.98e4, -1.02e3, 3.84e8, -1.50e11));
        entities.add(new Entity(Body.MARS, 0, 2.40e4, -2.28e11, 0));
        entities.add(
                new Entity(Body.PHOBOS, -2.14e3, 2.40e4, -2.28e11, -9.38e6));
        entities.add(
                new Entity(Body.DEIMOS, 1.35e3, 2.40e4, -2.28e11, 2.35e7));

        double timeAcceleration = 1.00e5;
        double overlayZoomFactor = 100;

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
