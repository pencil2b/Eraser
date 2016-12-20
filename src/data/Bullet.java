package data;

import java.awt.Point;

/**
 * Bullet data
 *
 * @author dorian
 */
public class Bullet {

    public double x, y;
    public double vx, vy;

    public Bullet(double x, double y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public int x() {
        return (int) this.x;
    }

    public int y() {
        return (int) this.y;
    }

    public void update() {
        x += vx;
        y += vy;
    }

    public Point position() {
        return new Point(x(), y());
    }
}
