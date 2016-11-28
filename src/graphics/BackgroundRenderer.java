/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import eraser.Config;
import java.awt.Color;
import java.awt.Graphics2D;
import world.World;

/**
 *
 * @author dorian
 */
public class BackgroundRenderer {
    
    private final GameCanvas canvas;
    private final World world;
    
    public BackgroundRenderer(GameCanvas canvas, World world) {
        this.canvas = canvas;
        this.world = world;
    }
    
    public void renderBackgroundColor(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
    }
    
    public void renderWorldGrid(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < world.width + (world.height % Config.GRID_LENGTH == 0 ? 1 : 0); i += Config.GRID_LENGTH) {
            g.drawLine(i, 0, i, world.height);
        }
        for (int i = 0; i < world.height + (world.width % Config.GRID_LENGTH == 0 ? 1 : 0); i += Config.GRID_LENGTH) {
            g.drawLine(0, i, world.width, i);
        }
    }
}
