package entities;

import java.awt.Color;

/**
 * Class representing the unchanging attributes of a simulation entity.
 * 
 * @author Eddie Summers
 */
public class Body {
    
    // Real-universe bodies
    public static final Body MERCURY = new Body(
            "Mercury", 3.30e23, 2.44e6, new Color(114, 104, 103));

    public static final Body EARTH = new Body(
            "Earth", 5.97e24, 6.37e6, new Color(4, 69, 160));

    public static final Body MARS = new Body(
            "Mars", 6.42e23, 3.39e6, new Color(119, 38, 8));

    public static final Body PHOBOS = new Body(
            "Phobos", 1.066e16, 1.13e4, new Color(114, 104, 103));

    public static final Body DEIMOS = new Body(
            "Deimos", 1.48e15, 6.0e3, new Color(163, 144, 128));

    public static final Body MOON = new Body(
            "Moon", 7.34e22, 1.74e6, new Color(112, 112, 112));

    public static final Body JUPITER = new Body(
            "Jupiter", 1.90e27, 7.15e7, new Color(209, 166, 102));

    public static final Body VENUS = new Body(
            "Venus", 4.87e24, 6.05e6, new Color(170, 144, 90));

    public static final Body SUN = new Body(
            "Sun", 1.99e30, 6.96e8, new Color(255, 255, 255));
    
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
