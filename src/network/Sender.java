/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import control.ControlData;
import game.Debug;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dorian
 */
class Sender {

    static void init() {

    }

    static int sendLoginAndGetId(String name) {
        String s = name + "\t" + UDP.getPort();
        Debug.tcpSend(s);
        TCP.write(s + "\n");
        s = TCP.read();
        int id = Integer.parseInt(s.split("\t")[1]);
        return id;
    }

    static void sendExit() {
        TCP.write("exit\n");
    }

    static void sendRestart() {
        TCP.write("restart\n");
    }

    static void sendFullListRequest() {
        TCP.write("full\n");
    }

    static void sendVisibleSize(int width, int height) {
        TCP.write(String.format("visible\t%d\t%d\n", width, height));
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
