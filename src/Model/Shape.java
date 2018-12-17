package Model;
import java.awt.*;
import java.io.Serializable;
public abstract class Shape implements Serializable, Cloneable{
    public Point p1, p2;
    public Color color;
    public Color fillin = Color.WHITE;
    public int thickness;
    public static Color to_be_fillin;
    Shape()
    {
    }
    Shape(Point p1, Point p2)
    {
        this.p1 = new Point(p1.x, p1.y);
        this.p2 = new Point(p2.x, p2.y);
        thickness = 2;
        color = Color.BLACK;
    }
    @Override
    public Shape clone() throws CloneNotSupportedException
    {
        Shape temp = (Shape)super.clone();
        if(p1 == null)
        {
            temp.p1 = null;
        }
        else
        {
            temp.p1 = (Point)p1.clone();
        }
        if(p2 == null)
        {
            temp.p2 = null;
        }
        else
        {
            temp.p2 = (Point)p2.clone();
        }
        return temp;
    }
    public void setPointOrder()
    {
        if(p1.x > p2.x)
        {
            int p1x = p1.x, p1y = p1.y;
            p1 = new Point(p2.x, p2.y);
            p2 = new Point(p1x, p1y);
        }
    }
    public void setThickness(Graphics g, int new_thickness) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(new_thickness));//直接设置粗细即可
    }
    public int getThickness()
    {
        return thickness;
    }
    public void changeSize(int flag)//改变大小绝大多数图形都是改变两个固定点的位置，少数不能这么做的图像就override此函数
    {
        int total = Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
        //这里x和y改变的比例必须用一个量来统一，否则数值计算误差会使得图形变形
        double portion_x = (double)Math.abs(p1.x - p2.x) / (double)total;
        double portion_y = (double)Math.abs(p1.y - p2.y) / (double)total;
        setPointOrder();
        //防止竖线的时候出错
        if(p1.x < p2.x)
        {
            p1.x -= (int)(flag * portion_x * 10);
            p2.x += (int)(flag * portion_x * 10);
        }

        if(p1.y > p2.y)
        {
            p1.y += (int)(flag * portion_y * 10);
            p2.y -= (int)(flag * portion_y * 10);
        }
        else if(p1.y < p2.y)
        {
            p1.y -= (int)(flag * portion_y * 10);
            p2.y += (int)(flag * portion_y * 10);
        }
        //同样，防止横线的时候出错
    }
    abstract public void draw(Graphics g);
    public void fill(Graphics g)
    {

    }
    abstract public boolean isSelected(Point p);
}