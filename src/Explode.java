/**
 * 坦克爆炸类
 * Created by Leo on 2017/3/16.
 */
import java.awt.*;

public class Explode {
    int x,y;
    boolean Live = true;
    int[] diameter = {5,10,15,20,25,30,35,27,20,10,5,2};
    int step = 0;
    Color color = new Color(255, 61, 54);
    TankClientFrame tc = null;

    Explode(int x,int y,TankClientFrame tc){
        this.x = x;
        this.y = y;
        this.tc = tc;
    }
    public void draw(Graphics g){
        if(!this.Live) {
            tc.explodes.remove(this);
            return;
        }
        if(step == diameter.length){
            step = 0;
            Live = false;
            return;
        }

        Color c = g.getColor();
        g.setColor(color);
        g.fillOval(x,y,diameter[step],diameter[step]);
        step ++ ;

        g.setColor(c);
    }
}
