/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import eraser.Config;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import world.World;

/**
 *
 * @author dorian
 */
public class BackgroundRenderer {
    
    public static void renderBackgroundColor(Graphics2D g, Dimension canvasSize) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, canvasSize.width, canvasSize.height);
        
    }
    
    public static void renderWorldGrid(Graphics2D g, Dimension worldSize) {
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < worldSize.width + (worldSize.height % Config.GRID_LENGTH == 0 ? 1 : 0); i += Config.GRID_LENGTH) {
            g.drawLine(i, 0, i, worldSize.height);
        }
        for (int i = 0; i < worldSize.height + (worldSize.width % Config.GRID_LENGTH == 0 ? 1 : 0); i += Config.GRID_LENGTH) {
            g.drawLine(0, i, worldSize.width, i);
        }
    }
}
