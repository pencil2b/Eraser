package data;

import game.Config;
import java.util.ArrayList;

/**
 * DOM module
 *
 * @author dorian
 */
public class World {

    public static void setSize(int w, int h) {
        getInstance().width = w;
        getInstance().height = h;
    }

    public static Player findPlayer(int id) {
        return getInstance().findPlayerById(id);
    }

    public static void update(ArrayList newPlayers, ArrayList newBullets) {
        getInstance().setObjects(newPlayers, newBullets);
    }

    public static void adjust() {
        getInstance().adjustPosotion();
    }

    public static int getWidth() {
        return getInstance().width;
    }

    public static int getHeight() {
        return getInstance().height;
    }

    public static ArrayList<Player> getPlayers() {
        return getInstance().players;
    }

    public static ArrayList<Bullet> getBullets() {
        return getInstance().bullets;
    }

    static World world;

    static World getInstance() {
        if (world == null) {
            world = new World();
        }
        return world;
    }

    ArrayList<Player> players;
    ArrayList<Bullet> bullets;

    int width, height;

    World() {
        width = Config.DEFAULT_WORLD_WIDTH;
        height = Config.DEFAULT_WORLD_HEIGHT;
        players = new ArrayList<>();
        bullets = new ArrayList<>();
    }

    void setObjects(ArrayList newPlayers, ArrayList newBullets) {
        players = newPlayers;
        bullets = newBullets;
    }

    void adjustPosotion() {
        players.forEach((p) -> {
            p.update();
        });
        bullets.forEach((b) -> {
            b.update();
        });
    }

    Player findPlayerById(int id) {
        for (Player player : players) {
            if (player.id == id) {
                return player;
            }
        }
        return null;
    }
}
