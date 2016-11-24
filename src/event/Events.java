/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import eraser.Game;
import java.util.ArrayList;

/**
 *
 * @author dorian
 */
public class Events {
    
    private Game game;
    public ArrayList<RankRow> fullRanks;
    public ArrayList<PlayerRow> playerList;
    
    boolean isReceivingRanks, isReceivingList;
    
    public Events(Game game) {
        this.game = game;
        isReceivingRanks = false;
        isReceivingList = false;
        playerList = new ArrayList<>();
        fullRanks = new ArrayList<>();
    }
    
    public void startAsId(int id) {
        // [!] start game
    }
    
    public void setWorldSize(int width, int height) {
        game.world.width = width;
        game.world.height = height;
    }

    public void loadPlayerList(ArrayList<PlayerRow> playerList) {
        this.playerList = playerList;
        playerList.forEach((row) -> {
            System.out.println("player: " + row.id + " " + row.name + " " + " " + row.rank);
        });
    }
    
    public void loadFullRanks(ArrayList<RankRow> fullRanks) {
        this.fullRanks = fullRanks;
    }
    
    public void die() {
        // [!] do die
    }
    
}
