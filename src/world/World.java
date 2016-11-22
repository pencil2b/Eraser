/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import java.util.ArrayList;

/**
 *
 * @author dorian
 */
public class World {
    
    public ArrayList<Player> players;
    public ArrayList<Bullet> bullets;
    
    public World() {
        players = new ArrayList<>();
        bullets = new ArrayList<>();
    }
    
    public void loadData(World w) {
        this.players = w.players;
        this.bullets = w.bullets;
    }
    
}
