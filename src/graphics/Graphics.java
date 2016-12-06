/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import control.Control;
import game.Config;
import game.Events;
import game.Game;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Point;

/**
 *
 * @author dorian
 */
public class Graphics {
    
    public static Scene canvas;
    
    public static void init() {
        Dimension canvasSize = new Dimension(Config.DEFAULT_CANVAS_WIDTH, Config.DEFAULT_CANVAS_HEIGHT);
        canvas = new Scene(canvasSize);
        Game.window.add(canvas);
        canvas.setIgnoreRepaint(true);
        canvas.createBufferStrategy(2);
        canvas.requestFocus();
        canvas.addListeners(Control.mouse);
    }
    
    public static void render() {
        canvas.render(Game.world, Game.id, Events.rankList);
    }
    
    public static Point getCenter() {
        return canvas.getCenter();
    }
    
    public static Canvas getCanvas() {
        return canvas;
    }
    
}
