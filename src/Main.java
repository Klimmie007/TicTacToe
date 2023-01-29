import MainWindow.MainWindowSingleton;
import MatchSetupPage.MatchSetupController;
import MatchSetupPage.MatchSetupModel;
import MatchSetupPage.MatchSetupView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = MainWindowSingleton.getInstance();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        MatchSetupView view1 = new MatchSetupView();
        MatchSetupModel model1 = new MatchSetupModel();
        MatchSetupController controller1 = new MatchSetupController(model1, view1);
        frame.add(view1);

        frame.pack();
        frame.setVisible(true);
    }
}