package Board.Strategy;

import Shape.ShapeEnum;

public class AllowDiagonalWinsStrategy extends AbstractWinStrategy{
    @Override
    public boolean checkIfWon(ShapeEnum[][] tiles, int xLocation, int yLocation, ShapeEnum shapeToCheck) {
        return checkDiagonalWin(tiles, xLocation, yLocation, shapeToCheck) || checkRowWin(tiles, yLocation, shapeToCheck)
                || checkColumnWin(tiles, xLocation, shapeToCheck);
    }

    public AllowDiagonalWinsStrategy(int rows, int columns)
    {
        this.rows = rows;
        this.columns = columns;
    }
}
