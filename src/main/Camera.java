package main;

import physics.Position;

public abstract class Camera {
    
    private static double zoomOutFactor;
    private static Position centreOfFrame;
    
    public static double getZoomOutFactor() {
        return zoomOutFactor;
    }

    public static void setZoomOutFactor(double zoomOutFactor) {
        Camera.zoomOutFactor = zoomOutFactor;
    }

    public static Position getCentreOfFrame() {
        return centreOfFrame;
    }

    public static void setCentreOfFrame(Position centreOfFrame) {
        Camera.centreOfFrame = centreOfFrame;
    }
    
}
