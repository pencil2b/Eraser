/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import game.Debug;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dorian
 */
class TCP {

    private static String ip;
    private static int port;
    private static Socket socket;
    private static BufferedReader reader;
    private static PrintStream writer;

    static void init(String remoteIp, int remotePort) throws IOException {
        ip = remoteIp;
        port = remotePort;
        socket = new Socket();
        socket.connect(new InetSocketAddress(ip, port), 2000);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintStream(socket.getOutputStream());
    }

    static void write(String s) {
        writer.print(s);
        Debug.tcpSend(s.trim());
    }

    static String read() {
        try {
            String s = reader.readLine();
            Debug.tcpRecv(s);
            return s;
        } catch (IOException ex) {
            Logger.getLogger(TCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
