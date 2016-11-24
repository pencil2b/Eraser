/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import eraser.Debug;
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
                newPlayers.add(player);;
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
                    Debug.show("Receive RANK");
                    int rankCount = Integer.parseInt(eventArgs[1]), rankIndex = 2;
                    ArrayList<RankRow> rankList = new ArrayList<>();
                    for(int i = 0; i < rankCount; i++) {
                        int id = Integer.parseInt(eventArgs[rankIndex++]);
                        String name = eventArgs[rankIndex++];
                        int age = Integer.parseInt(eventArgs[rankIndex++]);
                        int rank = Integer.parseInt(eventArgs[rankIndex++]);
                        rankList.add(new RankRow(id, name, age, rank));
                    }
                    events.loadFullRanks(rankList);
                    break;
                case "list":
                    Debug.show("Receive LIST");
                    int listCount = Integer.parseInt(eventArgs[1]), listIndex = 2;
                    ArrayList<PlayerRow> playerList = new ArrayList<>();
                    for(int i = 0; i < listCount; i++) {
                        int id = Integer.parseInt(eventArgs[listIndex++]);
                        String name = eventArgs[listIndex++];
                        int rank = Integer.parseInt(eventArgs[listIndex++]);
                        playerList.add(new PlayerRow(id, name, rank));
                    }
                    events.loadPlayerList(playerList);
                    break;
                case "size":
                    Debug.show("Receive SIZE");
                    int width = Integer.parseInt(eventArgs[1]);
                    int height = Integer.parseInt(eventArgs[2]);
                    events.setWorldSize(width, height);
                    break;
                case "die":
                    Debug.show("Receive DIE");
                    events.die();
                    break;
                default:
                    Debug.error("UnknowenEvent: " + eventString);
            }
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
