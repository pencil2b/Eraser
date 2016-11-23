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
    
    public Player findPlayer(int id) {
        for(Player player : players) {
            if(player.id == id) {
                return player;
            }
        }
        return null;
    }
    
    public void transformFor(int id, Point center) {
        Point p = getPointById(id);
        int dx = center.x - p.x;
        int dy = center.y - p.y;
        players.forEach((player) -> {
            player.x += dx;
            player.y += dy;
        });
        bullets.forEach((bullet) -> {
            bullet.x += dx;
            bullet.y += dy;
        });
    }
    
    private Point getPointById(int id) {
        for(Player player: players) {
            if(player.id == id) {
                return new Point(player.x(), player.y());
            }
        }
        return new Point(0, 0);
    }
}
