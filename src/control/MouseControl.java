/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Point;
import java.awt.event.MouseEvent;;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author dorian
 */
public class MouseControl implements MouseMotionListener {
    
    public Point position;
    
    public MouseControl(Point begin) {
        this.position = begin;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        position.x = e.getX();
        position.y = e.getY();
    }
    
    public ControlData getData(Point center) {
        Point point = new Point(position.x - center.x, position.y - center.y);
        return new ControlData(point);
    }
}
