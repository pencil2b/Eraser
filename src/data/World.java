/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import game.Config;
import java.util.ArrayList;

/**
 *
 * @author dorian
 */
public class World {
    
    public static void setSize(int w, int h) {
        getInstance().width = w;
        getInstance().height = h;
    }
    
    public static Player findPlayer(int id) {
        return getInstance().findPlayerById(id);
    }
    
    public static void update(ArrayList newPlayers, ArrayList newBullets) {
        getInstance().updateObjects(newPlayers, newBullets);
    }
    
    public static int getWidth() {
        return world.width;
    }
    
    public static int getHeight() {
        return world.height;
    }
    
    public static ArrayList<Player> getPlayers() {
        return getInstance().players;
    }
    
    public static ArrayList<Bullet> getBullets() {
        return getInstance().bullets;
    }
    
    static World world;
    
    static World getInstance() {
        if(world == null) {
            world = new World();
        }
        return world;
    }
    
    ArrayList<Player> players;
    ArrayList<Bullet> bullets;
    
    int width, height;
    
    World() {
        width = Config.DEFAULT_WORLD_WIDTH;
        height = Config.DEFAULT_WORLD_HEIGHT;
        players = new ArrayList<>();
        bullets = new ArrayList<>();
    }
    
    void updateObjects(ArrayList newPlayers, ArrayList newBullets) {
        players = newPlayers;
        bullets = newBullets;
    } 
    
    Player findPlayerById(int id) {
        for(Player player : players) {
            if(player.id == id) {
                return player;
            }
        }
        return null;
    }
}
