package physics;

import java.util.List;

/**
 * Class containing geometric utility methods (those involving angles and
 * co-ordinates, rather than forces and accelerations).
 * 
 * @author Eddie Summers
 */
public abstract class Geometry {

    /**
     * Given a pair of Positions, calculate the absolute distance between them.
     * @param here
     * @param there
     * @return double
     */
    public static double getDistance(Position here, Position there) {
        
        return Math.sqrt(
                Math.pow(there.getX() - here.getX(), 2) +
                Math.pow(there.getY() - here.getY(), 2));
    }

    /**
     * Given a pair of Positions, calculate the bearing (from negative-y, i.e.
     * upwards on-screen) of 'there' as observed from 'here', in radians.
     * @param here
     * @param there
     * @return double
     */
    public static double calculateBearing(Position here, Position there) {
        
        double deltaX = there.getX() - here.getX();
        double deltaY = there.getY() - here.getY();
        
        double theta = Math.PI / 2 + Math.atan2(deltaY, deltaX);
        
        // Not strictly necessary - makes testing clearer
        if (theta < 0) {
            theta += Math.PI * 2;
        }
        
        return theta;
    }

    /**
     * Given a list of XYVectors, resolve them into a single resultant XYVector.
     * @param vectors
     * @return XYVector
     */
    public static XYVector resolveVectors(List<XYVector> vectors) {
        
        XYVector resultantVector = new XYVector(0, 0);
        
        for (XYVector vector : vectors) {
            resultantVector.setX(resultantVector.getX() + vector.getX());
            resultantVector.setY(resultantVector.getY() + vector.getY());
        }
        
        return resultantVector;
    }

    /**
     * Convert a BearingVector to an XYVector.
     * @param vector
     * @return XYVector
     */
    public static XYVector convertToXYVector(BearingVector vector) {
        
        return new XYVector(
                vector.getMagnitude() * Math.sin(vector.getBearing()), 
                vector.getMagnitude() * -1 * Math.cos(vector.getBearing()));
    }
    
}
