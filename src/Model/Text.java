package Model;
import java.awt.*;

public class Text extends Shape
{
    private String content;
    public Text(Point p1, Point p2, String content)
    {
        super(p1, p2);
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Text clone()
    {
        Text temp = null;
        try {
            temp = (Text)super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        //deep copy?
        temp.content = content;
        return temp;
    }
    @Override
    public boolean isSelected(Point p)
    {
        if(p.x >= p1.x && p.x <= p2.x && p.y >= p1.y && p.y <= p2.y) {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void setPointOrder()
    {
        if(p1.x > p2.x)
        {
            int temp = p1.x;
            p1 = new Point(p2.x, p1.y);
            p2.x = temp;
        }
        if(p1.y > p2.y)
        {
            int temp = p1.y;
            p1 = new Point(p1.x, p2.y);
            p2.y = temp;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.setFont(new Font("楷体", Font.PLAIN, Math.abs(p1.y-p2.y)));
        this.setPointOrder();
        g.drawString(content, p1.x, p2.y);
    }
}