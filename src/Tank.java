import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {
    int x,y;
    TankClientFrame t = null;
    public static final double XSPEED = 5.0,YSPEED = 5.0;
    public static final int TWidth=30,THeight=30;
    private boolean bU =false,bR =false,bD =false,bL =false;
    private boolean friend;

    public boolean isLive() {
        return Live;
    }
    public void setLive(boolean live) {
        Live = live;
    }
    private boolean Live = true;

    Direction dirPT = Direction.U;


    enum Direction{U,RU,R,RD,D,LD,L,LU,STOP}
    private Direction dir = Direction.STOP;

    public Tank(int x, int y ,boolean friend) {
        this.x = x;
        this.y = y;
        this.friend = friend;
    }
    public Tank(int x, int y ,boolean friend,TankClientFrame t) {
        this.x = x;
        this.y = y;
        this.t = t;
        this.friend = friend;
    }

    public void draw(Graphics g) {
        if (!Live)return;       //如果坦克死亡就不画该Tank


        Color c = g.getColor();
        if (friend) g.setColor(Color.blue);
        if (!friend) g.setColor(Color.red);
        g.fillOval(x,y,TWidth,THeight);
        g.setColor(Color.black);


        if(dir != Direction.STOP){
            dirPT = dir;
        }
        switch (dirPT){
            case L:
                g.drawLine(x+Tank.TWidth/2,y+Tank.THeight/2,x,y+Tank.THeight/2);
                break;
            case LU:
                g.drawLine(x+Tank.TWidth/2,y+Tank.THeight/2,x,y);
                break;
            case U:
                g.drawLine(x+Tank.TWidth/2,y+Tank.THeight/2,x + Tank.TWidth/2,y);
                break;
            case RU:
                g.drawLine(x+Tank.TWidth/2,y+Tank.THeight/2,x + Tank.TWidth,y);
                break;
            case R:
                g.drawLine(x+Tank.TWidth/2,y+Tank.THeight/2,x + Tank.TWidth,y+Tank.THeight/2);
                break;
            case RD:
                g.drawLine(x+Tank.TWidth/2,y+Tank.THeight/2,x + Tank.TWidth,y+Tank.THeight);
                break;
            case D:
                g.drawLine(x+Tank.TWidth/2,y+Tank.THeight/2,x + Tank.TWidth/2,y+Tank.THeight);
                break;
            case LD:
                g.drawLine(x+Tank.TWidth/2,y+Tank.THeight/2,x,y+Tank.THeight);
                break;
        }
        g.setColor(c);
        move();
    }

    public void move(){
        switch (dir){
            case L:
                x -= XSPEED;
                break;
            case LU:
                x -= XSPEED/1.41;
                y -= YSPEED/1.41;
                break;
            case U:
                y -= YSPEED;
                break;
            case RU:
                x += XSPEED/1.41;
                y -= YSPEED/1.41;
                break;
            case R:
                x += XSPEED;
                break;
            case RD:
                x += XSPEED/1.41;
                y += YSPEED/1.41;
                break;
            case D:
                y += YSPEED;
                break;
            case LD:
                x -= XSPEED/1.41;
                y += YSPEED/1.41;
                break;
            case STOP:
                break;
        }
        if(x>=(TankClientFrame.TANK_WIDTH - TWidth)){
            x = TankClientFrame.TANK_WIDTH - TWidth;
        }
        else if(x<=0){
            x = 0 ;
        }
        if(y >= (TankClientFrame.TANK_HEIGTHT - THeight)){
            y = TankClientFrame.TANK_HEIGTHT - THeight;
        }
        else if (y <= (THeight-5)){
            y = THeight-5;
        }
    }


    public void keyPressed (KeyEvent e) {
        int keycode = e.getKeyCode();
        switch (keycode) {

            case KeyEvent.VK_LEFT :
                bL = true;
                break;
            case KeyEvent.VK_UP :
                bU = true;
                break;
            case KeyEvent.VK_RIGHT :
                bR = true;
                break;
            case KeyEvent.VK_DOWN :
                bD = true;
                break;
        }
        locateDirection();
    }



    private void locateDirection() {
        if(bL && !bU && !bR && !bD) dir = Direction.L;
        else if(bL && bU && !bR && !bD) dir = Direction.LU;
        else if(!bL && bU && !bR && !bD) dir = Direction.U;
        else if(!bL && bU && bR && !bD) dir = Direction.RU;
        else if(!bL && !bU && bR && !bD) dir = Direction.R;
        else if(!bL && !bU && bR && bD) dir = Direction.RD;
        else if(!bL && !bU && !bR && bD) dir = Direction.D;
        else if(bL && !bU && !bR && bD) dir = Direction.LD;
        else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
    }

    public void keyReleased(KeyEvent e) {
        int keycode = e.getKeyCode();
        switch (keycode) {
            case KeyEvent.VK_SPACE:
                    t.missiles.add(fire());
                break;
            case KeyEvent.VK_LEFT :
                bL = false;
                break;
            case KeyEvent.VK_UP :
                bU = false;
                break;
            case KeyEvent.VK_RIGHT :
                bR = false;
                break;
            case KeyEvent.VK_DOWN :
                bD = false;
                break;
        }
        locateDirection();
    }

    public Missile fire(){
        Missile m = new Missile(x,y,dirPT,this.t);
            return m;
    }

    public Rectangle getRect(){
        return  new Rectangle(x,y,TWidth,THeight);
    }
}
