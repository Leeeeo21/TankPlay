import java.awt.*;
import java.util.List;

public class Missile {
    int x, y;
    private Tank.Direction dir;
    public static final int XSPEED = 10, YSPEED = 10,WIDTH = 10,HEIGHT = 10;
    boolean friend;

    TankClientFrame tc = null;


    public Missile(int x, int y, Tank.Direction dir,boolean friend, TankClientFrame tc) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tc = tc;
        this.friend = friend;
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
        if (friend){
            g.setColor(Color.black);
        }
        if (!friend){
            g.setColor(Color.pink);
        }
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
        }
    }

    public Rectangle getRect(){
        return  new Rectangle(x,y,WIDTH,HEIGHT);
    }

    public boolean hitTank(Tank tank){
        if(this.Live&&this.getRect().intersects(tank.getRect()) && tank.isLive()&&this.friend!=tank.isFriend()){
            tank.setLive(false);
            Live = false;
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




