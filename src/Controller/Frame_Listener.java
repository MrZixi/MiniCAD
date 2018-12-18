package Controller;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import Model.*;
import Model.Point;
import Model.Polygon;
import Model.Rectangle;
import Model.Shape;
import View.Frame_View;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
@SuppressWarnings("unchecked")
public class Frame_Listener implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

    private Shape copyShape;
    private Shape seletedShape;
    private Point now_p1, now_p2;
    private Point origin_p1, origin_p2;
    private ArrayList<Point> origin_points = new ArrayList<Point>();
    private String text;
    private Color temp_chosen;
    private Color fillin = Color.WHITE;
    private Color drawon = Color.BLACK;
    private static boolean drawing = false;
    private static boolean filling = false;
    @Override
    public void actionPerformed(ActionEvent event)
    {
        String cmd = event.getActionCommand();
        switch (cmd)
        {
            case "select":
                Frame_Controller.state = "select";
                drawing = false;
                filling = false;
                seletedShape = null;
                now_p1 = null;
                now_p2 = null;
                origin_p1 = null;
                origin_p2 = null;
                break;
            case "curveline":
                    Frame_Controller.state = "curveline";
                break;
            case "polygon":
                    Frame_Controller.state = "polygon";
                break;
            case "draw":
                drawing = true;
                filling = false;
                break;
            case "fill":
                drawing = false;
                filling = true;
                fillin = temp_chosen;
                break;
            case "line":
                Frame_Controller.state = "line";

                break;
            case "rectangle":
                Frame_Controller.state = "rectangle";
                break;
            case "circle":
                Frame_Controller.state = "circle";
                break;
            case "text":
                Frame_Controller.state = "text";
                text = JOptionPane.showInputDialog("Please input your text:");
                break;
            case "oval":
                Frame_Controller.state = "oval";
                break;
            case "RED": case "GREEN": case "BLACK": case "WHITE": case "BLUE": case "CYAN": case "GRAY": case "MAGENTA": case "PINK": case "YELLOW": case"ORANGE":
                temp_chosen = Frame_View.colors[Arrays.asList(Frame_View.color_names).indexOf(cmd)];
                if(drawing)
                {
                   drawon = temp_chosen;
                }
                else if(filling)
                {
                    fillin = temp_chosen;
                }
                Frame_Controller.updateView();
                break;
            case "Load":
                Load_File();
                break;
            case "Save":
                Save_File();
                break;
            case "Help":
                ShowHelpText();
                break;
            case "About":
                ShowAboutText();
                break;
            case "Redo":
                break;
            case "Undo":
                break;
            case "Clear":
                Frame_Controller.ModelManager.clearAll();
                Frame_Controller.updateView();
                break;
            default:
                Frame_Controller.state = "idle";
                break;
        }
    }
    private static void ShowHelpText()
    {
        JOptionPane.showMessageDialog(null, Frame_View.HelpText, "Help", JOptionPane.YES_OPTION | JOptionPane.INFORMATION_MESSAGE);
    }
    private void ShowAboutText()
    {
        JOptionPane.showMessageDialog(null, Frame_View.AboutText, "About", JOptionPane.YES_OPTION | JOptionPane.INFORMATION_MESSAGE);
    }
    private Shape which_selected(Point p)
    {
        Shape temp;
        for(int i = Frame_Controller.ModelManager.size() - 1;i >= 0;i--)
        {
            temp = Frame_Controller.ModelManager.getShapeManager().get(i);
            if(temp.isSelected(p))
            {
                return temp;
            }
        }
        return null;
    }
    @Override
    public void mouseDragged(MouseEvent e) {//鼠标拖动过程中就要有效果
            Perform_action(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int vk = e.getKeyCode();
        if(seletedShape != null)
        {
            switch (vk)
            {
                case KeyEvent.VK_DELETE:  //delete键
                    Frame_Controller.ModelManager.getShapeManager().remove(seletedShape);
                    break;
                case KeyEvent.VK_EQUALS:  //+号
                    seletedShape.changeSize(1);
                    break;
                case KeyEvent.VK_MINUS:     //-号
                    seletedShape.changeSize(-1);
                    break;
                case KeyEvent.VK_COMMA:     //,号
                    seletedShape.thickness+=1;
                    Frame_Controller.updateView();
                    break;
                case KeyEvent.VK_PERIOD:        //.号
                    if(seletedShape.thickness > 1)
                    {
                        seletedShape.thickness--;
                        Frame_Controller.updateView();
                    }
                    break;
                case KeyEvent.VK_C:
                    if(e.isControlDown())
                    {
                        if(seletedShape != null)
                        {
                            try {
                                copyShape = seletedShape.clone();
                            }
                            catch (CloneNotSupportedException ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }
                    break;
                case KeyEvent.VK_V:
                    if(e.isControlDown())
                    {
                        if(copyShape != null)
                        {
                            try {
                                Frame_Controller.ModelManager.add(copyShape.clone());
                            }
                            catch (CloneNotSupportedException ex)
                            {
                                ex.printStackTrace();
                            }
                            Frame_Controller.updateView();
                        }
                    }
                    break;
                case KeyEvent.VK_B:
                    Frame_Controller.ModelManager.remove(seletedShape);
                    Frame_Controller.ModelManager.addTop(seletedShape);
                    break;
                case KeyEvent.VK_T:
                    Frame_Controller.ModelManager.remove(seletedShape);
                    Frame_Controller.ModelManager.add(seletedShape);
                    break;
                default:
                    break;
            }
        }
        else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V)
        {
            Frame_Controller.ModelManager.add(copyShape);
            Frame_Controller.updateView();
        }
        else if(Frame_Controller.state == "oval" && drawing)
        {
            Frame_Controller.state = "circle";
        }
        else if(Frame_Controller.state == "rectangle" && drawing)
        {
            Frame_Controller.state = "square";
        }
        Frame_Controller.updateView();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }
    private void Save_File()
    {
        String filename = JOptionPane.showInputDialog(null, "Please input the file name\n", ".CAD");
        if(filename == null)
        {
            return;
        }
        String path = null;
        JFileChooser f = new JFileChooser();
        f.setDialogTitle("Please choose the saving directory");
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int option = f.showOpenDialog(null);
        if(option == JFileChooser.APPROVE_OPTION)
        {
            path = f.getSelectedFile().getAbsolutePath();
        }
        if(filename == null || filename.equals("") || filename.equals(".CAD"))
        {
            JOptionPane.showMessageDialog(null, "Saving miniCAD record file error!", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if(!filename.endsWith(".CAD"))
        {
            filename.concat(".CAD");
        }
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path + "\\" + filename));
            out.writeObject(Frame_Controller.ModelManager.getShapeManager());
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Saving miniCAD record file error!", "错误", JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(null, "Saving miniCAD record file successfully!", "成功", JOptionPane.INFORMATION_MESSAGE);
    }
    private void Load_File()
    {
        String path = null;
        JFileChooser f = new JFileChooser();
        FileNameExtensionFilter record_file = new FileNameExtensionFilter("miniCAD记录文件(*.CAD)", "CAD");
        f.setDialogTitle("Please choose the miniCAD record file");
        f.setFileSelectionMode(JFileChooser.FILES_ONLY);
        f.setFileFilter(record_file);

        int option = f.showOpenDialog(null);
        if(option == JFileChooser.APPROVE_OPTION)
        {
            path = f.getSelectedFile().getAbsolutePath();
        }
        if(path == null)
        {
            return;
        }
        if(!path.endsWith("CAD"))
        {

            if(!path.endsWith("CAD"))
            {
                JOptionPane.showMessageDialog(null,"Please choose a miniCAD record file!", "错误",JOptionPane.ERROR_MESSAGE);
            }
            return;
        }
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
            ArrayList<Shape> temp = (ArrayList<Shape>)(in.readObject());
            Frame_Controller.ModelManager.setShapeManager(temp);
            Frame_Controller.updateView();
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Load miniCAD record file error!", "错误",JOptionPane.ERROR_MESSAGE);

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3)
        {
            seletedShape = which_selected(new Point(e.getX(), e.getY()));
            if(Frame_Controller.state.equals("curveline1") && drawing)
            {
                CurveLine curve = (CurveLine)Frame_Controller.ModelManager.getLast();
                Point temp = new Point(e.getX(), e.getY());
                curve.addPoint(temp);
                Frame_Controller.state = "curveline";
                Frame_Controller.updateView();
                return;
            }
            else if(seletedShape != null && Frame_Controller.state.equals("select"))
            {
                if(seletedShape.getClass().equals(Text.class))
                {
                    String temp = JOptionPane.showInputDialog("Please input your text:");
                    if(temp != null && !temp.equals(""))
                    {
                        Text temptext = (Text)seletedShape;
                        temptext.setContent(temp);
                        Frame_Controller.updateView();
                    }
                }
                    if(temp_chosen != null)
                    {
                        seletedShape.color = temp_chosen;
                        Frame_Controller.updateView();
                    }
            }
        }
        else
        {
            if(Frame_Controller.state.equals("select"))
            {
                seletedShape = which_selected(new Point(e.getX(), e.getY()));
            }
            if(Frame_Controller.state.equals("curveline1") && drawing)
            {
                CurveLine curve = (CurveLine)Frame_Controller.ModelManager.getLast();
                Point temp = new Point(e.getX(), e.getY());
                curve.addPoint(temp);
                Frame_Controller.updateView();
                return;
            }
            else if(Frame_Controller.state.equals("polygon1") && drawing)
            {
                Polygon polygon = (Polygon) Frame_Controller.ModelManager.getLast();
                Point temp = new Point(e.getX(), e.getY());
                if(!polygon.closed(temp))
                {
                    polygon.addPoint(temp);
                }
                else
                {
                    polygon.close();
                    Frame_Controller.state = "polygon";
                }
                Frame_Controller.updateView();
                return;
            }
            else {
                if(filling)
                {
                    seletedShape = which_selected(new Point(e.getX(), e.getY()));
                    if(seletedShape != null)
                    {
                        seletedShape.fillin = fillin;
                        Frame_Controller.ViewManager.fill(seletedShape);
                    }
                }
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        now_p1 = new Point(e.getX(), e.getY());
        now_p2 = new Point(e.getX(), e.getY());
        if(e.getButton() == MouseEvent.BUTTON3)
        {
            if(filling)
            {
                seletedShape = which_selected(now_p1);
                if(seletedShape != null)
                {
                    seletedShape.fillin = fillin;
                    Frame_Controller.ViewManager.fill(seletedShape);
                }
            }
           return;
        }
        else
        {
            if(drawing)
            {
                switch (Frame_Controller.state)
                {
                    case "line":
                        Frame_Controller.ModelManager.add(new Line(now_p1, now_p2));
                        seletedShape = null;
                        break;
                    case "rectangle":
                        Frame_Controller.ModelManager.add(new Rectangle(now_p1, now_p2));
                        seletedShape = null;
                        break;
                    case "circle":
                        Frame_Controller.ModelManager.add(new Circle(now_p1, now_p2));
                        seletedShape = null;
                        break;
                    case "text":
                        if(text != null && !text.equals(""))
                        {
                            Frame_Controller.ModelManager.add(new Text(now_p1, now_p2, text));
                        }
                        seletedShape = null;
                        break;
                    case "oval":
                        Frame_Controller.ModelManager.add(new Oval(now_p1, now_p2));
                        seletedShape = null;
                        break;
                    case "curveline":
                        Frame_Controller.ModelManager.add(new CurveLine(now_p1, now_p2));
                        Frame_Controller.state = "curveline1";
                        seletedShape = null;
                        break;
                    case "polygon":
                        Frame_Controller.ModelManager.add(new Polygon(now_p1, now_p2));
                        Frame_Controller.state = "polygon1";
                        seletedShape = null;
                        break;
                    case "square":
                        Frame_Controller.ModelManager.add(new Square(now_p1, now_p2));
                        seletedShape = null;
                        break;
                    default:
                        seletedShape = null;
                        break;
                }
                Shape temp = Frame_Controller.ModelManager.getLast();
                if(temp != null)
                {
                    temp.color = drawon;
                }
            }
            else if(Frame_Controller.state.equals("select"))
            {
                seletedShape = which_selected(now_p1);
                if(seletedShape != null)
                {
                    if(seletedShape.getClass() == CurveLine.class)
                    {
                        CurveLine temp = (CurveLine) seletedShape;
                        origin_points.clear();
                        for(int i = 0;i < temp.points.size();i++)
                        {
                            origin_points.add(new Point(temp.points.get(i).x, temp.points.get(i).y));
                        }
                    }
                    else if(seletedShape.getClass() == Polygon.class)
                    {
                        Polygon temp = (Polygon) seletedShape;
                        origin_points.clear();
                        for(int i = 0;i < temp.points.size();i++)
                        {
                            origin_points.add(new Point(temp.points.get(i).x, temp.points.get(i).y));
                        }
                    }
                    else
                    {
                        origin_p1 = new Point(seletedShape.p1.x, seletedShape.p1.y);
                        origin_p2 = new Point(seletedShape.p2.x, seletedShape.p2.y);
                    }
                }
            }
        }

    }
    private void Perform_action(MouseEvent e)
    {
        if(Frame_Controller.state.equals("idle"))
        {
            return;
        }
        else if(Frame_Controller.state.equals("polygon1"))
        {
            Polygon polygon = (Polygon)Frame_Controller.ModelManager.getLast();
            Point temp = polygon.getLastPoint();
            temp.x = e.getX();
            temp.y = e.getY();
            Frame_Controller.updateView();
        }
        else if(Frame_Controller.state.equals("curveline1"))
        {
            CurveLine curve = (CurveLine)Frame_Controller.ModelManager.getLast();
            Point temp = curve.getLastPoint();
            temp.x = e.getX();
            temp.y = e.getY();
            Frame_Controller.updateView();
        }
        else if(!Frame_Controller.state.equals("select") && drawing)
        {
            Shape temp = Frame_Controller.ModelManager.getLast();
            //画上去的图形的最后一个点确定了
            if(temp != null)
            {
                temp.p2.x = e.getX();
                temp.p2.y = e.getY();
            }
            Frame_Controller.updateView();
        }
        else
        {
            if (seletedShape != null)
            {
                if(seletedShape.getClass().equals(CurveLine.class))
                {
                    CurveLine temp = (CurveLine)seletedShape;
                    int dx = e.getX() - now_p1.x, dy = e.getY() - now_p1.y;
                    for(int i = 0;i < temp.points.size();i++)
                    {
                        temp.points.get(i).x = origin_points.get(i).x + dx;
                        temp.points.get(i).y = origin_points.get(i).y + dy;
                    }
                }
                else if(seletedShape.getClass().equals(Polygon.class))
                {
                    Polygon temp = (Polygon)seletedShape;
                    int dx = e.getX() - now_p1.x, dy = e.getY() - now_p1.y;
                    for(int i = 0;i < temp.points.size();i++)
                    {
                        temp.points.get(i).x = origin_points.get(i).x + dx;
                        temp.points.get(i).y = origin_points.get(i).y + dy;
                    }
                }
                else
                {
                    int dx = e.getX() - now_p1.x, dy = e.getY() - now_p1.y;
                    seletedShape.setPointOrder();
                    if(seletedShape.p1 != null && origin_p1 != null && origin_p2 != null)
                    {
                        seletedShape.p1.x = origin_p1.x + dx;
                        seletedShape.p1.y = origin_p1.y + dy;
                    }
                   if(seletedShape.p2 != null && origin_p1 != null && origin_p2 != null) {
                       seletedShape.p2.x = origin_p2.x + dx;
                       seletedShape.p2.y = origin_p2.y + dy;
                   }
                }
                Frame_Controller.updateView();
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {//鼠标释放时确定图形最终情况
        Perform_action(e);
        origin_p1 = null;
        origin_p2 = null;
        if(seletedShape != null){
            seletedShape.setPointOrder();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if((Frame_Controller.state.equals("curveline1") || Frame_Controller.state.equals("polygon1") )&& drawing)
        {
            Perform_action(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
       int vk = e.getKeyCode();
       if(vk == KeyEvent.VK_SHIFT)
       {
           if(Frame_Controller.state.equals("circle") && drawing)
           {
               Frame_Controller.state = "oval";
           }
           if(Frame_Controller.state.equals("rectangle") && drawing)
           {
               Frame_Controller.state = "square";
           }
       }
    }
}
