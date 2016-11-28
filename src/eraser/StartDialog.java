package eraser;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * A Simple Login Dialog
 *
 * @author Oliver Watkins (c)
 */
public class StartDialog {

    public static String getInfo(JFrame frame) {
        return JOptionPane.showInputDialog(frame, Config.START_MESSAGE, Config.START_TITLE, JOptionPane.PLAIN_MESSAGE);
    }
}
