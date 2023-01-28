package Command;

import Board.Board;
import Game.TicTacToe;

public interface ICommand {
    void execute(Board board);
}
