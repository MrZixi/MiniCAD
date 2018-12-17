package Model;

import java.util.ArrayList;

public class Frame_Model {
    ArrayList<Shape> ShapeManager;
    public Frame_Model(ArrayList<Shape> S)
    {
        ShapeManager = S;
    }
    public void setShapeManager(ArrayList<Shape> S)
    {
        ShapeManager = S;
    }

    public ArrayList<Shape> getShapeManager() {
        return ShapeManager;
    }

    public int size()
    {
        return ShapeManager.size();
    }
    public boolean add(Shape s)
    {
        if(ShapeManager.add(s))
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    public void addTop(Shape s)
    {
        ShapeManager.add(0, s);
    }
    public void remove(Shape s)
    {
        ShapeManager.remove(s);
    }
    public Shape getLast()
    {
        if(ShapeManager.size() >= 1)
        {
            return ShapeManager.get(ShapeManager.size() - 1);
        }
        return null;
    }
    public boolean isEmpty()
    {
        return ShapeManager.isEmpty();
    }
    public void clearAll()
    {
        ShapeManager.clear();
    }
}
