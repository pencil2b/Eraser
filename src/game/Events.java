package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import network.Network;
import data.Bullet;
import data.Player;
import data.World;

/**
 * Events module
 *
 * @author dorian
 */
public class Events {

    static ArrayList<Player> rankList;
    static HashMap<Integer, String> nameList;

    public static HashMap<Integer, String> getNameList() {
        if (nameList == null) {
            nameList = new HashMap<>();
        }

        return nameList;
    }

    public static ArrayList<Player> getRankList() {
        if (rankList == null) {
            rankList = new ArrayList<>();
        }

        return rankList;
    }

    public static void setWorldSize(int width, int height) {
        World.setSize(width, height);
    }

    public static void updateNameList(HashMap<Integer, String> nameList) {
        Events.nameList = nameList;
    }

    public static void updateRankList(ArrayList<Player> rankList) {
        Events.rankList = rankList;
    }

    public static void updateWorld(ArrayList<Player> newPlayers, ArrayList<Bullet> newBullets) {
        newPlayers.forEach((player) -> {
            String name = getNameList().get(player.id);
            player.name = (name == null ? "" : name);
        });

        World.update(newPlayers, newBullets);
    }

    public static void die() throws IOException {
        Game.isDead = true;
        Player me = World.findPlayer(Game.id);

        int code = GameOverDialog.show(Game.window, new Player(1, getNameList().get(Game.id), me.age, 1));

        switch (code) {
            case 0: // Exit
                System.exit(0);
                break;
            case 1: // Rank
                Network.sendFullListRequest();
                RankListDialog.show(Game.window, Network.receiveFullList());
                die();
                break;
            case 2: // Restart
                Network.sendRestart();
                Game.isDead = false;
                break;
            default:
                System.exit(0);
        }
    }
}
