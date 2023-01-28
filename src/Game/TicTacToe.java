package Game;

import Board.Board;
import Command.ICommand;
import Board.GameState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TicTacToe {
    private Board board;
    private MatchState state = MatchState.WAITING_FOR_PLAYERS;
    private List<ICommand> commands = new ArrayList<>();

    public Board getBoard()
    {
        return board;
    }

    public TicTacToe()
    {
        Board.Builder builder = new Board.Builder();
        builder.setBackgroundColor(Color.cyan);
        board = builder.build();
    }

    public void executeCommand(ICommand command)
    {
        command.execute(board);
        if(board.getState() != GameState.INCONCLUSIVE)
        {
            state = MatchState.FINISHED;
        }
    }
    public MatchState getState()
    {
        return state;
    }
}
