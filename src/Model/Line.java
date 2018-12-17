package Model;
import Model.Shape;
import Model.Point;

import java.awt.*;


public class Line extends Shape
{
    public Line(Point p1, Point p2)
    {
        super(p1, p2);
    }
    private int minx()
    {
        return Math.min(p1.x, p2.x);
    }
    private int miny()
    {
        return Math.min(p1.y, p2.y);
    }
    private int maxx()
    {
        return Math.max(p1.x, p2.x);
    }
    private int maxy()
    {
        return Math.max(p1.y, p2.y);
    }
    @Override
    public void draw(Graphics g)
    {
        setThickness(g, thickness);
        g.setColor(color);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
    }
    @Override
    public  boolean isSelected(Point p)
    {
        if(p.x >= minx() && p.x <= maxx() && p.y >= miny() && p.y <= maxy())
        {
            double temp1 = Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
            double temp2 = (p2.y - p1.y) * p.x + (p1.x - p2.x) * p.y + (p2.x * p1.y - p2.y * p1.x);
            if(Math.abs(temp2 / temp1) <= 5)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
}