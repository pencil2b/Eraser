/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import control.ControlData;
import eraser.Debug;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dorian
 */
public class Sender {
    
    private final TCPSocket tcp;
    private final UDPSocket udp;
    
    public Sender(TCPSocket tcp, UDPSocket udp) {
        this.udp = udp;
        this.tcp = tcp;
    }
    
    public int sendLoginAndGetId(String name) {
        try {
            String s = name + "\t" + udp.getPort();
            Debug.tcpSend(s);
            tcp.write(s + "\n");
            s = tcp.read();
            int id = Integer.parseInt(s.split("\t")[1]);
            return id;
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public void sendExit() {
        tcp.write("exit\n");  
    }
    
    public void sendRestart() {
        tcp.write("restart\n");  
    }
    
    public void sendFullListRequest() {
        tcp.write("full\n");
    }
    
    public void sendVisibleSize(int width, int height) {
        tcp.write(String.format("visible\t%d\t%d\n", width, height));
    }
    
    public void sendControl(int id, ControlData data) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeShort(id);
            dos.writeFloat(data.x);
            dos.writeFloat(data.y);
            byte[] buffer = baos.toByteArray();
            udp.write(buffer);
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}