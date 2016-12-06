/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import network.Network;
import data.Bullet;
import data.Player;

/**
 *
 * @author dorian
 */
public class Events {
    
    public static ArrayList<Player> rankList;
    public static HashMap<Integer, String> nameList;
    
    public static void init() {
        nameList = new HashMap<>();
        rankList = new ArrayList<>();
    }

    public static void setWorldSize(int width, int height) {
        Game.world.width = width;
        Game.world.height = height;
    }

    public static void updateNameList(HashMap<Integer, String> nameList) {
        Events.nameList = nameList;
    }
    
    public static void updateRankList(ArrayList<Player> rankList) {
        Events.rankList = rankList;
    }
    
    public static void updateWorld(ArrayList<Player> newPlayers, ArrayList<Bullet> newBullets) {
        newPlayers.forEach((player) -> {
            String name = nameList.get(player.id);
            player.name = (name == null ? "" : name);
        });
        Game.world.update(newPlayers, newBullets);
    }
    
    public static void die() throws IOException {
        Game.isDead = true;
        Player me = Game.world.findPlayer(Game.id);
        int code = GameOverDialog.show(Game.window, new Player(1, nameList.get(Game.id), me.age, 1));
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
        }
    }
}
