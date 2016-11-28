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
        int r = R + 3 * player.age / 10;
        switch(player.status) {
            case 0:
                g.setColor(Color.DARK_GRAY);
                break;
            case 1:
                g.setColor(Color.WHITE);
                break;
            case 2:
                g.setColor(Color.CYAN);
                break;
            default:
                g.setColor(Color.WHITE);
        }
        g.drawOval(player.x() - r, player.y() - r, r + r, r + r);
        g.setColor(Color.RED);
        g.drawString(String.format("id=%d name=%s age=%d status=%d", player.id, player.name, player.age, player.status), player.x() - r, player.y() - r - 4);
    }
}
