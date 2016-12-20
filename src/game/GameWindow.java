package game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * Main game window
 *
 * @author dorian
 */
class GameWindow extends JFrame {

    public GameWindow() {
        super(Config.TITLE);
        setup();
    }

    private void setup() {

        // Setup window location
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowLocationX = (int) ((screenSize.getWidth() - Config.DEFAULT_CANVAS_WIDTH) / 2);
        int windowLocationY = (int) ((screenSize.getHeight() - Config.DEFAULT_CANVAS_HEIGHT) / 2);
        setLocation(windowLocationX, windowLocationY);

        // Setup window size
        setMinimumSize(new Dimension(Config.DEFAULT_CANVAS_WIDTH, Config.DEFAULT_CANVAS_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Game.stop();
                super.windowClosing(e);
            }
        });

        pack();
    }

}
