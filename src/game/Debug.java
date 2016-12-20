package game;

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

    public static void error(String s) {
        if (enabled) {
            System.out.println("[!] " + s);
        }
    }

    public static void info(String s) {
        if (enabled) {
            System.out.println("[*] " + s);
        }
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
