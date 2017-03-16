import java.awt.*;
import java.awt.Event.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class Missile {
    int x, y;
    private Tank.Direction dir;
    public static final int XSPEED = 10, YSPEED = 10,WIDTH = 10,HEIGHT = 10;

    TankClientFrame tc = null;


    public Missile(int x, int y, Tank.Direction dir, TankClientFrame tc) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tc = tc;
    }

    public boolean isLive() {
        return Live;
    }


    private boolean Live = true;

    public Missile(int x, int y, Tank.Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }



    public void draw(Graphics g) {
        if (!Live){
            tc.missiles.remove(this);
            return;
        }
        Color c = g.getColor();
        g.setColor(Color.black);
        g.fillRect(x + Tank.TWidth/3 , y +Tank.THeight/3 , WIDTH, HEIGHT);
        g.setColor(c);
        move();
    }




    public void move() {

        switch (dir) {
            case L:
                x -= XSPEED;
                break;
            case LU:
                x -= XSPEED / 1.41;
                y -= YSPEED / 1.41;
                break;
            case U:
                y -= YSPEED;
                break;
            case RU:
                x += XSPEED / 1.41;
                y -= YSPEED / 1.41;
                break;
            case R:
                x += XSPEED;
                break;
            case RD:
                x += XSPEED / 1.41;
                y += YSPEED / 1.41;
                break;
            case D:
                y += YSPEED;
                break;
            case LD:
                x -= XSPEED / 1.41;
                y += YSPEED / 1.41;
                break;
        }
        if(x < 0-Tank.TWidth || y < 0 - Tank.THeight || x > TankClientFrame.TANK_WIDTH || y > TankClientFrame.TANK_HEIGTHT){
            Live = false;
            System.out.println(tc.missiles.size()+"");
            System.out.println(tc.tanks.size()+"");
        }
    }

    public Rectangle getRect(){
        return  new Rectangle(x,y,WIDTH,HEIGHT);
    }

    public boolean hitTank(Tank tank){
        if(this.getRect().intersects(tank.getRect()) && tank.isLive()){
            tank.setLive(false);
            this.Live = false;
            Explode e = new Explode(x,y,tc);
            tc.explodes.add(e);
            return  true;
        }
        return false;
    }

    public void hitTanks(List<Tank> tanks) {
        for(int i=0; i<tanks.size(); i++) {
            hitTank(tanks.get(i));
        }
    }
}




