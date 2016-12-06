/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import game.Config;
import game.Debug;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import data.Bullet;
import data.Player;

/**
 *
 * @author dorian
 */
class Renderer {
    
    public static void renderPlayer(Graphics2D g, Player player) {
        
        int r = Config.PLAYER_DEFAULT_RADIUS + 3 * player.age / 10;
        
        switch(player.status) {
            case 0:
                g.setColor(Config.PLAYER_INIT_COLOR);
                break;
            case 1:
                g.setColor(Config.PLAYER_NORMAL_COLOR);
                break;
            case 2:
                g.setColor(Config.PLAYER_DEAD_COLOR);
                break;
            default:
                g.setColor(Color.BLACK);
        }
        
        g.drawOval(player.x() - r, player.y() - r, r + r, r + r);
        
        g.setColor(Config.PLAYER_NAME_COLOR);
        g.drawString(String.format("id=%d name=%s age=%d status=%d", player.id, player.name, player.age, player.status), player.x() - r, player.y() - r - 4);
        
    }
    
    public static void renderPanel(Graphics2D g, ArrayList<Player> nameList) {
        for(int i = 0; i < nameList.size(); i++) {
            g.setColor(Config.RANK_COLOR);
            g.drawString(String.format("%2d %s", i + 1, nameList.get(i).name), 10, i * 20 + 20);
        }
    }
    
    public static void renderBullet(Graphics2D g, Bullet bullet) {
        int r = Config.BULLET_RADUIS;
        g.setColor(Color.GREEN);
        g.drawOval(bullet.x() - r, bullet.y() - r, r + r, r + r);
    }
    
    public static void renderBackground(Graphics2D g, Dimension canvasSize) {
        g.setColor(Config.BACKBROUND_COLOR);
        g.fillRect(0, 0, canvasSize.width, canvasSize.height);
        
    }
    
    public static void renderGrid(Graphics2D g, Dimension worldSize) {
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < worldSize.width + (worldSize.height % Config.GRID_LENGTH == 0 ? 1 : 0); i += Config.GRID_LENGTH) {
            g.drawLine(i, 0, i, worldSize.height);
        }
        for (int i = 0; i < worldSize.height + (worldSize.width % Config.GRID_LENGTH == 0 ? 1 : 0); i += Config.GRID_LENGTH) {
            g.drawLine(0, i, worldSize.width, i);
        }
    }
}
