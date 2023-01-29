import MainWindow.MainWindowSingleton;
import MatchChoicePage.MatchChoiceController;
import MatchChoicePage.MatchChoiceModel;
import MatchChoicePage.MatchChoiceView;
import MatchSetupPage.MatchSetupController;
import MatchSetupPage.MatchSetupModel;
import MatchSetupPage.MatchSetupView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = MainWindowSingleton.getInstance();
        if(frame == null)
        {
            throw new RuntimeException("frame is used by something else!");
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        MatchSetupView view1 = new MatchSetupView();
//        MatchSetupModel model1 = new MatchSetupModel();
//        MatchSetupController controller1 = new MatchSetupController(model1, view1);
//        frame.add(view1);

        MatchChoiceView view = new MatchChoiceView();
        MatchChoiceModel model = new MatchChoiceModel();
        MatchChoiceController controller = new MatchChoiceController(view, model);
        frame.add(view);
        frame.add(view.toolBar, BorderLayout.PAGE_END);

        frame.pack();
        frame.setVisible(true);
        MainWindowSingleton.release();
    }
}