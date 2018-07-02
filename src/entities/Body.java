package entities;

import java.awt.Color;

/**
 * Class representing the unchanging attributes of a simulation entity.
 * 
 * @author Eddie Summers
 */
public class Body {
    
    // Real-universe bodies
    public static final Body EARTH = new Body(
            "Earth", 5.97 * Math.pow(10, 24), 6.37 * Math.pow(10, 6),
            new Color(4, 69, 160));
    public static final Body MARS = new Body(
            "Mars", 6.42 * Math.pow(10, 23), 3.39 * Math.pow(10, 6),
            new Color(119, 38, 8));
    public static final Body MOON = new Body(
            "Moon", 7.34 * Math.pow(10, 22), 1.74 * Math.pow(10, 6),
            new Color(112, 112, 112));
    public static final Body JUPITER = new Body(
            "Jupiter", 1.90 * Math.pow(10, 27), 7.15 * Math.pow(10, 7), 
            new Color(209, 166, 102));
    public static final Body VENUS = new Body(
            "Venus", 4.87 * Math.pow(10, 24), 6.05 * Math.pow(10, 6),
            new Color(170, 144, 90));
    public static final Body SUN = new Body(
            "Sun", 1.99 * Math.pow(10, 30), 6.96 * Math.pow(10, 8),
            new Color(255, 255, 255));
    
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
