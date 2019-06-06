import javax.swing.*;
import java.awt.*;


public class GroundBlock extends Block implements IDrawable {
    int x , y , blockSize;
    int frame = 1;
    int subpixelsize;

    protected int[] healthX = {1,1,1,1,1};
    protected int[] healthY = {1,1,1,1,1};

    GroundBlock(int size){
        blockSize = size;
        subpixelsize = size/5;
        setTexture();
    }
    GroundBlock(){
        blockSize = 20;
        subpixelsize = 20/5;
        setTexture();
    }
    int ran = (int)(Math.random()*(5)+1);

    Image img = new ImageIcon("block1.png").getImage();

    private void setTexture(){
        int ran = (int)(Math.random()*(4)+1);
        switch (ran){
            case 1:
                img = new ImageIcon("Images/block1.png").getImage();break;
            case 2:
                img = new ImageIcon("Images/block2.png").getImage();break;
            case 3:
                img = new ImageIcon("Images/block3.png").getImage();break;
            case 4:
                img = new ImageIcon("Images/block4.png").getImage();break;
            case 5:
                img = new ImageIcon("Images/block5.png").getImage();break;
        }
    }
    @Override
    public boolean isGold(){
        return false;
    }
    @Override
    public int getHealthX(int x){
        return healthX[x];
    }

    @Override
    public int getHealthY(int y){
        return healthY[y];
    }

    @Override
    public void setHealthX(int x, int value) {
        healthX[x] = value;
    }

    @Override
    public void setHealthY(int y, int value) {
        healthY[y] = value;
    }

    @Override
    public void setPosition(int x , int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean isEmpty(){
        return (healthX[1]!=1&&healthX[2]!=1&&healthX[3]!=1)||(healthY[1]!=1&&healthY[2]!=1&&healthY[3]!=1);
    }

    @Override
    public void kill(){
        for(int i=0;i<5;i++)
            healthX[i]=0;
        for(int i=0;i<5;i++)
            healthY[i]=0;
    }

    public void draw(Graphics g){
        //g.setColor(Color.green);

        //g.fillRect(y,x,blockSize,blockSize);
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


    }
}
