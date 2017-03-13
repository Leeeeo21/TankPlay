
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//import java.awt.event.WindowListener;


public class TankClient {
    public static void main(String[] args) {
        TankClientFrame t = new TankClientFrame();
    }
}
class TankClientFrame extends Frame{

    TankClientFrame() throws HeadlessException {
        super("TankPlay");
        this.setBounds(400,300,800,600);
        this.setBackground(Color.green);
        this.setVisible(true);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                System.exit(0);
            }
        });
    }

    /**
     * {@inheritDoc}
     *
     * @param g
     * @since 1.7
     */
    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(50,50,30,30);
        g.setColor(c);
    }
}


