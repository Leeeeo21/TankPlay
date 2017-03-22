

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class Tank {
	int id = 0;
	
    int x,y;
    TankClientFrame t = null;
    public static final double XSPEED = 5.0,YSPEED = 5.0;
    public static final int TWidth=30,THeight=30;
    private boolean bU =false,bR =false,bD =false,bL =false;

    public boolean isFriend() {
        return friend;
    }

    private boolean friend;

    int[] location = {100,200,300,400,500,600};
    int sum = 0;

    private static Random random = new Random();
    private int step;

    public boolean isLive() {
        return Live;
    }
    public void setLive(boolean live) {
        Live = live;
    }
    private boolean Live = true;

    Dir dirPT = Dir.U;


    //enum Dir{U,RU,R,RD,D,LD,L,LU,STOP}
    Dir dir = Dir.STOP;

    int oldX,oldY;

    public Tank(int x, int y ,boolean friend,Dir dir,TankClientFrame t) {
        this.x = x;
        this.y = y;
        this.t = t;
        this.friend = friend;
        this.dir = dir;
        this.oldX = x;
        this.oldY = y;

    }

    public void draw(Graphics g) {
        if (!Live){
            t.tanks.remove(this);
            return;
        }

        Color c = g.getColor();
        if (friend) g.setColor(Color.blue);
        if (!friend) g.setColor(Color.red);
        g.fillOval(x,y,TWidth,THeight);
        g.drawString("id:"+id, x, y-10);
        g.setColor(Color.black);


        if(dir != Dir.STOP){
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
            case STOP:
            	break;
            default:
            	break;
        }
        g.setColor(c);
        move();
    }

    public void move(){
        this.oldX = x;
        this.oldY = y;
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

        if (!friend){
            Dir[] dirs = Dir.values();
            if(step == 0 ||x ==(TankClientFrame.TANK_WIDTH - TWidth)|| x == 0 ||y == (TankClientFrame.TANK_HEIGTHT - THeight )||y == (THeight-5)){
                step = random.nextInt(40)+5;
                int rn = random.nextInt(dirs.length);
                dir = dirs[rn];
            }
            step --;
            if (random.nextInt(10)>8) {
                t.missiles.add(fire());
            }
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
        locateDir();
    }



    private void locateDir() {
        if(bL && !bU && !bR && !bD) dir = Dir.L;
        else if(bL && bU && !bR && !bD) dir = Dir.LU;
        else if(!bL && bU && !bR && !bD) dir = Dir.U;
        else if(!bL && bU && bR && !bD) dir = Dir.RU;
        else if(!bL && !bU && bR && !bD) dir = Dir.R;
        else if(!bL && !bU && bR && bD) dir = Dir.RD;
        else if(!bL && !bU && !bR && bD) dir = Dir.D;
        else if(bL && !bU && !bR && bD) dir = Dir.LD;
        else if(!bL && !bU && !bR && !bD) dir = Dir.STOP;
    }

    public void keyReleased(KeyEvent e) {
        int keycode = e.getKeyCode();

        switch (keycode) {
            case KeyEvent.VK_1:
                t.myTank.setLive(true);
                x = 400;
                y = 500;
                break;
            case KeyEvent.VK_2:
                if(sum == location.length) sum = 0;
                t.tanks.add(new Tank(location[sum],100,false,Dir.R,t));
                sum++;
                System.out.println(t.tanks.size()+"");
                break;
            case KeyEvent.VK_SPACE:
                if(!Live) break;
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
        locateDir();
    }

    public Missile fire(){


        Missile m = new Missile(x,y,dirPT,friend,t);
            return m;
    }

    public Rectangle getRect(){
        return  new Rectangle(x,y,TWidth,THeight);
    }

    public void tankStop(){
        x = oldX;
        y = oldY;
    }

    public void tankTouch(Tank tank){
        if(this != tank) {
            if(this.Live && tank.Live&& tank.getRect().intersects(this.getRect())){
                if (this.friend == tank.friend){
                    this.tankStop();
                    tank.tankStop();
                }
                else {
                    this.Live = false;
                    tank.Live = false;
                    Explode e1 = new Explode(this.x,this.y,t);
                    Explode e2 = new Explode(tank.x,tank.y,t);
                    t.explodes.add(e1);
                    t.explodes.add(e2);
                }

            }
        }
    }
    public void tanksTouch(List<Tank> tanks){
        for (int i = 0;i < tanks.size();i++){
            this.tankTouch(tanks.get(i));
        }
    }
    /*public boolean tanksTouch(java.util.List<Tank> tanks) {
        for(int i=0; i<tanks.size(); i++) {
            Tank t = tanks.get(i);
            if(this != t) {
                if(this.Live && t.isLive() && this.getRect().intersects(t.getRect())) {
                    this.tankStop();
                    t.tankStop();
                    return true;
                }
            }
        }
        return false;
    }*/

}
