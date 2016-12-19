/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dorian
 */
class UDP {

    private static InetAddress ip;
    private static int port;
    private static DatagramSocket socket;

    static void init(String remoteIp, int remotePort) throws UnknownHostException, SocketException {
        ip = InetAddress.getByName(remoteIp);
        port = remotePort;
        socket = new DatagramSocket();
        
    }

    static void write(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
        try {
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(UDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static byte[] read() {
        byte[] buffer = new byte[65536];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(packet);
        } catch (IOException ex) {
            Logger.getLogger(UDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Arrays.copyOf(buffer, packet.getLength());
    }

    static int getPort() {
        return socket.getLocalPort();
    }
}
