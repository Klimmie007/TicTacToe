package MatchChoicePage;

import Game.TicTacToe;
import MatchSetupPage.MatchSetupModel;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

public class MatchChoiceModel {
    private List<TicTacToe> matches = new ArrayList<>();
    private List<MouseAdapter> adapters = new ArrayList<>();

    public List<TicTacToe> getMatches() {
        return matches;
    }

    public List<MouseAdapter> getAdapters() {
        return adapters;
    }

    public void addMatch(TicTacToe game, MouseAdapter adapter)
    {
        matches.add(game);
        adapters.add(adapter);
    }
}
