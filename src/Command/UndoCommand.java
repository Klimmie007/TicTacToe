package Command;

import Board.Board;

public class UndoCommand implements ICommand {
    int x, y;
    public UndoCommand(MoveCommand command)
    {
        x = command.getX();
        y = command.getY();
    }

    @Override
    public void execute(Board board) {
        board.removeShape(x, y);
    }
}
