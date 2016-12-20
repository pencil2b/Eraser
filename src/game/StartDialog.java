package game;

import javax.swing.JOptionPane;

/**
 * Login dialog
 *
 * @author dorian
 */
class StartDialog {

    public static String getInfo() {
        return JOptionPane.showInputDialog(Game.window, Config.START_MESSAGE, Config.START_TITLE, JOptionPane.PLAIN_MESSAGE);
    }
}
