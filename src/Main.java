import MainWindow.MainWindowSingleton;
import MatchChoicePage.MatchChoiceController;
import MatchChoicePage.MatchChoiceModel;
import MatchChoicePage.MatchChoiceView;
import MatchSetupPage.MatchSetupController;
import MatchSetupPage.MatchSetupModel;
import MatchSetupPage.MatchSetupView;
import SignInPage.SignInController;
import SignInPage.SignInModel;
import SignInPage.SignInView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        MainWindowSingleton frame = MainWindowSingleton.getInstance();
        if(frame == null)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.release();

        MatchSetupView view = new MatchSetupView();
        MatchSetupModel model = new MatchSetupModel();
        MatchSetupController controller = new MatchSetupController(model, view);



    }
}