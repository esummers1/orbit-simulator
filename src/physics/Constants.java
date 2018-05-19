package physics;

public abstract class Constants {
    
    // Simulation window will be a square of this many pixels on each side.
    public static final int WINDOW_SIZE = 950;
    
    // How many times larger than real size entities are displayed.
    public static final double ENTITY_DISPLAY_SCALE_FACTOR = 1;
    
    // Frame rate in frames per second
    public static final double FRAME_RATE = 300;

    // Universal gravitational constant
    public static final double BIG_G = 6.674 * Math.pow(10, -11);
    
}
