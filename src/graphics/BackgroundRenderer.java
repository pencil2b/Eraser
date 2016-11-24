/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import world.World;

/**
 *
 * @author dorian
 */
public class BackgroundRenderer {
    
    private final GameCanvas canvas;
    private final World world;
    private final int LENGTH = 100;
    
    public BackgroundRenderer(GameCanvas canvas, World world) {
        this.canvas = canvas;
        this.world = world;
    }
    
    public void renderBackgroundColor(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
//        g.setColor(Color.WHITE);
//        int r1 = 3, r2 = 100;
//        g.drawOval(center.x - r1, center.y - r1, r1 + r1, r1 + r1);
//        g.drawOval(center.x - r2, center.y - r2, r2 + r2, r2 + r2);
    }
    
    public void renderWorldGrid(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < world.width + (world.height % LENGTH == 0 ? 1 : 0); i += LENGTH) {
            g.drawLine(i, 0, i, world.height);
        }
        for (int i = 0; i < world.height + (world.width % LENGTH == 0 ? 1 : 0); i += LENGTH) {
            g.drawLine(0, i, world.width, i);
        }
    }
    
}
