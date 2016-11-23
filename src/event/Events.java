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
    public ArrayList<RankRow> ranks;
    public ArrayList<PlayerRow> list;
    
    boolean isReceivingRanks, isReceivingList;
    
    public Events(Game game) {
        this.game = game;
        isReceivingRanks = false;
        isReceivingList = false;
    }
    
    public void startAsId(int id) {
        // start game
    }

    public void beginReceivingRanks() {
        ranks.clear();
        isReceivingRanks = true;
    }

    public void endReceivingRanks() {
        isReceivingRanks = false;
        // show full ranks window
    }
    
    public void addRankRow(RankRow rankRow) {
        if (isReceivingRanks) {
            ranks.add(rankRow);
        }
    }

    public void beginReceivingList() {
        list.clear();
        isReceivingList = true;
    }

    public void endReceivingList() {
        isReceivingList = false;
        // update rank list in game
    }
    
    public void addPlayerRow(PlayerRow playerRow) {
        if (isReceivingList) {
            list.add(playerRow);
        }
    }
    
    public void die() {
        // do die
    }
}
