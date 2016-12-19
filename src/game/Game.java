
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
        isStopped = false;
        isDead = false;
        
        
        String info = StartDialog.getInfo();
        
        if(info == null || info.equals("")) {
            System.exit(0);
        }
        
        try {
            String[] infoSplit = info.trim().split("@");
            name = infoSplit[0];
            server = infoSplit[1];
        } catch(Exception e) {
            System.exit(0);
        }
        
        Network.init();
        id = Network.sendLoginAndGetId();
        Debug.info(" ID: " + id);
        
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
        
        
        window.setVisible(true);
    }

    private static void graphicsLoop() {
        Hertz hertz = new Hertz(Config.GRAPHICS_UPS);
        while (!isStopped) {
            hertz.adjust();
            Graphics.render();
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
        isStopped = true;
    }
}
