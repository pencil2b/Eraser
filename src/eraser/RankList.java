/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eraser;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import world.Player;

/**
 *
 * @author dorian
 */
public class RankList {

    public static void show(JFrame frame, ArrayList<Player> rankList) {
        String text = String.format("%7s\t%7s\t%7s\t%-36s\n", "Rank", "Age", "ID", "Name");
        for(int i = 0; i < rankList.size(); i++) {
            Player p = rankList.get(i);
            text += String.format("%7d\t%7d\t%7d\t%-36s\n", i, p.age, p.id, p.name);
        }
        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(frame, scrollPane, "Full Rank List", JOptionPane.PLAIN_MESSAGE);
    }
}
