/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import world.Player;

/**
 *
 * @author dorian
 */
public class PlayerRenderer {
    
    private final Player player;
    private final int R = 10;

    public PlayerRenderer(Player player) {
        this.player = player;
    }
    
    public void render(Graphics2D g) {
        int r = R + player.age;
        g.setColor(Color.WHITE);
        g.drawOval(player.x() - r, player.y() - r, r + r, r + r);
        g.setColor(Color.red);
        g.drawString(player.name, player.x(), player.y());
    }
}
