package Controller;

import Model.Frame_Model;
import View.Frame_View;

import javax.swing.text.View;


public class Frame_Controller {
    public static Frame_Model ModelManager;
    public static Frame_View ViewManager;
    public static Frame_Listener listener = new Frame_Listener();
    public static String state = "idle";
    public Frame_Controller(Frame_Model M, Frame_View V)
    {
        ModelManager = M;
        ViewManager = V;
    }
    public static void updateView()
    {
        ViewManager.rePaint_all(ModelManager.getShapeManager());
    }
}
