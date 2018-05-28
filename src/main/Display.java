package main;

import java.util.List;

import javax.swing.JFrame;

import entities.Entity;

/**
 * Class, responsible for creating the simulation window.
 * 
 * @author Eddie Summers
 */
public class Display {
    
    private JFrame frame;
    private MyPanel panel;
    
    // Window will be a square this many pixels per side.
    public static final int WINDOW_SIZE = 950;
    
    public Display(Simulation sim) {
        panel = new MyPanel(WINDOW_SIZE, WINDOW_SIZE, sim.getEntities());
        frame = createFrame(panel, createTitle(sim.getEntityNames()));
        frame.setVisible(true);
        frame.addKeyListener(sim);
    }
    
    /**
     * Creates the title string using a list of entity names.
     * @param names
     * @return String
     */
    public static String createTitle(List<String> names) {
        
        String title = "Orbit Simulator | ";
        
        for (int i = 0; i < names.size(); i++) {
            title = title + names.get(i);
            
            if (i != names.size() - 1) {
                title = title + ", ";
            } 
        }
        
        /*
         * Highlight the Entity which is the current focus of the simulation,
         * if one is.
         */
        Entity currentFocus = Simulation.getCurrentFocus();
        
        if (currentFocus != null) {
            title += " (watching " + currentFocus.getBody().getName() + ")";
        }
        
        return title;
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
