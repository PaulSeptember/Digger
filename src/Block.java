import java.awt.*;

public abstract class Block implements IDrawable  {

    public abstract void kill();

    public abstract void setPosition(int x , int y);

    public abstract void setHealthX(int x , int value);

    public abstract void setHealthY(int y , int value);

    public abstract int getHealthX(int x);

    public abstract int getHealthY(int y);

    public abstract boolean isGold();

    public abstract boolean isEmpty();
}
