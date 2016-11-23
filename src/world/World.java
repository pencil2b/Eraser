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
    
    public void loadLists(ArrayList newPlayers, ArrayList newBullets) {
        players = newPlayers;
        bullets = newBullets;
        
        // debug
        players.stream().map((player) -> {
            String s = "#";
            s += player.id + " (";
            s += player.x + ", ";
            s += player.y + ") ";
            s += player.age + " ";
            s += player.status;
            return s;
        }).forEachOrdered((s) -> {
            System.out.println(s);
        });
        bullets.stream().map((bullet) -> {
            String s = "# (";
            s += bullet.x + ", ";
            s += bullet.y + ") ";
            s += bullet.status;
            return s;
        }).forEachOrdered((s) -> {
            System.out.println(s);
        });
    } 
    
}
