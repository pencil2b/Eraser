/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import game.Debug;
import game.Game;

/**
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
