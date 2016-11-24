/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Graphics2D;
import java.util.ArrayList;
import world.Player;

/**
 *
 * @author dorian
 */
public class PanelRenderer {
    
    ArrayList<Player> nameList;
    
    public PanelRenderer(ArrayList<Player> nameList) {
        this.nameList = nameList;
    }
    
    public void render(Graphics2D g) {
        for(int i = 0; i < nameList.size(); i++) {
            g.drawString(String.format("%2d %s", i + 1, nameList.get(i).name), 10, i * 20 + 20);
        }
    }
}
