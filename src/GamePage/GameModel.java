package GamePage;

import Game.TicTacToe;
import Shape.IShape;
import Board.Board;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameModel {
    private TicTacToe ticTacToe;

    public GameModel(TicTacToe game)
    {
        ticTacToe = game;
    }

    public Board getBoard() {
        return ticTacToe.getBoard();
    }

    public TicTacToe getGame()
    {
        return ticTacToe;
    }
}
