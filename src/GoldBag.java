import javax.swing.*;
import java.awt.*;

public class GoldBag extends Enemy {


    int trembleTimer = 8;
    GoldBag(int blockSize){
        this.blockSize = blockSize;
        this.subpixelsize = blockSize/5;
    }


    @Override
    Direction getDirection() {
        return Direction.DOWN;
    }

    Image img1 = new ImageIcon("Images/MoneyBag1.png").getImage();
    Image img2 = new ImageIcon("Images/MoneyBag2.png").getImage();
    //Image img2 = new ImageIcon("Enemy2.png").getImage();


    @Override
    public void draw(Graphics g){
        if (trembleTimer%2==1)
            g.drawImage(img1,y+subpixelsize,x+subpixelsize,blockSize-subpixelsize*2,blockSize-subpixelsize*2,null);
        else
            g.drawImage(img2,y+subpixelsize,x+subpixelsize,blockSize-subpixelsize*2,blockSize-subpixelsize*2,null);
    }
}
