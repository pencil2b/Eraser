/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import world.Bullet;
import world.Player;
import world.World;

/**
 *
 * @author dorian
 */
public class Receiver {
    
    private final TCPSocket tcp;
    private final UDPSocket udp;
    
    public Receiver(TCPSocket tcp, UDPSocket udp) {
        this.tcp = tcp;
        this.udp = udp;
    }
    
    public void listenAndLoadWorld(World world) {
        // UDP (keep listening)
        // Receive a serialized World and do `world.load(xxx)`
        try {
            byte[] buffer = udp.read();
            
            ArrayList<Player> newPlayers = new ArrayList<>();
            ArrayList<Bullet> newBullets = new ArrayList<>();
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            DataInputStream dis = new DataInputStream(bais);
            
            int playerCount = dis.readInt();
            int bulletCount = 
            
            
            world.loadLists(newPlayers, newBullets);
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listenToTCPEvent() {
        // TCP (keep listening)
        // receive TCP data and dispatch incoming events
        // "#id\tID\n" Reset ID and start
        // "#rank\tBEGIN\n" Set receiving rank True
        // "#rank\t...\n" Store each record to list
        // "#rank\tEND\n" Flush and do something
        // "#list\tSTART\n" Start receiving list
        // "#list\t...\n" Store each record
        // "#list\tEND\n" Flush and do something
        // "#die\n" End the game and show endbar
    }
}
