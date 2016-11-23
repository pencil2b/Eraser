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
public class Player {
    
    public double x, y;
    public int id, age, status;
    
    public Player(int id, double x, double y, int age, int status) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.age = age;
        this.status = status;
    }
    
    public int x() {
        return (int) this.x;
    }
    
    public int y() {
        return (int) this.y;
    }
    
    public Point position() {
        return new Point(x(), y());
    }
}
