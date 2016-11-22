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
        ObjectInputStream ois = null;
        try {
            // UDP (keep listening)
            // [!] Game needs to create a thread for this
            // receive a serialized World and parse to the World object
            // [!] this.world.load(parsedSerializedWorld);
            byte[] buffer = udp.read();
            if(buffer.length > 0) {
                System.out.println("LOAD: " + buffer.toString());
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ois = new ObjectInputStream(new BufferedInputStream(bais));
            try {
                world.loadData((World) ois.readObject());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            ois.close();
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void listenToTCP() {
        // TCP (keep listening)
        // [!] Game needs to create a thread for this
        // receive TCP data and dispatch incoming events
    }
}
