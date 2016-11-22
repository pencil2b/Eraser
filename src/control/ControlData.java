/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Point;

/**
 *
 * @author dorian
 */
public class ControlData {
    
    public double x, y;
    
    // input `p` is mouse - center
    public ControlData(Point p) {
        // unit vector
        double d = p.distance(new Point(0, 0));
        x = (p.x / d);
        y = (p.y / d);
    }
}
