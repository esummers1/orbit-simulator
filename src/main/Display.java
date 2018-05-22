package main;

import javax.swing.JFrame;

/**
 * Class, responsible for creating the simulation window.
 * 
 * @author Eddie Summers
 */
public class Display {
    
    private JFrame frame;
    private MyPanel panel;
    private String names;
    
    // Window will be a square this many pixels per side.
    public static final int WINDOW_SIZE = 950;
    
    public Display(Simulation sim) {
        
        names = "";
        
        // Construct list of Entity names for title
        for (int i = 0; i < sim.getEntityNames().size(); i++) {
            names = names + sim.getEntityNames().get(i);
            
            if (i != sim.getEntityNames().size() - 1) {
                names = names + ", ";
            } 
        }
        
        panel = new MyPanel(WINDOW_SIZE, WINDOW_SIZE);
        frame = createFrame(panel);
        frame.setVisible(true);
    }
    
    public JFrame createFrame(MyPanel panel) {
        JFrame frame = new JFrame("Orbit Simulator | " + names);
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
