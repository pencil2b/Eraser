
package eraser;

import control.*;
import network.*;
import graphics.*;
import world.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dorian
 */
public class Game extends JFrame {

    private GameCanvas canvas;
    public World world;
    private boolean isStopped;
    public TCPSocket tcp;
    public UDPSocket udp;
    public Sender sender;
    public Receiver receiver;
    private MouseControl mouseControl;
    private Events events;
    public int id;
    public boolean isDead;

    private final int WIDTH = 800, HEIGHT = 600;

    public Game() {
        super("Eraser");
        String info = Start.getInfo(this);
        if(info == "" || info == null) {
            System.exit(0);
        }
        try {
            setup(info.split("@")[0], info.split("@")[1]);
        } catch(Exception e) {
            System.exit(0);
        }
        start();
    }

    private void setup(String name, String server) {
        isStopped = false;
        world = new World();
        events = new Events(this);
        
        // Setup network
        String ip = server.split(":")[0];
        int port = Integer.parseInt(server.split(":")[1]);
        try {
            tcp = new TCPSocket(ip, port);
        } catch (IOException ex) {
            System.exit(1);
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        udp = new UDPSocket(ip, port);
        sender = new Sender(tcp, udp);
        receiver = new Receiver(tcp, udp);

        // Login
        id = sender.sendLoginAndGetId(name);
        System.out.println("[*] ID: " + id);
        
        // Setup canvas
        Dimension canvasSize = new Dimension(WIDTH, HEIGHT);
        canvas = new GameCanvas(canvasSize);
        add(canvas);

        // Setup window location
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowLocationX = (int) ((screenSize.getWidth() - WIDTH) / 2);
        int windowLocationY = (int) ((screenSize.getHeight() - HEIGHT) / 2);
        setLocation(windowLocationX, windowLocationY);

        // Setup window size
        setMinimumSize(new Dimension(640, 480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stop();
                super.windowClosing(e);
            }
        });
        
        // Setup control
        mouseControl = new MouseControl(canvas.getCenter());
        canvas.addMouseMotionListener(mouseControl);
        canvas.addMouseListener(mouseControl);
        
        this.pack();
    }

    private void start() {
        
        canvas.setIgnoreRepaint(true);
        canvas.createBufferStrategy(2);
        
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
        
        canvas.requestFocus();
        
        this.setVisible(true);
    }

    private void graphicsLoop() {
        FPS fps = new FPS();
        while (!isStopped) {
            fps.adjust(120);
            canvas.render(world, id, events.rankList);
        }
    }

    private void updateWorldLoop() {
        while (!isStopped) {
            receiver.listenAndLoadWorld(events);
        }
    }
    
    // update rank, your status, 
    private void eventDispatchLoop() {
        while (!isStopped) {
            receiver.listenToTCPEvents(events);
        }
    }
    
    private void controlSendingLoop() {
        FPS fps = new FPS();
        while (!isStopped) {
            fps.adjust(30);
            if(!isDead) {
                ControlData data = mouseControl.getData(canvas.getCenter());
                sender.sendControl(id, data);
            }
        }
    }
    
    private void stop() {
        this.isStopped = true;
    }
}
