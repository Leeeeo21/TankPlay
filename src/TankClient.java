
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
//import java.awt.event.WindowListener;


public class TankClient {
    public static void main(String[] args) {
        TankClientFrame t = new TankClientFrame();
    }
}
class TankClientFrame extends Frame{


    private int x = 50,y = 50;

    private Image offScreenImage = null;

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

        Thread pt = new Thread(new PaintThread());
        pt.start();
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
        g.fillOval(x,y,30,30);

        g.setColor(c);

        y += 1;

    }


    @Override
    public void update(Graphics g) {
       if(offScreenImage == null){
           offScreenImage = this.createImage(800,600);
       }
       Graphics gOffScreen = offScreenImage.getGraphics();
       Color c = gOffScreen.getColor();
       gOffScreen.setColor(Color.GREEN);
       gOffScreen.fillRect(0,0,800,600);
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
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


