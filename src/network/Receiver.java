package network;

import data.Player;
import data.Bullet;
import game.Debug;
import game.Events;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Receiver
 *
 * @author dorian
 */
class Receiver {

    static void updateWorld() {
        // [done]
        // UDP (keep listening)
        // Receive a serialized World and do `world.load(xxx)`
        try {
            byte[] buffer = UDP.read();

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
                double vx = dis.readFloat();
                double vy = dis.readFloat();
                Bullet bullet = new Bullet(x, y, vx, vy);
                newBullets.add(bullet);
            }
            Events.updateWorld(newPlayers, newBullets);

        } catch (IOException ex) {
            Debug.die("Error updating world");
        }
    }

    static void dispatchEvent() {
        try {
            String eventString = TCP.read();
            String[] eventArgs = eventString.split("\t");
            switch (eventArgs[0]) {
                case "list":
                    int listCount = Integer.parseInt(eventArgs[1]),
                     listIndex = 2;
                    Debug.info("Receive LIST: count=" + listCount);
                    HashMap<Integer, String> nameList = new HashMap<>();
                    ArrayList<Player> rankList = new ArrayList<>();
                    for (int i = 0; i < listCount; i++) {
                        int id = Integer.parseInt(eventArgs[listIndex++]);
                        String name = eventArgs[listIndex++];
                        nameList.put(id, name);
                        rankList.add(new Player(id, name));
                        Debug.info("Receive LIST: id=" + id + " name=" + name);
                    }
                    Events.updateNameList(nameList);
                    Events.updateRankList(rankList);
                    break;
                case "size":
                    int width = Integer.parseInt(eventArgs[1]);
                    int height = Integer.parseInt(eventArgs[2]);
                    Debug.info("Receive SIZE: width=" + width + " height=" + height);
                    Events.setWorldSize(width, height);
                    break;
                case "die":
                    Debug.info("Receive DIE");
                    Events.die();
                    break;
                default:
                    Debug.die("Receive UNKNOWEN: " + eventString);
            }
        } catch (IOException ex) {
            Debug.die("Error dispatching event");
        } catch(NullPointerException e) {
            Debug.die("Connection error");
        }
    }

    static ArrayList<Player> receiveFullList() {
        String[] eventArgs = TCP.read().split("\t");
        while (!eventArgs[0].equals("full")) {
            eventArgs = TCP.read().split("\t");
        }
        int rankCount = Integer.parseInt(eventArgs[1]), rankIndex = 2;
        ArrayList<Player> fullList = new ArrayList<>();
        Debug.info("Receive FULL: count=" + rankCount);
        for (int i = 0; i < rankCount; i++) {
            int id = Integer.parseInt(eventArgs[rankIndex++]);
            String name = eventArgs[rankIndex++];
            int age = Integer.parseInt(eventArgs[rankIndex++]);
            fullList.add(new Player(id, name, age));
            Debug.info("Receive FULL: id=" + id + " name=" + name + " age=" + age);
        }
        return fullList;
    }
}
