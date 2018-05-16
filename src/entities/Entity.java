package entities;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Constants;

public class Entity {
    
    private String name;
    private double mass;
    private double radius;
    private double xVel;
    private double yVel;
    private double x;
    private double y;
    
    private Color colour;
    
    public Entity(
            String name,
            double mass, 
            double radius,
            double xVel, 
            double yVel, 
            double x,
            double y,
            Color colour) {
        this.setName(name);
        this.setMass(mass);
        this.setRadius(radius);
        this.setXVel(xVel);
        this.setYVel(yVel);
        this.setX(x);
        this.setY(y);
        this.setColour(colour);
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

    public double getXVel() {
        return xVel;
    }

    public void setXVel(double xVel) {
        this.xVel = xVel;
    }

    public double getYVel() {
        return yVel;
    }

    public void setYVel(double yVel) {
        this.yVel = yVel;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }
    
    public void draw(Graphics2D g) {
        double scaledX = this.x / Constants.SCALE_FACTOR;
        double scaledY = this.y / Constants.SCALE_FACTOR;
        
        // Originally this.radius * 2 for realism, obviously
        // TODO: export this to Constants, i.e. entity display scale
        double scaledDiameter = (this.radius * 6) / Constants.SCALE_FACTOR;
        
        g.setColor(colour);
        g.fillOval(
                (int) scaledX, 
                (int) scaledY,
                (int) scaledDiameter,
                (int) scaledDiameter);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
