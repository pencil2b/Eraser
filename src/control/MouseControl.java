/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Point;
import java.awt.event.MouseEvent;import java.awt.event.MouseListener;
;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author dorian
 */
public class MouseControl implements MouseMotionListener, MouseListener {
    
    Point position;
    boolean lock = false;
    ControlData savedControl;
    
    MouseControl(Point begin) {
        this.position = begin;
    }
    
    ControlData getData(Point center) {
        if(lock) {
            return savedControl;
        }
        Point point = new Point(position.x - center.x, position.y - center.y);
        savedControl = new ControlData(point);
        return savedControl;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        position.x = e.getX();
        position.y = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        lock = !lock;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}
