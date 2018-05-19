package entities;

import java.awt.Graphics2D;

import main.Camera;
import main.Simulation;
import physics.Constants;
import physics.Position;

public class Entity {
    
    private Body body;
    private double xVel;
    private double yVel;
    private Position position;
    
    public Entity(
            Body body,
            double xVel, 
            double yVel, 
            double x,
            double y) {
        
        this.setBody(body);
        this.setXVel(xVel);
        this.setYVel(yVel);
        this.setPosition(new Position(x, y));
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
        double scaleFactor = Simulation.getSizedScaleFactor();
        double windowSize = Constants.WINDOW_SIZE * scaleFactor;
        
        double baseX = position.getX() + body.getRadius();
        double baseY = position.getY() + body.getRadius();
        
        double adjustX = Camera.getCentreOfFrame().getX();
        double adjustY = Camera.getCentreOfFrame().getY();
        
        double unscaledX = baseX + (windowSize / 2 - adjustX);
        double unscaledY = baseY + (windowSize / 2 - adjustY);
        
        double scaledX = unscaledX / scaleFactor;
        double scaledY = unscaledY / scaleFactor;
        
        double scaledDiameter = 
                (body.getRadius() * 2 * Constants.ENTITY_DISPLAY_SCALE_FACTOR) / 
                Simulation.getSizedScaleFactor();
        
        g.setColor(body.getColour());
        g.fillOval(
                (int) scaledX, 
                (int) scaledY,
                (int) scaledDiameter,
                (int) scaledDiameter);
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
    
}
