import javax.swing.*;
import java.awt.*;


public class Player implements IDrawable {
    private int blockSize;
    int x, y;
    private int subpixelsize;
    private int animationDelay = 3;
    private int animationCounter = 0;
    //TODO: Use rotate!

    private Image imgUp = new ImageIcon("Images/DiggyUpNew1.png").getImage();
    private Image imgDown = new ImageIcon("Images/DiggyDownNew1.png").getImage();
    private Image imgLeft = new ImageIcon("Images/DiggyLeftNew1.png").getImage();
    private Image imgRight = new ImageIcon("Images/DiggyRightNew1.png").getImage();

    private Image imgUp1 = new ImageIcon("Images/DiggyUpNew1.png").getImage();
    private Image imgDown1 = new ImageIcon("Images/DiggyDownNew1.png").getImage();
    private Image imgLeft1 = new ImageIcon("Images/DiggyLeftNew1.png").getImage();
    private Image imgRight1 = new ImageIcon("Images/DiggyRightNew1.png").getImage();

    private Image imgUp2 = new ImageIcon("Images/DiggyUpNew2.png").getImage();
    private Image imgDown2 = new ImageIcon("Images/DiggyDownNew2.png").getImage();
    private Image imgLeft2 = new ImageIcon("Images/DiggyLeftNew2.png").getImage();
    private Image imgRight2 = new ImageIcon("Images/DiggyRightNew2.png").getImage();

    private Image imgUp3 = new ImageIcon("Images/DiggyUpNew3.png").getImage();
    private Image imgDown3 = new ImageIcon("Images/DiggyDownNew3.png").getImage();
    private Image imgLeft3= new ImageIcon("Images/DiggyLeftNew3.png").getImage();
    private Image imgRight3 = new ImageIcon("Images/DiggyRightNew3.png").getImage();

    private Image imgUp4 = new ImageIcon("Images/DiggyUpNew4.png").getImage();
    private Image imgDown4 = new ImageIcon("Images/DiggyDownNew4.png").getImage();
    private Image imgLeft4 = new ImageIcon("Images/DiggyLeftNew4.png").getImage();
    private Image imgRight4 = new ImageIcon("Images/DiggyRightNew4.png").getImage();

    Direction direction = Direction.NONE;
    Direction forDraw = Direction.UP;
    int bias = 0;

    void switchPicture(){
        animationCounter++;
        if (animationCounter==animationDelay) {
            animationCounter = 0;
            if (imgLeft == imgLeft1){
                imgLeft = imgLeft2;
                imgDown = imgDown2;
                imgUp = imgUp2;
                imgRight = imgRight2;
            }else if(imgLeft == imgLeft2) {
                imgLeft = imgLeft3;
                imgDown = imgDown3;
                imgUp = imgUp3;
                imgRight = imgRight3;
            }else if(imgLeft == imgLeft3) {
                imgLeft = imgLeft4;
                imgDown = imgDown4;
                imgUp = imgUp4;
                imgRight = imgRight4;
            }else if(imgLeft == imgLeft4) {
                imgLeft = imgLeft1;
                imgDown = imgDown1;
                imgUp = imgUp1;
                imgRight = imgRight1;
            }

        }

    }

    Player (int size){
        blockSize = size;
        subpixelsize = blockSize/5;
        direction = Direction.NONE;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Coordinate getPosition(int x, int y){
        Coordinate coordinate = new Coordinate(x,y);
        return coordinate;
    }

    int getBias(){
        return bias;
    }

    Direction getDirection(){
        return direction;
    }


    Direction move(Direction tryDirection){
        forDraw = tryDirection;

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
            if (bias < 4){
                bias++;
                forDraw = direction;
                return direction;
            }
            bias = 0;
            //blockChanged = true;
            forDraw = direction;
            direction = Direction.NONE;
            return forDraw;
        }
        return Direction.NONE;
    }

    public void draw(Graphics g){
        g.setColor(Color.blue);
        Image img = imgLeft;
        switch (forDraw){
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
        }

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
