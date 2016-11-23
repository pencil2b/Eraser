/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dorian
 */
public class TCPSocket {
    
    private String ip;
    private int port;
    private Socket socket;
    private BufferedReader reader;
    private PrintStream writer;
    
    public TCPSocket(String remoteIp, int remotePort) {
        try {
            ip = remoteIp;
            port = remotePort;
            socket = new Socket(ip, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(TCPSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void write(String s) {
        writer.print(s);
    }
    
    public String read() throws IOException {
        return reader.readLine();
    }
}
