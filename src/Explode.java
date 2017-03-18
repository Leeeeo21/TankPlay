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
    private TankClientFrame tc = null;



    private static Toolkit tk = Toolkit.getDefaultToolkit();      //Toolkit类
    private static Image[] imgs = { //把Image设成静态，以免每次生成对象都重载图片
            tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))
    };//class（反射），通过Toolkit将classpath下的图片导入程序

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
        if(step == imgs.length){
            step = 0;
            Live = false;
            return;
        }
        g.drawImage(imgs[step],x,y,null);
        step ++ ;

    }
}
