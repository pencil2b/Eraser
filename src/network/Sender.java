/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import control.ControlData;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
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
        // [done]
        // TCP
        // send name and UDP port
        // wait for the server to get an id
        // this will return an integer id
        try {
            tcp.write(name + "\n" + udp.getPort() + "\n");
            return Integer.parseInt(tcp.read());
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public void sendExit() {
        // [done]
        // TCP
        // send exit to the server and the server must disconnect
        tcp.write("exit\n");
    }
    
    public void sendControl(int id, ControlData data) {
        // [done]
        // buffer: id(int) x(double) y(double)
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeInt(id);
            dos.writeDouble(data.x);
            dos.writeDouble(data.y);
            byte[] buffer = baos.toByteArray();
            udp.write(buffer);
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}