import java.awt.*;

public class Missile {
    int x,y;
        private void draw(Graphics g){
            Color c = g.getColor();
            g.setColor(Color.black);
            g.fillRect(x,y,5,10);
            g.setColor(c);
            move();
        }

        private void move(){

        }

}
