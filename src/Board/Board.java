package Board;

import java.awt.*;
import java.awt.image.*;

import Shape.IShape;
import Shape.ShapeEnum;

public class Board {
    public static final int ZEROX = 3;
    public static final int ZEROY = 3;

    private IShape tiles[][];
    private Image image = null;
    private int rows, columns;
    private GameState state = GameState.INCONCLUSIVE;

    private Board(Image img, IShape[][] tiles, int columns, int rows)
    {
        this.tiles = tiles;
        image = img;
        this.columns = columns;
        this.rows = rows;
    }

    public void addShape(IShape shape, int x, int y)
    {
        tiles[x][y] = shape;
        if(checkColumnWin(shape.getChosen(), x) || checkRowWin(shape.getChosen(), y) || checkDiagonalWin(shape.getChosen(), y, x))
        {
            state = shape.getChosen() == ShapeEnum.CIRCLE ? GameState.CIRCLE_WON : GameState.CROSS_WON;
        }
        else if(isFull())
        {
            state = GameState.DRAW;
        }
    }

    private boolean checkColumnWin(ShapeEnum checking, int column)
    {
        for(int i = 0; i < rows; i++)
        {
            IShape toCheck = tiles[column][i];
            if(toCheck == null)
            {
                return false;
            }
            if(toCheck.getChosen() != checking)
            {
                return false;
            }
        }
        return true;
    }

    private boolean checkRowWin(ShapeEnum checking, int row)
    {
        for(int i = 0; i < rows; i++)
        {
            IShape toCheck = tiles[i][row];
            if(toCheck == null)
            {
                return false;
            }
            if(toCheck.getChosen() != checking)
            {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonalWin(ShapeEnum checking, int row, int column)
    {
        if(rows != columns || (row != column && row != columns - column - 1))
        {
            return false;
        }
        if(row == column)
        {
            for(int i = 0; i < rows; i++)
            {
                IShape toCheck = tiles[i][i];
                if(toCheck == null)
                {
                    return false;
                }
                if(toCheck.getChosen() != checking)
                {
                    return false;
                }
            }
            return true;
        }
        for(int i = 0; i < rows; i++)
        {
            IShape toCheck = tiles[columns-i-1][i];
            if(toCheck == null)
            {
                return false;
            }
            if(toCheck.getChosen() != checking)
            {
                return false;
            }
        }
        return true;
    }

    public void removeShape(int x, int y)
    {
        tiles[x][y] = null;
        state = GameState.INCONCLUSIVE;
    }

    public boolean isSpotEmpty(int x, int y)
    {
        if(x >= columns || y >= rows)
        {
            return false;
        }
        return tiles[x][y] == null;
    }

    public void draw(Graphics2D g)
    {
        g.drawImage(image, 0, 0, null);
        for(int i = 0; i < columns; i++)
        {
            for(int j = 0; j < rows; j++)
            {
                if(tiles[i][j] != null)
                {
                    tiles[i][j].draw(g, i, j);
                }
            }
        }
    }

    public Dimension getDimensions()
    {
        return new Dimension(columns * IShape.TILESIZE, rows * IShape.TILESIZE);
    }

    public boolean isFull() {
        for(int i = 0; i < columns; i++)
        {
            for(int j = 0; j < rows; j++)
            {
                if(tiles[i][j] == null)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public GameState getState() {
        return state;
    }

    public static class Builder
    {
        private Color backgroundColor;
        private static int rows, columns;
        private static Image BoardParts[] = new Image[9];
        public Builder()
        {
            this.rows = 3;
            this.columns = 3;
        }

        public String getFormat()
        {
            return backgroundColor == null ? "Standard" : rows + "x" + columns;
        }

        public void setRowsAndColumns(int rows, int columns)
        {
            this.rows = rows;
            this.columns = columns;
        }

        public void setBackgroundColor(Color color)
        {
            this.backgroundColor = color;

        }

        public Board build()
        {
            BoardPartsSingleton singleton = BoardPartsSingleton.getInstance();
            Image image = new BufferedImage(columns * IShape.TILESIZE, rows * IShape.TILESIZE, BufferedImage.TYPE_BYTE_BINARY);
            Graphics2D graphics2D = (Graphics2D) image.getGraphics();
            graphics2D.drawImage(singleton.getPart(0, 0), 0, 0, null);
            graphics2D.drawImage(singleton.getPart(0, 2), 0* IShape.TILESIZE, (rows-1) * IShape.TILESIZE, null);
            graphics2D.drawImage(singleton.getPart(2, 0), (columns-1) * IShape.TILESIZE, 0 * IShape.TILESIZE, null);
            graphics2D.drawImage(singleton.getPart(2, 2), (columns-1) * IShape.TILESIZE, (rows-1) * IShape.TILESIZE, null);
            IShape tiles[][] = new IShape[columns][rows];
            for(int i = 1; i < columns - 1; i++)
            {
                graphics2D.drawImage(singleton.getPart(1, 0), i* IShape.TILESIZE, 0, null);
                graphics2D.drawImage(singleton.getPart(1, 2), i* IShape.TILESIZE, (rows - 1) * IShape.TILESIZE, null);
                for(int j = 1; j < rows - 1; j++)
                {
                    graphics2D.drawImage(singleton.getPart(1, 1), i* IShape.TILESIZE, j* IShape.TILESIZE, null);
                }
            }
            for(int i = 1; i < rows - 1; i++)
            {
                graphics2D.drawImage(singleton.getPart(0, 1), 0, i* IShape.TILESIZE, null);
                graphics2D.drawImage(singleton.getPart(2, 1), (columns - 1) * IShape.TILESIZE,i* IShape.TILESIZE, null);
            }
            graphics2D.dispose();
            if(backgroundColor != null)
            {
                ImageFilter filter = new RGBImageFilter() {
                    public int markerRGB = Color.white.getRGB();
                    @Override
                    public int filterRGB(int x, int y, int rgb) {
                        if ( rgb == markerRGB ) {
                            // Mark the alpha bits as zero - transparent
                            return backgroundColor.getRGB();
                        }
                        else {
                            // nothing to do
                            return rgb;
                        }
                    }
                };
                ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
                image = Toolkit.getDefaultToolkit().createImage(ip);
            }
            return new Board(image, tiles, columns, rows);
        }
    }
}
