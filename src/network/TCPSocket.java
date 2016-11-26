/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import eraser.Debug;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

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

    public TCPSocket(String remoteIp, int remotePort) throws IOException {

        ip = remoteIp;
        port = remotePort;
        socket = new Socket(ip, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintStream(socket.getOutputStream());
    }

    public void write(String s) {
        writer.print(s);
        Debug.tcpSend(s.trim());
    }

    public String read() throws IOException {
        String s = reader.readLine();
        Debug.tcpRecv(s);
        return s;
    }
}
