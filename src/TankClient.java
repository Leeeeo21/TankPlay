
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
        this.setBounds(400,300,800,600);
        this.setBackground(Color.white);
        this.setVisible(true);
        this.setResizable(false);

    }
}


