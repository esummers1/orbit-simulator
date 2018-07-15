package main;

import java.util.ArrayList;
import java.util.List;

import entities.Body;
import entities.Entity;
import physics.Physics;

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
        allScenarios.add(createEmptyPlanetaryScaleScenario());
        allScenarios.add(createEmptyStellarScaleScenario());
        allScenarios.add(createEarthMoonScenario());
        allScenarios.add(createInnerPlanetsScenario());
        allScenarios.add(createJupiterAndMoonsScenario());
        allScenarios.add(createEarthMarsVenusCollisionScenario());

        return new ScenarioRepository(allScenarios);
    }

    /**
     * Create a fictional Scenario representing Earth, Mars and Venus orbiting
     * in close proximity and coalescing.
     * @return Scenario
     */
    private static Scenario createEarthMarsVenusCollisionScenario() {

        List<Entity> entities = new ArrayList<>();
        entities.add(new Entity(Body.EARTH, 0, 0, 0, 0));
        entities.add(new Entity(
                Body.VENUS, -1.00e3, 0, 0, 1.26e8));
        entities.add(new Entity(
                Body.MARS, 0, -1.00e3, 1.26e8, 0));

        return new Scenario(
                "Collision of Earth, Mars and Venus",
                entities,
                1.6e4,
                3,
                Physics.calculateAppropriateScaleFactor(entities));
    }

    /**
     * Create a Scenario representing the Earth-Moon system.
     * @return Scenario
     */
    private static Scenario createEarthMoonScenario() {
        
        List<Entity> entities = new ArrayList<>();
        entities.add(new Entity(Body.EARTH, 0, 0, 0, 0));
        entities.add(new Entity(
                Body.MOON, 1.02e3, 0, 0, 4.00e8));

        return new Scenario(
                "Earth and Moon",
                entities,
                6e4,
                3,
                Physics.calculateAppropriateScaleFactor(entities));
    }

    /**
     * Create an empty Scenario scaled appropriately for instantiating planets.
     * @return Scenario
     */
    private static Scenario createEmptyPlanetaryScaleScenario() {

        List<Entity> mockEntities = new ArrayList<>();
        mockEntities.add(new Entity(Body.EARTH, 0, 0, 0, 0));

        return new Scenario(
                "Empty Simulation - Planetary Scale",
                new ArrayList<>(),
                2e4,
                3,
                Physics.calculateAppropriateScaleFactor(mockEntities));
    }

    /**
     * Create an empty Scenario scaled appropriately for instantiating stars.
     * @return Scenario
     */
    private static Scenario createEmptyStellarScaleScenario() {

        List<Entity> mockEntities = new ArrayList<>();
        mockEntities.add(new Entity(Body.SUN, 0, 0, 0, 0));

        return new Scenario(
                "Empty Simulation - Stellar Scale",
                new ArrayList<>(),
                6e6,
                100,
                Physics.calculateAppropriateScaleFactor(mockEntities));
    }

    /**
     * Create a Scenario representing the Sun, the inner planets and their moons
     * (orbital phase angles not correct).
     * @return Scenario
     */
    private static Scenario createInnerPlanetsScenario() {

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

        return new Scenario(
                "Inner planets and moons",
                entities,
                1e5,
                100,
                Physics.calculateAppropriateScaleFactor(entities));
    }

    /**
     * Create a Scenario representing Jupiter and its inner four (Galilean)
     * moons.
     * @return Scenario
     */
    private static Scenario createJupiterAndMoonsScenario() {

        List<Entity> entities = new ArrayList<>();
        entities.add(new Entity(Body.JUPITER, 0, 0, 0, 0));
        entities.add(new Entity(Body.IO, 1.73e4, 0, 0, 4.22e8));
        entities.add(new Entity(Body.EUROPA, 1.37e4, 0, 0, 6.71e8));
        entities.add(new Entity(Body.GANYMEDE, 1.09e4, 0, 0, 1.07e9));
        entities.add(new Entity(Body.CALLISTO, 8.20e3, 0, 0, 1.88e9));

        return new Scenario(
                "Jupiter and its inner moons",
                entities,
                2e4,
                8,
                Physics.calculateAppropriateScaleFactor(entities));
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }
    
}
