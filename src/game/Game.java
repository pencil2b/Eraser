package game;

import data.World;
import graphics.Graphics;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import java.util.ArrayList;
import network.Network;

/**
 * Game module
 *
 * @author dorian
 */
public class Game {

    public static JFrame window;
    public static World world;
    private static boolean isStopped;
    public static String server;
    public static int id;
    public static String name;
    public static boolean isDead;

    public static void init() {
        window = new GameWindow();
        isStopped = false;
        isDead = false;

        String info = StartDialog.getInfo();

        try {
            String[] infoSplit = info.trim().split("@");
            name = infoSplit[0];
            server = infoSplit[1];

            Debug.info("Connecting to " + server);
            Network.init();
            Debug.success("Connection established");

            id = Network.sendLoginAndGetId();
            Debug.success("Login id=" + id);

            Debug.info("Initialize graphics");
            Graphics.init();
        } catch (SocketTimeoutException e) {
            Debug.die("Connection timeout.");
        } catch (UnknownHostException e) {
            Debug.die("Invalid host name.");
        } catch (IOException e) {
            Debug.die("Connection failed.");
        } catch (ArrayIndexOutOfBoundsException e) {
            Debug.die("Invalid input.");
        } catch (NumberFormatException e) {
            Debug.die("Port is an integer, please!");
        } catch (IllegalArgumentException e) {
            Debug.die("Not a valid port.");
        } catch(NullPointerException e) {
            System.exit(0);
        } catch (Exception e) {
            Debug.die("WTF exception");
        }
    }

    public static void start() {

        ArrayList<Thread> threads = new ArrayList<>();

        threads.add(new Thread() {
            @Override
            public void run() {
                graphicsLoop();
            }
        });

        threads.add(new Thread() {
            @Override
            public void run() {
                updateWorldLoop();
            }
        });

        threads.add(new Thread() {
            @Override
            public void run() {
                eventDispatchLoop();
            }
        });

        threads.add(new Thread() {
            @Override
            public void run() {
                controlSendingLoop();
            }
        });

        Debug.info("Start threads");
        threads.stream().map((thread) -> {
            thread.setDaemon(true);
            return thread;
        }).forEachOrdered((thread) -> {
            thread.start();
        });

        Debug.info("Show window");
        window.setVisible(true);
    }

    private static void graphicsLoop() {
        Hertz hertz = new Hertz(Config.GRAPHICS_UPS);

        while (!isStopped) {
            World.adjust(); // Better animation
            hertz.adjust();
            Graphics.render();
        }
    }

    private static void controlSendingLoop() {
        Hertz hertz = new Hertz(Config.CONTROL_UPS);

        while (!isStopped) {
            hertz.adjust();

            if (!isDead) {
                Network.sendControl();
            }
        }
    }

    private static void updateWorldLoop() {
        while (!isStopped) {
            Network.updateWorld();
        }
    }

    // update rank, your status, 
    private static void eventDispatchLoop() {
        while (!isStopped) {
            Network.dispatchEvent();
        }
    }

    public static void stop() {
        Debug.info("Stop");
        isStopped = true;
    }
}
