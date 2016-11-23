/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import java.awt.Point;
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
    } 
    
    public void transformFor(int id) {
        for(Player player : players) {
            
        }
        for(Bullet bullet : bullets) {
            
        }
    }
    
    private Point getCenterById(int id) {
        for(Player player: players) {
            if(player.id == id) {
                return new Point(player.x(), player.y());
            }
        }
        return new Point(players.get(0).x(), players.get(0).y());
    }
}
