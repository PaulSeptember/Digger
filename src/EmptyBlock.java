import java.awt.*;

public class EmptyBlock implements IDrawable {
    int x , y , pixelSize;

    EmptyBlock(int size){
        pixelSize = size;
    }

    public void setPosition(int x , int y){
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g){
        g.setColor(Color.black);
        g.fillRect(y,x,pixelSize,pixelSize);
    }
}
