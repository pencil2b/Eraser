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
    public Point position;
    public int id, age, status;
    
    public Player(Point position, int id, int age, int status) {
        this.position = position;
        this.id = id;
        this.age = age;
        this.status = status;
    }

}
