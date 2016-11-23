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

            int playerCount = dis.readShort();
            int bulletCount = dis.readShort();

            for (int i = 0; i < playerCount; i++) {
                int id = dis.readShort();
                double x = dis.readFloat();
                double y = dis.readFloat();
                int age = dis.readShort();
                int status = dis.readByte();
                Player player = new Player(id, x, y, age, status);
                newPlayers.add(player);
            }

            for (int i = 0; i < bulletCount; i++) {
                double x = dis.readFloat();
                double y = dis.readFloat();
                int status = dis.readByte();
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
        // "id\tID\n" Reset ID and start
        // "rank\tBEGIN\n" Set receiving rank True
        // "rank\t...\n" Store each record to list
        // "rank\tEND\n" Flush and do something
        // "list\tSTART\n" Start receiving list
        // "list\t...\n" Store each record
        // "list\tEND\n" Flush and do something
        // "die\n" End the game and show endbar
        try {
            String eventString = tcp.read();
            String[] eventArgs = eventString.split("\t");
            switch (eventArgs[0]) {
                case "id":
                    int idId = Integer.parseInt(eventArgs[1]);
                    System.out.println("[+] Start as ID: " + idId);
                    events.startAsId(idId);
                    break;
                case "rank":
                    if(eventArgs.length == 2 && eventArgs[1].equals("BEGIN")) {
                        System.out.println("[+] Rank BEGIN");
                        events.beginReceivingRanks();
                    } else if(eventArgs.length == 2 && eventArgs[1].equals("END")) {
                        System.out.println("[+] Rank END");
                        events.endReceivingRanks();
                    } else if(eventArgs.length == 5) {
                        int rankId = Integer.parseInt(eventArgs[1]);
                        String rankName = eventArgs[2];
                        int rankAge = Integer.parseInt(eventArgs[3]);
                        int rankRank = Integer.parseInt(eventArgs[4]);
                        System.out.println("[+] Rank: " + rankId + " " + rankName + " " + rankAge + " " + rankRank);
                        RankRow rankRow = new RankRow(rankId, rankName, rankAge, rankRank);
                        events.addRankRow(rankRow);
                    }
                    break;
                case "list":
                    if(eventArgs.length == 2 && eventArgs[1].equals("BEGIN")) {
                        System.out.println("[+] List BRGIN");
                        events.beginReceivingList();
                    } else if(eventArgs.length == 2 && eventArgs[1].equals("END")) {
                        System.out.println("[+] List END");
                        events.endReceivingList();
                    } else if(eventArgs.length == 4) {
                        int listId = Integer.parseInt(eventArgs[1]);
                        String listName = eventArgs[2];
                        int listRank = Integer.parseInt(eventArgs[3]);
                        System.out.println("[+] List: " + listId + " " + listName + " " + listRank);
                        PlayerRow playerRow = new PlayerRow(listId, listName, listRank);
                        events.addPlayerRow(playerRow);
                    }
                    break;
                case "die":
                    System.out.println("[+] Received DIE");
                    events.die();
                    break;
                default:
                    System.out.println("[-] UnknowenEvent: " + eventString);
            }
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
