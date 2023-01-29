package GamePage;

import Command.ICommand;
import Game.TicTacToe;
import Shape.IShape;
import Board.Board;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private TicTacToe ticTacToe;

    public List<ICommand> commands = new ArrayList<>();
    public int commandPointer;

    public GameModel(TicTacToe game)
    {
        ticTacToe = game;
        commandPointer = 0;
    }

    public Board getBoard() {
        return ticTacToe.getBoard();
    }

    public TicTacToe getGame()
    {
        return ticTacToe;
    }

    public void rematch()
    {
        ticTacToe.rematch();
        commands.clear();
        commandPointer = 0;
    }

    public boolean canUndo()
    {
        return commandPointer > 0;
    }

    public boolean canRedo()
    {
        return commandPointer < commands.size();
    }

    public void executeCommand(ICommand command)
    {
        ticTacToe.executeCommand(command);
    }
}
