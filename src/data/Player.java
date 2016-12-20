package data;

import control.Control;
import control.ControlData;
import game.Config;
import game.Game;
import java.awt.Point;

/**
 * Player data
 *
 * @author dorian
 */
public class Player {

    public double x, y;
    public int id, age, status, rank;
    public String name;

    public Player(int id, double x, double y, int age, int status) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.age = age;
        this.status = status;
        this.name = "";
    }

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Player(int id, String name, int age) {
        this(id, name);
        this.age = age;
    }

    public Player(int id, String name, int age, int rank) {
        this(id, name, age);
        this.rank = rank;
    }

    public int x() {
        return (int) this.x;
    }

    public int y() {
        return (int) this.y;
    }

    public void update() {
        if (id != Game.id || Game.isDead) {
            return;
        }
        ControlData cd = Control.getData();
        x += cd.x * Config.PLAYER_VELOCITY;
        y += cd.y * Config.PLAYER_VELOCITY;

        if (x > World.getWidth()) {
            x = World.getWidth();
        }
        if (y > World.getHeight()) {
            y = World.getHeight();
        }
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
    }

    public Point position() {
        return new Point(x(), y());
    }
}
