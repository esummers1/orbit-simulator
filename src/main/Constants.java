package main;

public abstract class Constants {
    
    /**
     * Simulation window will be a square of this many pixels on each side.
     */
    public static final int WINDOW_SIZE = 800;
    
    // Universal gravitational constant
    public static final double BIG_G = 6.674 * Math.pow(10, -11);
    
    /**
     * Spatial scale factor, i.e. number of metres per pixel.
     * TODO: Make this part of the simulation setup, to simulate bigger systems
     */
    public static final double SCALE_FACTOR = Math.pow(10, 9) / WINDOW_SIZE;
    
    /**
     * Temporal scale factor, i.e. number of real-world seconds per second in
     * the simulation. This is combined with the framerate to give the true
     * timestep.
     */
    public static final double TIME_FACTOR = 100000;
    
    /**
     * Frame rate in frames per second. Is combined with TIME_FACTOR to give the
     * simulated time step per frame.
     */
    public static final double FRAME_RATE = 240;
    
    /**
     * Time step, i.e. the number of seconds of simulated time that pass per
     * frame of the simulation.
     */
    public static final double TIME_STEP = TIME_FACTOR / FRAME_RATE;
    
}
