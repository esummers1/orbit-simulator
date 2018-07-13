package entities;

import main.Camera;
import physics.Position;

/**
 * Class representing a single Entity shot, i.e. information about a single
 * instance of the user selecting a Body and dragging with the mouse to shoot
 * an Entity into the simulation.
 *
 * @author Eddie Summers
 */
public class EntityShot {

    private Body body;
    private Position start;
    private Position end;
    private Camera camera;
    private double duration;
    private double scaleFactor;

    public EntityShot(
            Body body,
            Position start,
            Position end,
            Camera camera,
            double duration,
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

    public Position getStart() {
        return start;
    }

    public void setStart(Position start) {
        this.start = start;
    }

    public Position getEnd() {
        return end;
    }

    public void setEnd(Position end) {
        this.end = end;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }
}
