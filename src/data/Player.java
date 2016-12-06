/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.awt.Point;

/**
 *
 * @author dorian
 */
public class Player {
    
    public double x, y;
    public int id, age, status, rank;
    public String name;
    
    public Player(int id, double x, double y, int age, int status) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.age = age;
        this.status = status;
        this.name = "";
    }
    
    public Player(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    
    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Player(int id, String name, int age, int rank) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.rank = rank;
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
