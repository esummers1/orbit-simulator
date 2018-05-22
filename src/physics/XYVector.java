package physics;

/**
 * Class representing a vector composed of x- and y- components.
 * 
 * @author Eddie Summers
 */
public class XYVector {
    
    private double x;
    private double y;
    
    public XYVector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return x;
    }
    public void setX(double X) {
        this.x = X;
    }
    public double getY() {
        return y;
    }
    public void setY(double Y) {
        this.y = Y;
    }
    
}
