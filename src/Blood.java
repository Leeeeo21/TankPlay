import java.awt.*;

/**
 * Created by Leo on 2017/3/18.
 */
public class Blood {
    int x = Tank.random.nextInt(TankClientFrame.TANK_WIDTH);
    int y = Tank.random.nextInt(TankClientFrame.TANK_HEIGTHT);
    public final int B_WIDTH = 10;
    public final int B_HEIGHT = 10;
    private boolean Live = true;
    Tank tank = null;
    public final int lifePlus = 20;

    public Blood(Tank tank) {
        this.tank = tank;
    }

    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.blue);
        g.fillRect(x,y,B_WIDTH,B_HEIGHT);
        g.setColor(c);
    }


    public Rectangle getRect(){
        return  new Rectangle(x,y,B_WIDTH,B_HEIGHT);
    }

    public boolean beEaten(){
        if (tank.isLive() && tank.isFriend() && tank.getLife() < tank.fullLife && tank.getRect().intersects(this.getRect())){
            tank.setLife(tank.getLife()+lifePlus);
            if (tank.getLife() > tank.fullLife){
                tank.setLife(tank.fullLife);
            }
            reAppear();
            //this.Live = false;
            return true;
        }else return false;

    }

    public void reAppear(){
        x = Tank.random.nextInt(TankClientFrame.TANK_WIDTH);
        y = Tank.random.nextInt(TankClientFrame.TANK_HEIGTHT);
    }

}
