package eraser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class EndBar implements ActionListener {

    private JFrame frame;
    private String ip;
    private String name;
    private String rank;
    private String time;

    public EndBar(String ip, String name, String rank, String time) {
        // Setting 
        this.ip = ip;
        this.name = name;
        this.rank = rank;
        this.time = time;

        // Make Frame 
        frame = new JFrame("Game Over");
        setComponent();
        frame.setLocation(300, 300);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        frame.setSize(350, 155);
        frame.setLayout(null);
        frame.repaint();
        frame.setVisible(true);
    }

    private void setComponent() {
        // Title 
        JLabel label1 = new JLabel(" Rank ");
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setSize(60, 20);
        label1.setLocation(40, 20);

        JLabel label2 = new JLabel(" Name ");
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setSize(60, 20);
        label2.setLocation(135, 20);

        JLabel label3 = new JLabel(" Servial Time ");
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setSize(100, 20);
        label3.setLocation(220, 20);

        JLabel rankLabel = new JLabel(this.rank);
        rankLabel.setHorizontalAlignment(JLabel.CENTER);
        rankLabel.setText(this.rank);
        rankLabel.setSize(60, 20);
        rankLabel.setLocation(40, 50);

        JLabel nameLabel = new JLabel(this.name);
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setText(this.name);
        nameLabel.setSize(60, 20);
        nameLabel.setLocation(135, 50);

        JLabel timeLabel = new JLabel(this.time);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setText(this.time);
        timeLabel.setSize(100, 20);
        timeLabel.setLocation(220, 50);

        JButton restart = new JButton("Restart");
        restart.setSize(80, 30);
        restart.setLocation(30, 80);
        restart.addActionListener(this);

        JButton rankList = new JButton("Rank");
        rankList.setSize(80, 30);
        rankList.setLocation(126, 80);
        rankList.addActionListener(this);

        JButton exit = new JButton("Exit");
        exit.setSize(80, 30);
        exit.setLocation(230, 80);
        exit.addActionListener(this);

        frame.add(label1);
        frame.add(label2);
        frame.add(label3);
        frame.add(nameLabel);
        frame.add(rankLabel);
        frame.add(timeLabel);
        frame.add(restart);
        frame.add(rankList);
        frame.add(exit);

        frame.repaint();
        frame.revalidate();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Restart":
                // Try to Connect Server
                break;
            case "Rank_List":
                // Show PlayerList
                break;
            case "Exit":
                System.exit(0);
                break;

        }
    }
}
