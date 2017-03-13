
import java.awt.*;
//import java.awt.event.WindowListener;


public class TankClient {
    public static void main(String[] args) {
        TankClientFrame t = new TankClientFrame();
    }
}
class TankClientFrame extends Frame{

    TankClientFrame() throws HeadlessException {
        super("TankPlay");
        Frame frame = new Frame();
        frame.setBounds(400,300,800,600);
        frame.setBackground(Color.white);
        frame.setVisible(true);
    }
}
