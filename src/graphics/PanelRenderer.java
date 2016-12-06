/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import world.Player;

/**
 *
 * @author dorian
 */
public class PanelRenderer {
    
    public static void renderPanel(Graphics2D g, ArrayList<Player> nameList) {
        for(int i = 0; i < nameList.size(); i++) {
            g.setColor(Color.GRAY);
            g.drawString(String.format("%2d %s", i + 1, nameList.get(i).name), 10, i * 20 + 20);
        }
    }
}
