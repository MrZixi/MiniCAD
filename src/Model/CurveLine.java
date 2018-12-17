package Model;

import java.awt.*;
import java.util.ArrayList;

public class CurveLine extends Shape {
    public ArrayList<Point> points;

    public CurveLine(Point p)
    {
        points = new ArrayList<Point>();
        points.add(p);
    }
    public CurveLine(Point p1, Point p2)
    {
        points = new ArrayList<Point>();
        points.add(p1);
        points.add(p2);
    }
    @Override
    public void setPointOrder()
    {

    }
    @Override
    public CurveLine clone()
    {
        CurveLine temp = null;
        try{
            temp = (CurveLine) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        temp.points = new ArrayList<Point>();
        for(int i = 0;i < points.size();i++)
        {
            temp.points.add((Point)points.get(i).clone());
        }
        return temp;
    }
    public void addPoint(Point p)
    {
        points.add(p);
    }
    public Point getLastPoint()
    {
        return points.get(points.size() - 1);
    }
    private boolean isThisLineSelected(Point p1, Point p2, Point p)
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
    @Override
    public boolean isSelected(Point p) {
        for (int i = 0;i < points.size() - 1;i++)
        {
            if(isThisLineSelected(points.get(i), points.get(i + 1), p))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(Graphics g) {
        setThickness(g, thickness);
        g.setColor(color);
        for (int i = 0;i < points.size() - 1;i++)
        {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    @Override
    public void changeSize(int flag) {
        int av_x = 0, av_y = 0;
        for(Point p: points)
        {
            av_x += p.x;
            av_y += p.y;
        }
        av_x /= points.size();
        av_y /= points.size();
        Point temp = new Point(av_x, av_y);
        int total;
        double portion_x;
        double portion_y;
        for(Point p: points)
        {
            total = Math.abs(p.x - temp.x) + Math.abs(p.y - temp.y);
            portion_x = (double)Math.abs(p.x - temp.x) / (double)total;
            portion_y = (double)Math.abs(p.y - temp.y) / (double)total;
            if(p.x < temp.x)
            {
                p.x -= (int)(flag * portion_x * 10);
            }
            else if(p.x > temp.x)
            {
                p.x += (int)(flag * portion_x * 10);
            }
            if(p.y > temp.y)
            {
                p.y += (int)(flag * portion_y * 10);
            }
            else if(p.y < temp.y)
            {
                p.y -= (int)(flag * portion_y * 10);
            }
            //同样，防止横线的时候出错
        }
    }
}
