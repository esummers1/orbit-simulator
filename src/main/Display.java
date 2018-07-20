package main;

import java.util.List;

import javax.swing.JFrame;

import entities.Body;
import entities.Entity;

/**
 * Class responsible for creating the simulation window.
 * 
 * @author Eddie Summers
 */
public class Display {
    
    private JFrame frame;
    private MyPanel panel;
    
    // Window will be a square this many pixels per side.
    public static final int WINDOW_SIZE = 950;
    
    public Display(Simulation sim) {
        panel = new MyPanel(
                WINDOW_SIZE, 
                WINDOW_SIZE, 
                sim.getEntities(), 
                sim.getCamera(),
                sim.getOverlayZoomFactor());
        
        frame = createFrame(panel, createTitle(sim));
        frame.setVisible(true);
        frame.addKeyListener(sim);
        frame.addMouseListener(sim);
        frame.addMouseMotionListener(sim);
        frame.addMouseWheelListener(sim);
    }
    
    /**
     * Creates the title string using a list of Entity names.
     * @param sim
     * @return String
     */
    public static String createTitle(Simulation sim) {
        
        String title = "Orbit Simulator";

        // Display time acceleration factor.
        title += " | Time Acceleration: " + displayDoubleAsScientific(
                Simulation.getTimeStep() * Simulation.FRAME_RATE, 2);

        /*
         * Highlight the Entity which is the current focus of the simulation,
         * if one is.
         */
        Entity currentFocus = sim.getCurrentFocus();

        if (currentFocus != null) {
            title += " | Watching " +
                    trimStringToLength(currentFocus.getBody().getName(), 40);
        }

        // Include the currently selected Body for shooting.
        Body currentBody = sim.getCurrentBodyForShooting();

        if (currentBody != null) {
            title += " | Shooting " +
                    trimStringToLength(currentBody.getName(), 40);
        }

        // Give number of Entities currently in the Simulation.
        title += " | Entities: " +  sim.getEntities().size();
        
        return title;
    }

    /**
     * Given some String, either return it as-is or truncate it at <length - 3>
     * characters and add three full stops.
     * @param string
     * @param length
     * @return String
     */
    public static String trimStringToLength(String string, int length) {

        if (string.length() >= (length - 3)) {
            string = string.substring(0, (length - 3)) + "...";
        }

        return string;
    }

    /**
     * Render a positive Double in scientific notation (e.g. 4.0 x 10^10), to a
     * given number of decimal places.
     * @param number
     * @param decimalPlaces
     * @return String
     */
    public static String displayDoubleAsScientific(
            double number, int decimalPlaces) {

        double prefix = number;
        int exponent = 0;

        if (number >= 1) {

            while (Math.abs(prefix) >= 10) {
                prefix /= 10;
                exponent++;
            }

        } else if (number > 0) {

            while (Math.abs(prefix) < 1) {
                prefix *= 10;
                exponent--;
            }

        } else {

            return "";
        }

        // Trim digits to given number of decimal places (N.b. not rounding)
        String truncatedPrefix = String.valueOf(prefix);
        truncatedPrefix = truncatedPrefix
                .substring(
                        0,
                        Math.min(truncatedPrefix.length(), decimalPlaces + 2));

        return truncatedPrefix + " x 10^" + exponent;
    }
    
    public JFrame createFrame(MyPanel panel, String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        return frame;
    }
    
    public JFrame getFrame() {
        return frame;
    }
    
    public MyPanel getPanel() {
        return panel;
    }
    
}
