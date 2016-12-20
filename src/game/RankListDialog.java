package game;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import data.Player;

/**
 * Rank list dialog
 *
 * @author dorian
 */
class RankListDialog {

    public static void show(JFrame frame, ArrayList<Player> rankList) {
        String text = String.format("\n%7s\t%7s\t%7s\t%-36s\n\n", "Rank", "Age", "ID", "Name");

        if (rankList != null) {
            for (int i = 0; i < rankList.size(); i++) {
                Player p = rankList.get(i);
                text += String.format("%7d\t%7d\t%7d\t%-36s\n\n", i + 1, p.age, p.id, p.name);
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
