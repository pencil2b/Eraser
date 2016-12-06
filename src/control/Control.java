/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import game.Config;
import graphics.Graphics;
import java.awt.Point;


/**
 *
 * @author dorian
 */
public class Control {
    
    public static MouseControl mouse;
    
    public static void init() {
        Point center = new Point(Config.DEFAULT_CANVAS_WIDTH / 2, Config.DEFAULT_CANVAS_HEIGHT / 2);
        mouse = new MouseControl(center);
    }
    
    public static ControlData getData() {
        return mouse.getData(Graphics.getCenter());
    }
    
}
