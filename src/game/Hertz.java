/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author dorian
 */
class Hertz {
    
    private final double NANO_TO_BASE = 1.0e9;
    private long lastUpdateTime;
    private double deltaTime;
    private int hertz;
    
    public Hertz(int hertz) {
        this.hertz = hertz;
        lastUpdateTime = System.nanoTime();
    }
    
    public void adjust() {
        deltaTime = getDelta();
        if (deltaTime < 1.0 / hertz) {
            try {
                Thread.sleep((long) ((1.0 / hertz - deltaTime) * 1000));
            } catch (InterruptedException e) { }
        }
        deltaTime = getDelta();
        lastUpdateTime = System.nanoTime();
    }

    private double getDelta() {
        return (System.nanoTime() - this.lastUpdateTime) / NANO_TO_BASE;
    }
    
    public void set(int hertz) {
        this.hertz = hertz;
    }
}
