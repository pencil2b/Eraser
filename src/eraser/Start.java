package eraser;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * A Simple Login Dialog
 *
 * @author Oliver Watkins (c)
 */
public class Start {

    public static String getInfo(JFrame frame) {
        return JOptionPane.showInputDialog(frame, "To connect to the server,\nplease input: \"name@ip:port\"", "Welcome", JOptionPane.PLAIN_MESSAGE);
    }
}
