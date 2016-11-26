/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import eraser.Debug;
import eraser.Events;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import world.*;

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

    public void listenAndLoadWorld(Events events) {
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

            events.updateWorld(newPlayers, newBullets);

        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listenToTCPEvents(Events events) {
        try {
            String eventString = tcp.read();
            String[] eventArgs = eventString.split("\t");
            Debug.info("<TCP>: " + eventString);
            switch (eventArgs[0]) {
                case "full":
                    int rankCount = Integer.parseInt(eventArgs[1]), rankIndex = 2;
                    ArrayList<Player> fullList = new ArrayList<>();
                    Debug.show("Receive FULL: count=" + rankCount);
                    for(int i = 0; i < rankCount; i++) {
                        int id = Integer.parseInt(eventArgs[rankIndex++]);
                        String name = eventArgs[rankIndex++];
                        int age = Integer.parseInt(eventArgs[rankIndex++]);
                        fullList.add(new Player(id, name, age));
                        Debug.show("Receive FULL: id=" + id + " name=" + name + " age=" + age);
                    }
                    events.loadFullRanks(fullList);
                    break;
                case "list":
                    int listCount = Integer.parseInt(eventArgs[1]), listIndex = 2;
                    Debug.show("Receive LIST: count=" + listCount);
                    HashMap<Integer, String> nameList = new HashMap<>();
                    ArrayList<Player> rankList = new ArrayList<>();
                    for(int i = 0; i < listCount; i++) {
                        int id = Integer.parseInt(eventArgs[listIndex++]);
                        String name = eventArgs[listIndex++];
                        nameList.put(id, name);
                        rankList.add(new Player(id, name));
                        Debug.show("Receive LIST: id=" + id + " name=" + name);
                    }
                    events.updateNameList(nameList);
                    events.updateRankList(rankList);
                    break;
                case "size":
                    int width = Integer.parseInt(eventArgs[1]);
                    int height = Integer.parseInt(eventArgs[2]);
                    Debug.show("Receive SIZE: width=" + width + " height=" + height);
                    events.setWorldSize(width, height);
                    break;
                case "die":
                    Debug.show("Receive DIE");
                    events.die();
                    break;
                default:
                    Debug.error("Receive UNKNOWEN: " + eventString);
            }
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
