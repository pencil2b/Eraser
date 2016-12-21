package game;

import javax.swing.JOptionPane;

/**
 * Print debug messages
 *
 * @author dorian
 */
public class Debug {

    public static boolean enabled;

    public static void notice(String s) {
        if (enabled) {
            System.out.println("[?] " + s);
        }
    }

    public static void success(String s) {
        if (enabled) {
            System.out.println("[+] " + s);
        }
    }

    public static void info(String s) {
        if (enabled) {
            System.out.println("[*] " + s);
        }
    }
    
    public static void error(String s) {
        if (enabled) {
            System.out.println("[!] " + s);
        }
    }
    
    public static void die(String s) {
        if (enabled) {
            System.out.println("[X] " + s);
        }
        Game.stop();
        JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }

    public static void print(String s) {
        if (enabled) {
            System.out.println("> " + s);
        }
    }

    public static void tcpRecv(String s) {
        if (enabled) {
            System.out.println("TCP >> " + s);
        }
    }

    public static void tcpSend(String s) {
        if (enabled) {
            System.out.println("TCP << " + s);
        }
    }
}
