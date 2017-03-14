import java.awt.*;
import java.awt.Event.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Missile {
    int x, y;
    //static boolean fire = false;
    private Tank.Direction dir;
    public static final int XSPEED = 10, YSPEED = 10;

    public Missile(int x, int y, Tank.Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.black);
        g.fillRect(x, y, 10, 10);
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
    }

}




