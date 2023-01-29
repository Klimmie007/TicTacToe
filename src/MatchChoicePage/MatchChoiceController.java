package MatchChoicePage;

import Game.TicTacToe;
import GamePage.GameController;
import GamePage.GameModel;
import GamePage.GameView;
import MainWindow.MainWindowSingleton;
import MatchSetupPage.MatchSetupController;
import MatchSetupPage.MatchSetupModel;
import MatchSetupPage.MatchSetupView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MatchChoiceController{
    private MatchChoiceModel model;
    private MatchChoiceView view;

    public class customMouseAdapter extends MouseAdapter
    {
        private int index;
        public customMouseAdapter(int idx)
        {
            super();
            index = idx;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            JFrame frame = MainWindowSingleton.getInstance();
            GameView gameView = new GameView();
            GameModel gameModel = new GameModel(model.getMatches().get(index));
            GameController gameController = new GameController(gameModel, gameView);
            view.setEnabled(false);
            view.setVisible(false);
            frame.add(gameView);
            frame.add(gameView.getBar(), BorderLayout.PAGE_START);
            frame.pack();
            MainWindowSingleton.release();
        }
    }

    public MatchChoiceController(MatchChoiceView view, MatchChoiceModel model)
    {
        this.view = view;
        this.model = model;
        model.addMatch(new TicTacToe(new MatchSetupModel()), new customMouseAdapter(0));
        view.updateView(model);
        view.newGameButton.addActionListener(getCreateAction());
    }

    public ActionListener getCreateAction()
    {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = MainWindowSingleton.getInstance();
                MatchSetupView matchSetupView = new MatchSetupView();
                MatchSetupModel matchSetupModel = new MatchSetupModel();
                MatchSetupController matchSetupController = new MatchSetupController(matchSetupModel, matchSetupView);
                view.setEnabled(false);
                view.setVisible(false);
                frame.add(matchSetupView);
                frame.pack();
                MainWindowSingleton.release();
            }
        };
    }
}
