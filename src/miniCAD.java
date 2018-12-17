import Controller.Frame_Controller;
import Model.Frame_Model;
import Model.Shape;
import View.Frame_View;

import java.util.ArrayList;

public class miniCAD {
    public static void main(String[] argv)
    {
        Frame_Model ModelManager = new Frame_Model(new ArrayList<Shape>());
        Frame_View ViewManager = new Frame_View();
        Frame_Controller controller = new Frame_Controller(ModelManager, ViewManager);
    }
}
