package physics;

public abstract class Constants {
    
    // Simulation window will be a square of this many pixels on each side.
    public static final int WINDOW_SIZE = 900;
    
    // Universal gravitational constant
    public static final double BIG_G = 6.674 * Math.pow(10, -11);
    
    // How many times larger than real size entities are displayed.
    public static final double ENTITY_DISPLAY_SCALE_FACTOR = 3;
    
    // Spatial scale factor, i.e. number of metres per pixel.
    // TODO: Get rid of this and use camera logic instead
    public static final double SCALE_FACTOR = Math.pow(10, 9) / WINDOW_SIZE;
    
    // Frame rate in frames per second
    public static final double FRAME_RATE = 300;
    
}
