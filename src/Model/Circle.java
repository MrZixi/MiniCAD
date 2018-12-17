package Model;

import Model.Point;
import Model.Shape;

import java.awt.*;


public class Circle extends Shape
{
    public Circle(Point p1, Point p2)
    {
        super(p1, p2);
    }
    @Override
    public void setPointOrder()
    {

    }
    @Override
    public void draw(Graphics g) {
        setThickness(g, thickness);
        g.setColor(color);
        setPointOrder();
        int d = Math.abs(p1.x - p2.x);
        g.drawOval(p1.x, p1.y, d, d);
        g.setColor(color);
    }
    @Override
    public void fill(Graphics g)
    {
        g.setColor(fillin);
        int d = Math.abs(p1.x - p2.x);
        g.fillOval(p1.x, p1.y, d, d);
        g.setColor(color);
    }
    private int distance(Point temp_p1, Point temp_p2)
    {
        return (temp_p1.x - temp_p2.x) * (temp_p1.x - temp_p2.x) + (temp_p1.y - temp_p2.y) * (temp_p1.y - temp_p2.y);
    }
    @Override
    public boolean isSelected(Point p) {
        setPointOrder();
        if(distance(p, new Point(p1.x + Math.abs(p1.x - p2.x) / 2, p1.y + Math.abs(p1.x - p2.x) / 2)) * 4 <= (p1.x - p2.x) * (p1.x - p2.x))
        {
            return true;
        }
        return false;
    }
}
