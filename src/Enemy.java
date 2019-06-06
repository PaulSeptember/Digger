import javax.swing.*;
import java.awt.*;
import java.io.Console;

public class Enemy implements IDrawable {
    Direction forDraw = Direction.UP;
    Direction direction = Direction.NONE;
    int bias;
    int x,y,blockSize;
    int subpixelsize;
    int animationCounter = 0;
    int animationDelay = 4;
    int pictureNumber = 1;

    Image img1 = new ImageIcon("Images/Enemy1.png").getImage();
    Image img2 = new ImageIcon("Images/Enemy2.png").getImage();
    Image img3 = new ImageIcon("Images/Enemy3.png").getImage();

    Image img = img1;

    void switchPicture() {
        animationCounter++;
        if (animationCounter == animationDelay) {
            animationCounter=0;
            pictureNumber++;
            pictureNumber%=4;
            switch (pictureNumber) {
                case 0:
                    img = img1;
                    break;
                case 3:
                case 1:
                    img = img2;
                    break;
                case 2:
                    img = img3;
                    break;

            }
        }
    }

    Direction getDirection(){
        return direction;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    Enemy(int size){
        blockSize = size;
        subpixelsize=size/5;
    }

    Enemy(){
        blockSize = 20;
        subpixelsize = 20/5;
    }

    boolean blockChanged = false;

    boolean isBlockChanged(){
        return blockChanged;
    }


    int getBias(){
        return bias;
    }

    Direction move(Direction tryDirection){
        forDraw = tryDirection;
        blockChanged = false;
        if (direction == Direction.NONE) {
            direction = tryDirection;
            bias = 1;
            return direction;
        }
        if (tryDirection == direction && bias < 4){
            bias++;
            return direction;
        }
        if (tryDirection == direction && bias == 4){
            bias = 0;
            blockChanged = true;
            direction = Direction.NONE;
            return tryDirection;
        }
        if (direction == Direction.opposite(tryDirection)){
            bias--;
            if (bias == 0)
                direction = Direction.NONE;
            return direction;
        }
        if (direction != Direction.opposite(tryDirection)){
            if (bias > 0){
                bias--;
                forDraw = Direction.opposite(direction);
                return Direction.opposite(tryDirection);
            }
            //bias = 0;
            forDraw = direction;
            direction = Direction.NONE;
            return forDraw;
        }
        return Direction.NONE;
    }

    public void draw(Graphics g){
        g.setColor(Color.blue);
        //Image img = imgLeft;

        /*switch (forDraw){
            case UP:
                img = imgUp;
                break;
            case DOWN:
                img = imgDown;
                break;
            case RIGHT:
                img = imgRight;
                break;
            case LEFT:
                img = imgLeft;
                break;
        }*/

        switch (direction) {
            case NONE:
                g.drawImage(img,y+subpixelsize,x+subpixelsize,blockSize-subpixelsize*2,blockSize-subpixelsize*2,null);
                break;
            case UP:
                g.drawImage(img,y+subpixelsize,x+subpixelsize-subpixelsize*bias,blockSize-subpixelsize*2,blockSize-subpixelsize*2,null);
                break;
            case DOWN:
                g.drawImage(img,y+subpixelsize,x+subpixelsize+subpixelsize*bias,blockSize-subpixelsize*2,blockSize-subpixelsize*2,null);
                break;
            case LEFT:
                g.drawImage(img,y+subpixelsize-subpixelsize*bias,x+subpixelsize,blockSize-subpixelsize*2,blockSize-subpixelsize*2,null);
                break;
            case RIGHT:
                g.drawImage(img,y+subpixelsize+subpixelsize*bias,x+subpixelsize,blockSize-subpixelsize*2,blockSize-subpixelsize*2,null);
                break;
        }


    }
}
