/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import java.awt.Point;

/**
 *
 * @author dorian
 */
public class Bullet {
    
    public double x, y;
    public int status;
    
    public Bullet(double x, double y, int status) {
        this.x = x;
        this.y = y;
        this.status = status;
    }
    
    public int x() {
        return (int) this.x;
    }
    
    public int y() {
        return (int) this.y;
    }
    
}
