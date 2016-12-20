package network;

import control.ControlData;
import game.Debug;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sender
 *
 * @author dorian
 */
class Sender {

    static int sendLoginAndGetId(String name) {
        String s = name + "\t" + UDP.getPort();
        TCP.write(s + "\n");
        s = TCP.read();
        int id = Integer.parseInt(s.split("\t")[1]);
        return id;
    }

    static void sendRestart() {
        TCP.write("restart\n");
    }

    static void sendFullListRequest() {
        TCP.write("full\n");
    }

    static void sendControl(int id, ControlData data) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeShort(id);
            dos.writeFloat(data.x);
            dos.writeFloat(data.y);
            byte[] buffer = baos.toByteArray();
            UDP.write(buffer);
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
