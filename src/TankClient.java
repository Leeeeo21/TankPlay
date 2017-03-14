
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
    private int x = 50,y = 50;

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
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x,y,30,30);

        g.setColor(c);


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
            int keycode = e.getKeyCode();
            switch(keycode){
                case KeyEvent.VK_UP:
                    y -= 5;
                    break;
                case KeyEvent.VK_DOWN:
                    y += 5;
                    break;
                case KeyEvent.VK_LEFT:
                    x -= 5;
                    break;
                case KeyEvent.VK_RIGHT:
                    x += 5;
                    break;
            }
            /*if(keycode == KeyEvent.VK_UP){
                System.out.println("you pressed UP");
                y -= 5;
            }
            if(keycode == KeyEvent.VK_DOWN){
                System.out.println("you pressed UP");
                y += 5;
            }
            if(keycode == KeyEvent.VK_LEFT){
                System.out.println("you pressed UP");
                x -= 5;
            }
            if(keycode == KeyEvent.VK_RIGHT){
                System.out.println("you pressed UP");
                x +=5;
            }*/

        }
    }

}


