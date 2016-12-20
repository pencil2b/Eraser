package graphics;

import control.Control;
import game.Config;
import game.Game;
import java.awt.Dimension;
import java.awt.Point;

/**
 * Graphics module
 *
 * @author dorian
 */
public class Graphics {

    static Scene canvas;

    public static void init() {
        Game.window.add(getCanvas());
        getCanvas().setIgnoreRepaint(true);
        getCanvas().createBufferStrategy(2);
        getCanvas().requestFocus();
        getCanvas().addListeners(Control.getInstance());
    }

    public static void render() {
        getCanvas().render();
    }

    public static Point getCenter() {
        return getCanvas().getCenter();
    }

    static Scene getCanvas() {
        if (canvas == null) {
            Dimension canvasSize = new Dimension(Config.DEFAULT_CANVAS_WIDTH, Config.DEFAULT_CANVAS_HEIGHT);
            canvas = new Scene(canvasSize);
        }
        return canvas;
    }

}
