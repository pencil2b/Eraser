package network;

import control.Control;
import game.Game;
import java.util.ArrayList;
import data.Player;
import java.io.IOException;

/**
 * Network module
 *
 * @author dorian
 */
public class Network {

    public static void init() throws IOException {
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
}
