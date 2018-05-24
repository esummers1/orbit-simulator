package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import entities.Entity;

/**
 * Class responsible for rendering the simulation.
 * 
 * @author Eddie Summers
 */
public class MyPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;

    private List<Entity> entities;

    public MyPanel(int width, int height, List<Entity> entities) {
        setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.entities = entities;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        for (Entity entity : entities) {
            entity.draw(g2d);
        }
    }
    
}