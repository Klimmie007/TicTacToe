package MatchChoicePage;

import Board.Board;
import Game.TicTacToe;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class MatchChoiceView extends JScrollPane {
    public static final int lineHeight = 25;
    private JPanel ChoicePanel = new JPanel();
    public JButton newGameButton = new JButton("Create your own game");

    public JToolBar toolBar = new JToolBar();
    public MatchChoiceView()
    {
        setViewportView(ChoicePanel);
        setPreferredSize(new Dimension(200, 600));
        toolBar.add(newGameButton);
    }
    public void updateView(MatchChoiceModel model)
    {
        ChoicePanel.removeAll();
        List<TicTacToe> games = model.getMatches();
        for(int i = 0; i < games.size(); i++)
        {
            JPanel match = new JPanel();
            match.setLayout(new BoxLayout(match, BoxLayout.PAGE_AXIS));
            match.add(new JLabel(games.get(i).getFormat()));
            match.setPreferredSize(new Dimension(180, lineHeight));
            match.addMouseListener(model.getAdapters().get(i));
            match.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            ChoicePanel.add(match);
        }
        repaint();
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        toolBar.setVisible(aFlag);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        toolBar.setEnabled(enabled);
    }
}
