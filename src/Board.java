import java.awt.*;
import java.awt.image.BufferedImage;

public class Board {
    public static final int ZEROX = 3;
    public static final int ZEROY = 3;

    private IShape tiles[][];
    private Image image = null;
    private int rows, columns;

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
        return new Dimension(columns*Shape.TILESIZE, rows*Shape.TILESIZE);
    }

    public static class Builder
    {
        private static int rows, columns;
        private static Image BoardParts[] = new Image[9];
        public Builder(int rows, int columns)
        {
            this.rows = rows;
            this.columns = columns;
        }
        public Board build()
        {
            BoardPartsSingleton singleton = BoardPartsSingleton.getInstance();
            Image image = new BufferedImage(columns*IShape.TILESIZE, rows*IShape.TILESIZE, BufferedImage.TYPE_BYTE_BINARY);
            Graphics2D graphics2D = (Graphics2D) image.getGraphics();
            graphics2D.drawImage(singleton.getPart(0, 0), 0, 0, null);
            graphics2D.drawImage(singleton.getPart(0, 2), 0*IShape.TILESIZE, (rows-1) * IShape.TILESIZE, null);
            graphics2D.drawImage(singleton.getPart(2, 0), (columns-1) * IShape.TILESIZE, 0 * IShape.TILESIZE, null);
            graphics2D.drawImage(singleton.getPart(2, 2), (columns-1) * IShape.TILESIZE, (rows-1) * IShape.TILESIZE, null);
            IShape tiles[][] = new IShape[columns][rows];
            for(int i = 1; i < columns - 1; i++)
            {
                graphics2D.drawImage(singleton.getPart(1, 0), i*IShape.TILESIZE, 0, null);
                graphics2D.drawImage(singleton.getPart(1, 2), i*IShape.TILESIZE, (rows - 1) * IShape.TILESIZE, null);
                for(int j = 1; j < rows - 1; j++)
                {
                    graphics2D.drawImage(singleton.getPart(1, 1), i*IShape.TILESIZE, j*IShape.TILESIZE, null);
                }
            }
            for(int i = 1; i < rows - 1; i++)
            {
                graphics2D.drawImage(singleton.getPart(0, 1), 0, i*IShape.TILESIZE, null);
                graphics2D.drawImage(singleton.getPart(2, 1), (columns - 1) * IShape.TILESIZE,i*IShape.TILESIZE, null);
            }
            graphics2D.dispose();
            return new Board(image, tiles, columns, rows);
        }
    }
}
