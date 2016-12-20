package control;

import java.awt.Point;

/**
 * Control data
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
        if (d < R) {
            x = p.x / R;
            y = p.y / R;
        } else {
            x = (float) (p.x / d);
            y = (float) (p.y / d);
        }
    }
}
