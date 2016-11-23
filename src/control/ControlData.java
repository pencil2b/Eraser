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
    
    private final float R = 200;
    public float x, y;
    
    // input `p` is mouse - center
    public ControlData(Point p) {
        // unit vector
        double d = p.distance(new Point(0, 0));
        if(d < R) {
            x = p.x / R;
            y = p.y / R;
        } else {
            x = (float) (p.x / d);
            y = (float) (p.y / d);
        }
    }
}
