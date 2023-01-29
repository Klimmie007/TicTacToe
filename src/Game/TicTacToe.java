package Game;

import Board.Board;
import Command.ICommand;
import Board.GameState;
import MatchSetupPage.MatchSetupModel;

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
    private Board.Builder builder;

    public TicTacToe(MatchSetupModel model)
    {
        builder = new Board.Builder();
        if(!model.standard)
        {
            builder.setRowsAndColumns(model.rows, model.columns);
            builder.setBackgroundColor(model.color);
        }
        board = builder.build();
    }

    public void executeCommand(ICommand command)
    {
        command.execute(board);
        if(board.getState() != GameState.INCONCLUSIVE)
        {
            state = MatchState.FINISHED;
        }
        else
        {
            state = MatchState.ONGOING;
        }
    }
    public MatchState getState()
    {
        return state;
    }

    public void rematch()
    {
        board = builder.build();
        state = MatchState.ONGOING;
    }
}
