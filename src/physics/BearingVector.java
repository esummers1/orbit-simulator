package physics;

/**
 * Class representing a vector composed of a magnitude and a bearing.
 * 
 * @author Eddie Summers
 */
public class BearingVector {
    
    private double magnitude;
    private double bearing;
    
    public BearingVector(double magnitude, double bearing) {
        this.magnitude = magnitude;
        this.bearing = bearing;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }
    
}
