package main;

import game.Debug;
import game.Game;

/**
 * Main class
 *
 * @author dorian
 */
public class Main {

    public static void main(String[] args) {
        Debug.enabled = true;
        
        Game.init();
        Game.start();
    }
}
