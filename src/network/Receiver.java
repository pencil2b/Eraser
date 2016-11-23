/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import event.Events;
import event.PlayerRow;
import event.RankRow;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
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
        // [done]
        // UDP (keep listening)
        // Receive a serialized World and do `world.load(xxx)`
        try {
            byte[] buffer = udp.read();

            ArrayList<Player> newPlayers = new ArrayList<>();
            ArrayList<Bullet> newBullets = new ArrayList<>();

            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            DataInputStream dis = new DataInputStream(bais);

            int playerCount = dis.readInt();
            int bulletCount = dis.readInt();

            for (int i = 0; i < playerCount; i++) {
                int id = dis.readInt();
                float x = dis.readFloat();
                float y = dis.readFloat();
                int age = dis.readInt();
                int status = dis.readInt();
                Player player = new Player(id, x, y, age, status);
                newPlayers.add(player);
            }

            for (int i = 0; i < bulletCount; i++) {
                float x = dis.readFloat();
                float y = dis.readFloat();
                int status = dis.readInt();
                Bullet bullet = new Bullet(x, y, status);
                newBullets.add(bullet);
            }

            world.loadLists(newPlayers, newBullets);

        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listenToTCPEvents(Events events) {
        // [done]
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
        try {
            String eventString = tcp.read();
            String[] eventArgs = eventString.split("\t");
            switch (eventArgs[0]) {
                case "#id":
                    int id = Integer.parseInt(eventArgs[1]);
                    events.startAsId(id);
                    break;
                case "#rank":
                    if(eventArgs[1].equals("BEGIN")) {
                        events.beginReceivingRanks();
                    } else if(eventArgs[1].equals("END")) {
                        events.endReceivingRanks();
                    } else {
                        RankRow rankRow = new RankRow();
                        events.addRankRow(rankRow);
                    }
                    break;
                case "#list":
                    if(eventArgs[1].equals("BEGIN")) {
                        events.beginReceivingList();
                    } else if(eventArgs[1].equals("END")) {
                        events.endReceivingList();
                    } else {
                        PlayerRow playerRow = new PlayerRow();
                        events.addPlayerRow(playerRow);
                    }
                    break;
                case "#die":
                    events.die();
                    break;
                default:
                    System.out.println("Unknowen event: " + eventString);
            }
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
