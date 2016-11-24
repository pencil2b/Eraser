package eraser;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class StartBar implements ActionListener {

    private JFrame frame;
    private JTextField ip;
    private JTextField name;
    private JLabel info;

    public StartBar() {
        frame = new JFrame("Eraser Login");
        setComponent();
        frame.setSize(335, 130);
        frame.setLayout(null);
        frame.repaint();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowLocationX = (int) ((screenSize.getWidth() - 335) / 2);
        int windowLocationY = (int) ((screenSize.getHeight() - 130) / 2);
        frame.setLocation(windowLocationX, windowLocationY);
        frame.setResizable(false);
        frame.setVisible(true);
        
    }

    private void setComponent() {

        JLabel label1 = new JLabel("IP:Port");
        label1.setSize(60, 20);
        label1.setLocation(30, 15);

        JLabel label2 = new JLabel("Name");
        label2.setSize(60, 20);
        label2.setLocation(30, 40);

        ip = new JTextField("127.0.0.1:8888");
        ip.setSize(130, 20);
        ip.setLocation(95, 15);

        name = new JTextField("Tester");
        name.setSize(130, 20);
        name.setLocation(95, 40);

        info = new JLabel();
        info.setSize(300, 20);
        info.setLocation(30, 70);

        JButton connectButton = new JButton("Connect");
        connectButton.setSize(80, 50);
        connectButton.setLocation(230, 13);
        connectButton.addActionListener(this);

        frame.add(label1);
        frame.add(label2);
        frame.add(ip);
        frame.add(name);
        frame.add(info);
        frame.add(connectButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!validate(ip.getText()) || name.getText().equals("")) {
            if (!validate(ip.getText())) {
                info.setText("Error IP Format.");
            }
            if (name.getText().equals("")) {
                info.setText("Name Cannot be Blank.");
            }
            frame.repaint();
            frame.revalidate();
        } else {
            info.setText("Try Connecting...");
            frame.repaint();
            frame.revalidate();
            
            String[] intermediate = ip.getText().split(":");
            String ipStr = intermediate[0];
            int port = Integer.parseInt(intermediate[1]);
            
            // Try to Connect Server
            Game game = new Game(name.getText(), ipStr, port);
            
            info.setText("Success!");
            frame.repaint();
            frame.revalidate();
            
            frame.setVisible(false);
        }
    }

    private static final Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5]):\\d{1,5}$");

    public static boolean validate(final String ip) {
        return PATTERN.matcher(ip).matches();
    }
}
