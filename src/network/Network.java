/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import control.Control;
import game.Game;
import java.util.ArrayList;
import data.Player;

/**
 *
 * @author dorian
 */
public class Network {  
    
    public static void init() {
        String ip = Game.server.split(":")[0];
        int port = Integer.parseInt(Game.server.split(":")[1]);
        TCP.init(ip, port);
        UDP.init(ip, port);
    }
    
    public static void updateWorld() {
        Receiver.updateWorld();
    }

    public static void dispatchEvent() {
        Receiver.dispatchEvent();
    }

    public static ArrayList<Player> receiveFullList() {
        return Receiver.receiveFullList();
    }
    
    public static int sendLoginAndGetId() {
        return Sender.sendLoginAndGetId(Game.name);
    }
    
    public static void sendRestart() {
        Sender.sendRestart();
    }
    
    public static void sendFullListRequest() {
        Sender.sendFullListRequest();
    }

    public static void sendControl() {
        Sender.sendControl(Game.id, Control.getData());
    }
    
    public static void tcpWrite(String data) {
        TCP.write(data);
    }
    
    public static String tcpRead() {
        return TCP.read();
    }
    
    public static void udpWrite(byte[] data) {
        UDP.write(data);
    }
    
    public static byte[] udpRead() {
        return UDP.read();
    }
}
