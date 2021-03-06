package entities;

import main.Camera;
import physics.Position;

import java.awt.*;

/**
 * Class representing a single Entity shot, i.e. information about a single
 * instance of the user selecting a Body and dragging with the mouse to shoot
 * an Entity into the simulation.
 *
 * @author Eddie Summers
 */
public class EntityShot {

    private Body body;
    private Point start;
    private Point end;
    private Camera camera;
    private long duration;
    private double scaleFactor;

    public EntityShot(
            Body body,
            Point start,
            Point end,
            Camera camera,
            long duration,
            double scaleFactor) {

        this.body = body;
        this.start = start;
        this.end = end;
        this.camera = camera;
        this.duration = duration;
        this.scaleFactor = scaleFactor;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public double getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }
}
