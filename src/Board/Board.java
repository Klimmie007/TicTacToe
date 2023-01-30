package Board;

import java.awt.*;
import java.awt.image.*;
import java.util.HashMap;

import Board.Strategy.AbstractWinStrategy;
import Board.Strategy.AllowDiagonalWinsStrategy;
import Board.Strategy.NoDiagonalWinsStrategy;
import Shape.IShape;
import Shape.ShapeEnum;
import Shape.ShapeAffineDecorator;
import Shape.Shape;

public class Board {
    public static final int ZEROX = 3;
    public static final int ZEROY = 3;

    private ShapeEnum tiles[][];
    private final IShape Circle = new ShapeAffineDecorator(new Shape(ShapeEnum.CIRCLE));
    private final IShape Cross = new ShapeAffineDecorator(new Shape(ShapeEnum.CROSS));
    private Image image;
    private int rows, columns;
    private GameState state = GameState.INCONCLUSIVE;

    private AbstractWinStrategy strategy;

    private Board(Image img, ShapeEnum[][] tiles, int columns, int rows, AbstractWinStrategy strategy)
    {
        this.tiles = tiles;
        image = img;
        this.columns = columns;
        this.rows = rows;
        this.strategy = strategy;
    }

    public void addShape(ShapeEnum shape, int x, int y)
    {
        tiles[x][y] = shape;
        if(strategy.checkIfWon(tiles, x, y, shape))
        {
            state = shape == ShapeEnum.CIRCLE ? GameState.CIRCLE_WON : GameState.CROSS_WON;
        }
        else if(isFull())
        {
            state = GameState.DRAW;
        }
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

    public void draw(Graphics2D g)
    {
        g.drawImage(image, 0, 0, null);
        for(int i = 0; i < columns; i++)
        {
            for(int j = 0; j < rows; j++)
            {
                if(tiles[i][j] == null)
                {
                    continue;
                }
                switch(tiles[i][j])
                {
                    case CROSS -> {
                        Cross.draw(g, i, j);
                        break;
                    }
                    case CIRCLE -> {
                        Circle.draw(g, i, j);
                        break;
                    }
                }
            }
        }
    }

    public Dimension getDimensions()
    {
        return new Dimension(columns * IShape.TILESIZE, rows * IShape.TILESIZE);
    }

    public GameState getState() {
        return state;
    }

    public static class Builder
    {
        private Color backgroundColor;
        private int rows, columns;
        private AbstractWinStrategy strategy;
        private boolean areDiagonalWinsAllowed;
        private static Image BoardParts[] = new Image[9];
        public Builder()
        {
            this.rows = 3;
            this.columns = 3;
            areDiagonalWinsAllowed = true;
        }

        public String getFormat()
        {
            return backgroundColor == null ? "Standard" : rows + "x" + columns;
        }

        public void dontAllowDiagonalWins()
        {
            areDiagonalWinsAllowed = false;
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
            if(strategy == null)
            {
                strategy = areDiagonalWinsAllowed ? new AllowDiagonalWinsStrategy(rows, columns) : new NoDiagonalWinsStrategy(rows, columns);
            }
            BoardPartsSingleton singleton = BoardPartsSingleton.getInstance();
            Image image = new BufferedImage(columns * IShape.TILESIZE, rows * IShape.TILESIZE, BufferedImage.TYPE_BYTE_BINARY);
            Graphics2D graphics2D = (Graphics2D) image.getGraphics();
            graphics2D.drawImage(singleton.getPart(0, 0), 0, 0, null);
            graphics2D.drawImage(singleton.getPart(0, 2), 0* IShape.TILESIZE, (rows-1) * IShape.TILESIZE, null);
            graphics2D.drawImage(singleton.getPart(2, 0), (columns-1) * IShape.TILESIZE, 0 * IShape.TILESIZE, null);
            graphics2D.drawImage(singleton.getPart(2, 2), (columns-1) * IShape.TILESIZE, (rows-1) * IShape.TILESIZE, null);
            ShapeEnum tiles[][] = new ShapeEnum[columns][rows];
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
            return new Board(image, tiles, columns, rows, strategy);
        }
    }
}
