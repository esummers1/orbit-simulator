package entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the unchanging attributes of a simulation entity.
 * 
 * @author Eddie Summers
 */
public class Body {
    
    // Real-universe bodies
    public static final Body SUN = new Body(
            "Sun", 1.99e30, 6.96e8, new Color(255, 255, 255));

    public static final Body MERCURY = new Body(
            "Mercury", 3.30e23, 2.44e6, new Color(114, 104, 103));

    public static final Body VENUS = new Body(
            "Venus", 4.87e24, 6.05e6, new Color(170, 144, 90));

    public static final Body EARTH = new Body(
            "Earth", 5.97e24, 6.37e6, new Color(4, 69, 160));
    public static final Body MOON = new Body(
            "Moon", 7.34e22, 1.74e6, new Color(112, 112, 112));

    public static final Body MARS = new Body(
            "Mars", 6.42e23, 3.39e6, new Color(119, 38, 8));
    public static final Body PHOBOS = new Body(
            "Phobos", 1.066e16, 1.13e4, new Color(114, 104, 103));
    public static final Body DEIMOS = new Body(
            "Deimos", 1.48e15, 6.0e3, new Color(163, 144, 128));

    public static final Body JUPITER = new Body(
            "Jupiter", 1.90e27, 7.15e7, new Color(209, 166, 102));
    public static final Body IO = new Body(
            "Io", 8.93e22, 1.82e6, new Color(150, 134, 45));
    public static final Body EUROPA = new Body(
            "Europa", 4.80e22, 1.56e6, new Color(163, 127, 73));
    public static final Body GANYMEDE = new Body(
            "Ganymede", 1.48e23, 2.63e6, new Color(112, 89, 74));
    public static final Body CALLISTO = new Body(
            "Callisto", 1.08e23, 2.41e6, new Color(145, 130, 120));

    public static final Body SATURN = new Body(
            "Saturn", 5.68e26, 5.82e7, new Color(186, 134, 61));
    public static final Body RHEA = new Body(
            "Rhea", 2.31e21, 7.63e5, new Color(183, 183, 183));
    public static final Body TITAN = new Body(
            "Titan", 1.34e23, 2.58e6, new Color(234, 185, 37));
    public static final Body IAPETUS = new Body(
            "Iapetus", 1.81e21, 7.34e5, new Color(117, 95, 69));

    public static final Body URANUS = new Body(
            "Uranus", 8.68e25, 2.54e7, new Color(156, 215, 216));
    public static final Body TITANIA = new Body(
            "Titania", 3.53e21, 7.88e5, new Color(201, 189, 165));
    public static final Body OBERON = new Body(
            "Oberon", 3.01e21, 7.61e5, new Color(165, 137, 112));

    public static final Body NEPTUNE = new Body(
            "Neptune", 1.02e26, 2.46e7, new Color(53, 99, 224));
    public static final Body TRITON = new Body(
            "Triton", 2.14e22, 1.35e6, new Color(183, 180, 168));

    public static final Body PLUTO = new Body(
            "Pluto", 1.30e22, 1.19e6, new Color(198, 140, 81));
    public static final Body CHARON = new Body(
            "Charon", 1.59e21, 6.06e5, new Color(214, 182, 149));

    public static final Body VY_CANIS_MAJORIS = new Body(
            "VY Canis Majoris", 3.6e30, 9.88e11, new Color(255, 67, 0));

    private String name;
    private double mass;
    private double radius;
    private Color colour;
    
    public Body(String name, double mass, double radius, Color colour) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.colour = colour;
    }

    /**
     * Return a list of the default Body constants.
     * @return List<Body>
     */
    public static List<Body> getDefaultBodies() {

        List<Body> defaultBodies = new ArrayList<>();
        defaultBodies.add(SUN);
        defaultBodies.add(MERCURY);
        defaultBodies.add(VENUS);
        defaultBodies.add(EARTH);
        defaultBodies.add(MOON);
        defaultBodies.add(MARS);
        defaultBodies.add(PHOBOS);
        defaultBodies.add(DEIMOS);
        defaultBodies.add(JUPITER);
        defaultBodies.add(IO);
        defaultBodies.add(EUROPA);
        defaultBodies.add(GANYMEDE);
        defaultBodies.add(CALLISTO);
        defaultBodies.add(SATURN);
        defaultBodies.add(RHEA);
        defaultBodies.add(TITAN);
        defaultBodies.add(IAPETUS);
        defaultBodies.add(URANUS);
        defaultBodies.add(TITANIA);
        defaultBodies.add(OBERON);
        defaultBodies.add(NEPTUNE);
        defaultBodies.add(TRITON);
        defaultBodies.add(PLUTO);
        defaultBodies.add(CHARON);
        defaultBodies.add(VY_CANIS_MAJORIS);

        return defaultBodies;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getMass() {
        return mass;
    }
    public void setMass(double mass) {
        this.mass = mass;
    }
    public double getRadius() {
        return radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }
    public Color getColour() {
        return colour;
    }
    public void setColour(Color colour) {
        this.colour = colour;
    }
    
}
