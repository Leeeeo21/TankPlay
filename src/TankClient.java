
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient {
    public static void main(String[] args) {
        TankClientFrame t = new TankClientFrame();
    }
}
class TankClientFrame extends Frame{

    public static final int TANK_WIDTH = 800,TANK_HEIGTHT = 600;

    Tank myTank = new Tank(50,50);

    private Image offScreenImage = null;

    TankClientFrame() throws HeadlessException {
        super("TankPlay");
        this.setBounds(400,300,TANK_WIDTH,TANK_HEIGTHT);
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

        Thread pt = new Thread(new PaintThread());
        pt.start();
        this.addKeyListener(new KeyMonitor());
    }


    @Override
    public void paint(Graphics g) {
        myTank.draw(g);
    }


    @Override
    public void update(Graphics g) {
       if(offScreenImage == null){
           offScreenImage = this.createImage(TANK_WIDTH,TANK_HEIGTHT);
       }
       Graphics gOffScreen = offScreenImage.getGraphics();
       Color c = gOffScreen.getColor();
       gOffScreen.setColor(Color.GREEN);
       gOffScreen.fillRect(0,0,TANK_WIDTH,TANK_HEIGTHT);
       gOffScreen.setColor(c);
       paint(gOffScreen);
       g.drawImage(offScreenImage,0,0,null);

    }

    private class PaintThread implements Runnable{

        @Override
        public void run() {
            while(true){
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class KeyMonitor extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }
    }

}


