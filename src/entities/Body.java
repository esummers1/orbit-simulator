package entities;

import java.awt.Color;

public class Body {
    
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
