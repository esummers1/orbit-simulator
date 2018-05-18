package entities;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Camera;
import physics.Constants;
import physics.Position;

public class Entity {
    
    private String name;
    private double mass;
    private double radius;
    private double xVel;
    private double yVel;
    private Position position;
    
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
        this.setPosition(new Position(x, y));
        this.setColour(colour);
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

    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
    public void setPositionDirectly(double x, double y) {
        this.position.setX(x);
        this.position.setY(y);
    }
    
    public void draw(Graphics2D g) {
        
        /**
         * Calculate on-screen position of Entity for rendering. This takes into
         * account the current scaling as well as the offset calculated from the
         * position of the system's centre of mass at this moment.
         * TODO: clean up this garbage
         */
        double scaleFactor = Constants.SCALE_FACTOR;
        double windowSize = Constants.WINDOW_SIZE * scaleFactor;
        
        double baseX = position.getX() + radius;
        double baseY = position.getY() + radius;
        
        double adjustX = Camera.getCentreOfFrame().getX();
        double adjustY = Camera.getCentreOfFrame().getY();
        
        double unscaledX = baseX + (windowSize / 2 - adjustX);
        double unscaledY = baseY + (windowSize / 2 - adjustY);
        
        double scaledX = unscaledX / scaleFactor;
        double scaledY = unscaledY / scaleFactor;
        
        double scaledDiameter = 
                (radius * 2 * Constants.ENTITY_DISPLAY_SCALE_FACTOR) / 
                Constants.SCALE_FACTOR;
        
        g.setColor(colour);
        g.fillOval(
                (int) scaledX, 
                (int) scaledY,
                (int) scaledDiameter,
                (int) scaledDiameter);
    }
    
}
