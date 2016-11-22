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
    
    private Player player;
    private Graphics2D g;
    
    public PlayerRenderer(Player player) {
        this.player = player;
    }
    
    public void render(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.drawRect(
                player.position.x - player.age,
                player.position.y - player.age,
                player.age,
                player.age);
    }
}
