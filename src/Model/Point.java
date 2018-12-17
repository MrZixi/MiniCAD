package Model;

import java.io.Serializable;

public class Point implements Serializable, Cloneable
{
    public int x, y;
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    @Override
    public Object clone()
    {
        Point temp = null;
        try {
          temp = (Point) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return temp;
    }
    public int getX()
    {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
