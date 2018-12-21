package Model;

import javafx.scene.shape.Ellipse;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Oval extends Shape {
    public Oval(Point p1, Point p2)
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
    public void draw(Graphics g) {
        setThickness(g, thickness);
        g.setColor(color);
        g.drawOval(minx(), miny(), p2.x + p1.x - 2 * minx(), p2.y + p1.y - 2 * miny());
        fill(g);
    }
    @Override
    public void fill(Graphics g)
    {
        g.setColor(fillin);
        g.fillOval(minx(), miny(), p2.x + p1.x - 2 * minx(), p2.y + p1.y - 2 * miny());
        g.setColor(color);
    }
    @Override
    public boolean isSelected(Point p)
    {
        this.setPointOrder();
        Integer minx = minx(), miny = miny(), width = p2.x + p1.x - 2 * minx(), height = p2.y + p1.y - 2 * miny();
        Ellipse2D temp = new Ellipse2D.Double(minx.doubleValue(), miny.doubleValue(), width.doubleValue(), height.doubleValue());
        Integer px = p.x, py = p.y;
        return temp.contains(px.doubleValue(), py.doubleValue());
        /*if(p.x >= p1.x && p.x <= p2.x && p.y >= p1.y && p.y <= p2.y) {
            return true;
        }
        else
        {
            return false;
        }*/
    }
}
