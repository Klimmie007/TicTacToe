package Board.Strategy;

import Shape.ShapeEnum;

public abstract class AbstractWinStrategy {
    protected int rows, columns;
    public abstract boolean checkIfWon(ShapeEnum[][] tiles, int xLocation, int yLocation, ShapeEnum shapeToCheck);

    protected boolean checkRowWin(ShapeEnum[][] tiles, int yLocation, ShapeEnum shapeToCheck)
    {
        for(int i = 0; i < columns; i++)
        {
            if(tiles[i][yLocation] != shapeToCheck)
            {
                return false;
            }
        }
        return true;
    }

    protected boolean checkColumnWin(ShapeEnum[][] tiles, int xLocation, ShapeEnum shapeToCheck)
    {
        for(int i = 0; i < rows; i++)
        {
            if(tiles[xLocation][i] != shapeToCheck)
            {
                return false;
            }
        }
        return true;
    }

    protected boolean checkDiagonalWin(ShapeEnum[][] tiles, int xLocation, int yLocation, ShapeEnum shapeToCheck)
    {
        if(rows > columns)
        {
            for(int i = 0; i <= rows - columns; i++)
            {
                //flag used to check which (if any) diagonal is confirmed to not be winning. 0 - none, 1 - falling, 2, rising
                int diagonalFlag = 0;
                for(int j = 0; j < columns; j++)
                {
                    if(diagonalFlag == 3)
                    {
                        break;
                    }
                    if(diagonalFlag != 1 && tiles[j][i+j] != shapeToCheck)
                    {
                        diagonalFlag += 1;
                    }
                    if(diagonalFlag < 2 && tiles[j][rows-1-i-j] != shapeToCheck)
                    {
                        diagonalFlag += 2;
                    }
                }
                if(diagonalFlag != 3)
                {
                    return true;
                }
            }
        }
        else
        {
            for(int i = 0; i <= columns - rows; i++)
            {
                //flag used to check which (if any) diagonal is confirmed to not be winning. 0 - none, 1 - falling, 2, rising
                int diagonalFlag = 0;
                for(int j = 0; j < rows; j++)
                {
                    if(diagonalFlag == 3)
                    {
                        break;
                    }
                    if(diagonalFlag != 1 && tiles[j+i][j] != shapeToCheck)
                    {
                        diagonalFlag += 1;
                    }
                    if(diagonalFlag < 2 && tiles[j+i][rows-1-j] != shapeToCheck)
                    {
                        diagonalFlag += 2;
                    }
                }
                if(diagonalFlag != 3)
                {
                    return true;
                }
            }
        }
        return false;
    }

}
