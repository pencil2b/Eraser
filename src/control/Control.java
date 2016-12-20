package control;

import game.Config;
import graphics.Graphics;
import java.awt.Point;

/**
 * Control module
 *
 * @author dorian
 */
public class Control {

    static MouseControl mouse;

    public static MouseControl getInstance() {
        if (mouse == null) {
            Point center = new Point(Config.DEFAULT_CANVAS_WIDTH / 2, Config.DEFAULT_CANVAS_HEIGHT / 2);
            mouse = new MouseControl(center);
        }
        return mouse;
    }

    public static ControlData getData() {
        return getInstance().getData(Graphics.getCenter());
    }

}
