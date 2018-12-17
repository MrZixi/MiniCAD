package Model;

import java.awt.*;

public class Square extends Rectangle{
    public Square(Point p1, Point p2)
    {
        this.p1 = p1;
        this.p2 = p2;
    }
    @Override
    public void fill(Graphics g) {
        setPointOrder();
        super.fill(g);
    }
    @Override
    public void setPointOrder()
    {
        int dx = p1.x - p2.x, dy = p1.y - p2.y;
        if(Math.abs(dx) > Math.abs(dy))
        {
            if(p2.y > p1.y)
            {
                p2.y = p1.y + Math.abs(dx);
            }
            else
            {
                p2.y = p1.y - Math.abs(dx);
            }
        }
        else
        {
            if(p2.x > p1.x)
            {
                p2.x = p1.x + Math.abs(dy);
            }
            else
            {
                p2.x = p1.x - Math.abs(dy);
            }
        }
        super.setPointOrder();
    }
    @Override
    public void draw(Graphics g) {
        setThickness(g, thickness);
        int dis = Math.max(Math.abs(p1.x - p2.x), Math.abs(p1.y - p2.y));
        g.drawRect(p1.x, p1.y, dis, dis);
    }

    @Override
    public boolean isSelected(Point p) {
        setPointOrder();
        return super.isSelected(p);
    }
}
