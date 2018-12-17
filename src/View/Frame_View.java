package View;

import Controller.Frame_Controller;

import Model.Shape;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Frame_View extends JFrame {
    public static Color[] colors = {Color.RED, Color.GREEN, Color.BLACK, Color.WHITE,
            Color.BLUE, Color.CYAN, Color.GRAY, Color.MAGENTA, Color.PINK, Color.YELLOW, Color.ORANGE, Color.LIGHT_GRAY};
    public static String[] color_names = {"RED", "GREEN", "BLACK", "WHITE", "BLUE", "CYAN", "GRAY", "MAGENTA", "PINK", "YELLOW", "ORANGE", "LIGHT_GRAY"};
    private Graphics g;
    public static String HelpText = "运行程序时请将输入法切换为英文，否则可能存在无法响应键盘按键的情况\n" +
            "\n" +
            "=\"+\" 变大\n" +
            "- 变下\n" +
            ".\">\" 加粗\n" +
            ",\"<\" 变细\n" +
            "Delete 删除\n" +
            "B 将图形挪到底层\n" +
            "T 将图形挪到顶层\n" +
            "Ctrl+C 复制图形\n" +
            "Ctrl+V 黏贴图形\n" +
            "绘制多边形时请以回到原点形成封闭图形结束绘制\n"+
            "绘制多段折线时请单击右键结束绘制\n" +
            "在绘制椭圆状态下按住Shift可以绘制正圆\n" +
            "在绘制矩形状态下按住Shift可以绘制正方形\n" +
            "保存CAD记录文件时，后缀名请设置为.CAD";
    public static String AboutText = "Copyright ZJU mix1601 \n 3160104050   Richard Li";
    private void setSimpleToolsJButton(JButton temp)
    {
        temp.setBorderPainted(false);
        temp.setOpaque(false);
        temp.setMargin(new Insets(0, 0, 0, 0));
        temp.setHorizontalTextPosition(AbstractButton.CENTER);
        temp.setVerticalTextPosition(AbstractButton.BOTTOM);
        temp.addActionListener(Frame_Controller.listener);
        temp.addKeyListener(Frame_Controller.listener);
    }
    public Frame_View()
    {
        super("miniCAD");
        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);

        setFocusable(true);
        setLocation(100, 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel.addMouseListener(Frame_Controller.listener);
        panel.addMouseMotionListener(Frame_Controller.listener);
        panel.addKeyListener(Frame_Controller.listener);

        JMenuBar menu = new JMenuBar();
        JMenu file_menu = new JMenu("File");
        JMenuItem save_option = new JMenuItem("Save");
        save_option.addActionListener(Frame_Controller.listener);
        JMenuItem load_option = new JMenuItem("Load");
        load_option.addActionListener(Frame_Controller.listener);
        file_menu.add(save_option);
        file_menu.add(load_option);

        JMenu others = new JMenu("Others");
        JMenuItem help_option = new JMenuItem("Help");
        help_option.addActionListener(Frame_Controller.listener);
        JMenuItem about_option = new JMenuItem("About");
        about_option.addActionListener(Frame_Controller.listener);
        others.add(help_option);
        others.add(about_option);
        JMenu edit_menu = new JMenu("Edit");
        JMenuItem undo_option = new JMenuItem("Undo");
        undo_option.addActionListener(Frame_Controller.listener);
        JMenuItem redo_option = new JMenuItem("Redo");
        redo_option.addActionListener(Frame_Controller.listener);
        JMenuItem clear = new JMenuItem("Clear");
        clear.addActionListener(Frame_Controller.listener);
        edit_menu.add(clear);
        edit_menu.add(undo_option);
        edit_menu.add(redo_option);

        menu.add(file_menu);
        menu.add(edit_menu);
        menu.add(others);
        setJMenuBar(menu);


        JPanel tools = new JPanel(new GridLayout(4, 2,10,10));
        JButton Line = new JButton("line", new ImageIcon(".\\miniCADicon\\line.png"));
        setSimpleToolsJButton(Line);
        JButton Rectangle = new JButton("rectangle", new ImageIcon(".\\miniCADicon\\square.png"));
        setSimpleToolsJButton(Rectangle);
        JButton Oval = new JButton("oval", new ImageIcon(".\\miniCADicon\\oval.png"));
        setSimpleToolsJButton(Oval);
        JButton Text = new JButton("text", new ImageIcon(".\\miniCADicon\\text.png"));
        setSimpleToolsJButton(Text);
        JButton CurveLine = new JButton("curveline", new ImageIcon(".\\miniCADicon\\curveline.png"));
        setSimpleToolsJButton(CurveLine);
        JButton Polygon = new JButton("polygon", new ImageIcon(".\\miniCADicon\\polygon.png"));
        setSimpleToolsJButton(Polygon);
        tools.add(Line);
        tools.add(Rectangle);
        tools.add(Polygon);
        tools.add(Oval);
        tools.add(CurveLine);
        tools.add(Text);

        JPanel color_panel = new JPanel(new GridLayout(4, 4, 5, 5));
        for(int i = 0;i < color_names.length;i++)
        {
            JButton temp = new JButton();
            temp.setBackground(colors[i]);
            temp.setFocusPainted(false);
            //去掉鼠标移上去的focus效果
            temp.setBorderPainted(false);
            temp.setActionCommand(color_names[i]);
            temp.addActionListener(Frame_Controller.listener);
            temp.addKeyListener(Frame_Controller.listener);
            temp.addMouseListener(Frame_Controller.listener);
            color_panel.add(temp);
        }
        JPanel operator = new JPanel(new GridLayout(2, 1));
        JPanel draw_or_fill = new JPanel(new FlowLayout());
        JButton drawbtn = new JButton("draw", new ImageIcon(".\\miniCADicon\\draw.png"));
        setSimpleToolsJButton(drawbtn);
        JButton fillbtn = new JButton("fill", new ImageIcon(".\\miniCADicon\\fill.png"));
        setSimpleToolsJButton(fillbtn);
        JButton pointer = new JButton("selector", new ImageIcon(".\\miniCADicon\\selector.png"));
        setSimpleToolsJButton(pointer);
        pointer.setActionCommand("select");
        draw_or_fill.add(drawbtn);
        draw_or_fill.add(fillbtn);
        operator.add(pointer);
        operator.add(draw_or_fill);
        tools.add(operator);
        tools.add(color_panel);
        tools.setPreferredSize(new Dimension(300, 0));
        add(tools, BorderLayout.EAST);
        setBackground(Color.WHITE);
        setSize(1440, 960);
        setVisible(true);
        g = panel.getGraphics();
    }
    public void rePaint_all(ArrayList<Shape> Shape_manager)
    {
        Component[] temp = getRootPane().getContentPane().getComponents();
        //先跟据窗口大小清空画布
        g.clearRect(0, 0, temp[0].getWidth(), temp[0].getHeight());
        //然后重新画
        for(Shape shape:Shape_manager)
        {
            shape.draw(g);
            shape.fill(g);
        }
    }
    public void fill(Shape shape)
    {
        shape.fill(g);
    }
}
