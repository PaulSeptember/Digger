import javax.swing.*;
import java.awt.*;

public class GoldPile extends Enemy  {
    Image img1 = new ImageIcon("Images/GoldPile.png").getImage();
    int deathTimer = 200;

    GoldPile(int blockSize,int x,int y){
        this.blockSize = blockSize;
        this.subpixelsize = blockSize/5;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g){
        g.drawImage(img1,y+subpixelsize,x+subpixelsize,blockSize-subpixelsize*2,blockSize-subpixelsize*2,null);
    }
}
