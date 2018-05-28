package entities;

import java.awt.Graphics2D;

import main.Camera;
import main.Display;
import main.Simulation;
import physics.Position;
import physics.XYVector;

/**
 * Class representing physical objects in the simulation (e.g. planets).
 * 
 * @author Eddie Summers
 */
public class Entity {
    
    /**
     * Inner class representing the properties needed for the panel component
     * to render the outer Entity.
     * 
     * @author Eddie Summers
     */
    public static class EntityForRendering {
        
        private int x;
        private int y;
        private int diameter;
        
        public EntityForRendering(int x, int y, int diameter) {
            this.x = x;
            this.y = y;
            this.diameter = diameter;
        }
        
        public int getX() {
            return x;
        }
        
        public int getY() {
            return y;
        }
        
        public int getDiameter() {
            return diameter;
        }
        
    }
    
    private Body body;
    private XYVector velocity;
    private Position position;
    
    public Entity(
            Body body,
            double xVel, 
            double yVel, 
            double x,
            double y) {
        
        this.setBody(body);
        this.setVelocity(new XYVector(xVel, yVel));
        this.setPosition(new Position(x, y));
    }
    
    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
    
    public XYVector getVelocity() {
        return velocity;
    }
    
    public void setVelocity(XYVector velocity) {
        this.velocity = velocity;
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
    
    /**
     * Draw method for the panel component to use when rendering simulation
     * objects.
     * @param g
     */
    public void draw(Graphics2D g) {
        
        EntityForRendering entityForRendering = 
                EntityRenderer.constructEntityForRendering(this);
        
        g.setColor(body.getColour());
        g.fillOval(
                (int) entityForRendering.getX(), 
                (int) entityForRendering.getY(),
                (int) entityForRendering.getDiameter(),
                (int) entityForRendering.getDiameter());
    }

}
