package main;

import javax.swing.JFrame;

import physics.Constants;

public class Display {
    
    private JFrame frame;
    private MyPanel panel;
    private String names;
    
    public Display(Simulation sim) {
        
        names = "";
        
        // Construct list of Entity names for title
        for (int i = 0; i < sim.getEntityNames().size(); i++) {
            names = names + sim.getEntityNames().get(i);
            
            if (i != sim.getEntityNames().size() - 1) {
                names = names + ", ";
            } 
        }
        
        panel = new MyPanel(Constants.WINDOW_SIZE, Constants.WINDOW_SIZE);
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
