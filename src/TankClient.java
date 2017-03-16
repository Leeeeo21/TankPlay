
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ArrayList;

public class TankClient {
    public static void main(String[] args) {
        TankClientFrame t = new TankClientFrame();
    }
}
class TankClientFrame extends Frame{

    public static final int TANK_WIDTH = 800,TANK_HEIGTHT = 600;
    public static final Color BACK_GOUND_COLOR = new Color(203, 225, 193);

    Tank myTank = new Tank(400,500,true,this);//难点！！！！！！
//    Tank enemyTank = new Tank(400,100,false,this);
//    Explode e = new Explode(this,enemyTank);


    List<Missile>missiles = new ArrayList<Missile>();
    List<Explode>explodes = new ArrayList<Explode>();
    List<Tank>tanks = new ArrayList<Tank>();


    private Image offScreenImage = null;

    TankClientFrame() throws HeadlessException {
        super("TankPlay");
        this.setBounds(400,300,TANK_WIDTH,TANK_HEIGTHT);
        this.setBackground(BACK_GOUND_COLOR);
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
        g.drawString("missle count: "+missiles.size(),10,50);
        g.drawString("tanks count: "+tanks.size(),10,60);
        g.drawString("explodes count: "+explodes.size(),10,70);

        myTank.draw(g);
        //enemyTank.draw(g);
        for(int i=0;i<missiles.size();i++){
            Missile m = missiles.get(i);
            m.hitTanks(tanks);
            m.draw(g);
        }
        for(int i=0;i<explodes.size();i++){
            Explode e = explodes.get(i);
            e.draw(g);
        }
        for(int i=0;i<tanks.size();i++){
            Tank t = tanks.get(i);
            t.draw(g);
        }
    }


    @Override
    public void update(Graphics g) {
       if(offScreenImage == null){
           offScreenImage = this.createImage(TANK_WIDTH,TANK_HEIGTHT);
       }
       Graphics gOffScreen = offScreenImage.getGraphics();
       Color c = gOffScreen.getColor();
       gOffScreen.setColor(BACK_GOUND_COLOR);
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


        @Override
        public void keyReleased(KeyEvent e) {
             myTank.keyReleased(e);
        }


    }

}


