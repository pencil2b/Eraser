/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import world.World;

/**
 *
 * @author dorian
 */
public class Receiver {
    
    private final TCPSocket tcp;
    private final UDPSocket udp;
    
    public Receiver(TCPSocket tcp, UDPSocket udp) {
        this.tcp = tcp;
        this.udp = udp;
    }
    
    public void listenAndLoadWorld(World world) {
        // UDP (keep listening)
        // Receive a serialized world and do `world.load(xxx)`
    }
    
    public void listenToTCP() {
        // TCP (keep listening)
        // receive TCP data and dispatch incoming events
    }
}
