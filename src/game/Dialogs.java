/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import data.Player;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author dorian
 */
public class Dialogs {
    
    public static String showStart() {
        return JOptionPane.showInputDialog(Game.window, Config.START_MESSAGE, Config.START_TITLE, JOptionPane.PLAIN_MESSAGE);
    }
    
    public static int showGameOver(JFrame frame, Player player) {
        String message = "[ Result ]\n";
        message += "Name: " + player.name + "\n";
        message += "Age: " + player.age + "\n";
        message += "Rank: " + player.rank + "\n";

        Object[] options = {"Exit", "Full Rank", "Restart"};

        int code = JOptionPane.showOptionDialog(frame, message,
                Config.END_TITLE, JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

        return code;
    }
    
    public static void showRankList(JFrame frame, ArrayList<Player> rankList) {
        String text = String.format("\n%9s\t%7s\t%7s\t%-36s\n\n", "Place", "Age", "ID", "Name");

        if (rankList != null) {
            for (int i = 0; i < rankList.size(); i++) {
                Player p = rankList.get(i);
                text += String.format("%9d\t%7d\t%7d\t%-36s\n\n", i + 1, p.age, p.id, p.name);
            }
        } else {
            text += "Nothing in list";
        }

        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(frame, scrollPane, "Full Rank List", JOptionPane.PLAIN_MESSAGE);
    }
}
