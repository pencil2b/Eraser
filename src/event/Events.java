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

    public void beginReceivingRanks() {
        fullRanks.clear();
        isReceivingRanks = true;
    }

    public void endReceivingRanks() {
        isReceivingRanks = false;
        // [!] show full ranks window
    }
    
    public void addRankRow(RankRow rankRow) {
        if (isReceivingRanks) {
            fullRanks.add(rankRow);
        }
    }

    public void beginReceivingList() {
        playerList.clear();
        isReceivingList = true;
    }

    public void endReceivingList() {
        isReceivingList = false;
        // [!] update rank list in game
    }
    
    public void addPlayerRow(PlayerRow playerRow) {
        if (isReceivingList) {
            playerList.add(playerRow);
        }
    }
    
    public void die() {
        // [!] do die
    }
    
}
