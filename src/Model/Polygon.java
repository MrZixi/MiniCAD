package Model;

import java.awt.*;
import java.util.ArrayList;

public class Polygon extends Shape {
    public ArrayList<Point> points;
    public Polygon(Point p)
    {
        points = new ArrayList<Point>();
        points.add(p);
    }
    public Polygon(Point p1, Point p2)
    {
        points = new ArrayList<Point>();
        points.add(p1);
        points.add(p2);
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
    @Override
    public Polygon clone()
    {
        Polygon temp = null;
        try {
            temp = (Polygon)super.clone();
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
    private int minx()
    {
        int temp_x = 2000;
        for(int i = 0;i < points.size();i++)
        {
            temp_x = Math.min(temp_x, points.get(i).x);
        }
        return temp_x;
    }
    private int miny()
    {
        int temp_y = 2000;
        for(int i = 0;i < points.size();i++)
        {
            temp_y = Math.min(temp_y, points.get(i).y);
        }
        return temp_y;
    }
    private int maxx()
    {
        int temp_x = 0;
        for(int i = 0;i < points.size();i++)
        {
            temp_x = Math.max(temp_x, points.get(i).x);
        }
        return temp_x;
    }
    private int maxy()
    {
        int temp_y = 0;
        for(int i = 0;i < points.size();i++)
        {
            temp_y = Math.max(temp_y, points.get(i).y);
        }
        return temp_y;
    }
    public boolean closed(Point p)
    {
        Point first = points.get(0);
        return (Math.abs(p.x - first.x) * Math.abs(p.y - first.y) + Math.abs(p.y - first.y) * Math.abs(p.y - first.y)) <= 100;
    }
    public void addPoint(Point p)
    {
        points.add(p);
    }
    public Point getLastPoint()
    {
        return points.get(points.size() - 1);
    }
    public int getSize(){
        return points.size();
    }
    public void close()
    {
        points.remove(points.size() - 1);
    }
    @Override
    public boolean isSelected(Point p) {
        java.awt.Polygon temp = new java.awt.Polygon();
        for(int i = 0;i < points.size();i++)
        {
            temp.addPoint(points.get(i).x, points.get(i).y);
        }
        return temp.contains(p.x, p.y);
       /* if(p.x >= minx() && p.x <= maxx() && p.y >= miny() && p.y <= maxy())
        {
            return true;
        }
        else
        {
            return false;
        }*/
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
        Point last = points.get(points.size() - 1);
        Point first = points.get(0);
        g.drawLine(last.x, last.y, first.x, first.y);
    }
    @Override
    public void setPointOrder()
    {

    }
    @Override
    public void fill(Graphics g)
    {
        g.setColor(fillin);
        int[] points_x = new int[points.size()];
        int[] points_y = new int[points.size()];
        for(int i = 0;i < points.size();i++)
        {
            points_x[i] = points.get(i).x;
            points_y[i] = points.get(i).y;
        }
        g.fillPolygon(points_x, points_y, points.size());
        g.setColor(color);
    }


}
