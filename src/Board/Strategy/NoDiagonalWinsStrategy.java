package Board.Strategy;

import Shape.ShapeEnum;

public class NoDiagonalWinsStrategy extends AbstractWinStrategy{

    @Override
    public boolean checkIfWon(ShapeEnum[][] tiles, int xLocation, int yLocation, ShapeEnum shapeToCheck) {
        return checkRowWin(tiles, yLocation, shapeToCheck) || checkColumnWin(tiles, xLocation, shapeToCheck);
    }

    public NoDiagonalWinsStrategy(int rows, int columns)
    {
        this.rows = rows;
        this.columns = columns;
    }
}
