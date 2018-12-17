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
        //不方便将多边形的尺寸改变
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
        if(p.x >= minx() && p.x <= maxx() && p.y >= miny() && p.y <= maxy())
        {
            return true;
        }
        else
        {
            return false;
        }
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
