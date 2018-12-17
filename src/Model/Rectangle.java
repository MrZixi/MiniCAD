package Model;
import Model.Point;
import Model.Shape;
import java.awt.*;

public class Rectangle extends Shape
{
    public Rectangle()
    {

    }
    public Rectangle(Point p1, Point p2)
    {
        super(p1, p2);
    }
    @Override
    public Rectangle clone() throws CloneNotSupportedException
    {
        return (Rectangle)super.clone();
    }
    private int minx()
    {
        return Math.min(p1.x, p2.x);
    }
    private int miny()
    {
        return Math.min(p1.y, p2.y);
    }
    private static void swapX(Point a, Point b)
    {
        Integer temp1x = new Integer(a.x), temp2x = new Integer(b.x);
        a.setX(temp2x.intValue());
        b.setX(temp1x.intValue());
    }
    private static void swapY(Point a, Point b)
    {
        Integer temp1y = new Integer(a.y), temp2y = new Integer(b.y);
        a.setY(temp2y.intValue());
        b.setY(temp1y.intValue());
    }
    @Override
    public void setPointOrder()
    {
        if(p1.x > p2.x)
        {
            swapX(p1, p2);
        }
        if(p1.y > p2.y)
        {
            swapY(p1, p2);
        }
    }
    @Override
    public void draw(Graphics g)
    {
        setThickness(g, thickness);
        g.setColor(color);
        g.drawRect(minx(), miny(), p2.x + p1.x - 2 * minx(), p2.y + p1.y - 2 * miny());
        fill(g);
    }
    @Override
    public void fill(Graphics g)
    {
        g.setColor(fillin);
        g.fillRect(minx(), miny(), p2.x + p1.x - 2 * minx(), p2.y + p1.y - 2 * miny());
        g.setColor(color);
    }
    @Override
    public boolean isSelected(Point p)
    {
        this.setPointOrder();
        if(p.x >= p1.x && p.x <= p2.x && p.y >= p1.y && p.y <= p2.y) {
            return true;
        }
        else
        {
            return false;
        }
    }
}