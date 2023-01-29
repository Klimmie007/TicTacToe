package Command;

import Board.Board;
import Game.TicTacToe;
import Shape.IShape;
import Shape.Shape;
import Shape.ShapeAffineDecorator;
import Shape.ShapeEnum;

public class MoveCommand implements ICommand {
    private IShape shape;
    private int x, y;
    protected int getX()
    {
        return x;
    }

    protected int getY()
    {
        return y;
    }
    public MoveCommand(ShapeEnum shape, int x, int y)
    {
        this.x = x;
        this.y = y;
        this.shape = new ShapeAffineDecorator(new Shape(shape));
    }
    @Override
    public void execute(Board board)
    {
        board.addShape(shape, x, y);
    }
}
