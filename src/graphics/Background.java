/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author dorian
 */
public class Background {
    
    private Point center;
    private int width, height;
    
    public Background(int width, int height, Point center) {
        this.center = center;
        this.width = width;
        this.height = height;
    }
    
    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.WHITE);
        g.fillRect(center.x - 1, center.y - 1, 2, 2);
    }
    
}
