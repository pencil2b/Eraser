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
    
    public Point position;
    public int status;
    
    public Bullet(Point position, String name, int age, int status) {
        this.position = position;
        this.status = status;
    }
}
