
package eraser;

import control.ControlData;
import control.MouseControl;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import network.*;
import graphics.GameCanvas;
import java.util.ArrayList;
import world.World;

/**
 *
 * @author dorian
 */
public class Game extends JFrame {

    private GameCanvas canvas;
    private World world;
    private boolean isStopped;
    private TCPSocket tcp;
    private UDPSocket udp;
    private Sender sender;
    private Receiver receiver;
    private MouseControl mouseControl;
    private int id;

    private final int WIDTH = 800, HEIGHT = 600;

    public Game(String name, String ip, int port) {
        super("Eraser");
        this.setup(name, ip, port);
        this.start();
    }

    private void setup(String name, String ip, int port) {
        isStopped = false;
        world = new World();
        
        // Setup network
        tcp = new TCPSocket(ip, port);
        udp = new UDPSocket(ip, port);
        sender = new Sender(tcp, udp);
        receiver = new Receiver(tcp, udp);
        this.id = sender.sendLoginAndGetId(name);

        // Setup canvas
        Dimension canvasSize = new Dimension(WIDTH, HEIGHT);
        canvas = new GameCanvas(canvasSize);
        add(canvas);

        // Setup window location
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowLocationX = (int) ((screenSize.getWidth() - WIDTH) / 2);
        int windowLocationY = (int) ((screenSize.getHeight() - HEIGHT) / 2);

        // Setup window size
        setMinimumSize(new Dimension(640, 480));
        setLocation(windowLocationX, windowLocationY);
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
                eventDispachLoop();
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
            fps.adjust(60);
            canvas.render(world);
        }
    }

    private void updateWorldLoop() {
        FPS fps = new FPS();
        while (!isStopped) {
            fps.adjust(20);
            //System.out.println("UpdateWorldLoop");
            //receiver.listenAndLoadWorld(world);
        }
    }
    
    // update rank, your status, 
    private void eventDispachLoop() {
        FPS fps = new FPS();
        while (!isStopped) {
            fps.adjust(5);
            //System.out.println("EventDispatchLoop");
            //receiver.listenToTCP();
        }
    }
    
    private void controlSendingLoop() {
        FPS fps = new FPS();
        while (!isStopped) {
            fps.adjust(100);
            ControlData data = mouseControl.getData(canvas.getCenter());
            sender.sendControl(id, data);
            System.out.println("[#] control: " + data.x + ", " + data.y);
        }
    }
    
    private void stop() {
        this.isStopped = true;
    }
}
