package main;

import physics.Position;

public abstract class Camera {
    
    private static Position centreOfFrame;

    public static Position getCentreOfFrame() {
        return centreOfFrame;
    }

    public static void setCentreOfFrame(Position centreOfFrame) {
        Camera.centreOfFrame = centreOfFrame;
    }
    
}
