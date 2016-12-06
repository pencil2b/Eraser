
package game;

import data.World;
import control.*;
import network.*;
import graphics.*;
import javax.swing.JFrame;
import java.util.ArrayList;

/**
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
        world = new World();
        isStopped = false;
        isDead = false;
        
        
        String info = StartDialog.getInfo().trim();
        
        if("".equals(info) || info == null) {
            System.exit(0);
        }
        
        try {
            name = info.split("@")[0];
            server = info.split("@")[1];
        } catch(Exception e) {
            System.exit(0);
        }
        
        Network.init();
        id = Network.sendLoginAndGetId();
        Debug.info(" ID: " + id);
        
        Events.init();
        Control.init();
        Graphics.init();
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

        // Setting daemon true makes the thread stop when the main thread stop
        threads.stream().map((thread) -> {
            thread.setDaemon(true);
            return thread;
        }).forEachOrdered((thread) -> {
            thread.start();
        });
        
        Graphics.getCanvas().requestFocus();
        window.setVisible(true);
    }

    private static void graphicsLoop() {
        Hertz hertz = new Hertz(Config.GRAPHICS_UPS);
        while (!isStopped) {
            hertz.adjust();
            Graphics.render();
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
    
    private static void controlSendingLoop() {
        Hertz hertz = new Hertz(Config.CONTROL_UPS);
        while (!isStopped) {
            hertz.adjust();
            if(!isDead) {
                Network.sendControl();
            }
        }
    }
    
    public static void stop() {
        isStopped = true;
    }
}
