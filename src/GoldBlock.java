import javax.swing.*;
import java.awt.*;

public class GoldBlock extends GroundBlock {
    GoldBlock(int size){
        blockSize = size;
        subpixelsize = size/5;
    }
    //Image img = new ImageIcon("Diamond.png").getImage();

    Image img2 = new ImageIcon("Images/Diamond.png").getImage();
    @Override
    public boolean isGold(){
        return (healthX[1]==1&&healthX[2]==1&&healthX[3]==1&&healthY[1]==1&&healthY[2]==1&&healthY[3]==1);
    }

    @Override
    public void draw(Graphics g){
        //g.setColor(Color.green);
        g.drawImage(img,y,x,blockSize,blockSize,null);
        g.setColor(Color.black);

        if (healthX[0]==0&&healthY[0]==0)
            g.fillRect(y,x,subpixelsize,subpixelsize);
        if (healthX[0]==0&&healthY[4]==0)
            g.fillRect(y+blockSize-subpixelsize,x,subpixelsize,subpixelsize);
        if (healthX[4]==0&&healthY[0]==0)
            g.fillRect(y,x+blockSize-subpixelsize,subpixelsize,subpixelsize);
        if (healthX[4]==0&&healthY[4]==0)
            g.fillRect(y+blockSize-subpixelsize,x+blockSize-subpixelsize,subpixelsize,subpixelsize);

        for(int i=0;i<5;i++)
            if (healthX[i]==0)
                g.fillRect(y + subpixelsize  , x + subpixelsize * i , subpixelsize*3 , subpixelsize);

        for(int i=0;i<5;i++)
            if (healthY[i]==0)
                g.fillRect(y + subpixelsize * i,x+subpixelsize,subpixelsize,subpixelsize*3);

        if (healthX[1]==1&&healthX[2]==1&&healthX[3]==1&&healthY[1]==1&&healthY[2]==1&&healthY[3]==1){

            g.drawImage(img2,y+subpixelsize,x+subpixelsize,subpixelsize*3,subpixelsize*3,null);
        }
    }
}
