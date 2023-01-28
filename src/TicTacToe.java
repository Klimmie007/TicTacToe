import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;

public class TicTacToe extends JPanel {
    public static final int ZEROX = 0;
    public static final int fieldWidth = 113;
    public static final int separatorWidth = 15;

    static final int ZEROY = 0;

    private Board board;

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        board.draw(graphics2D);
    }

    public TicTacToe()
    {
        Board.Builder builder = new Board.Builder(2, 4);
        board = builder.build();
        setPreferredSize(board.getDimensions());

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = (int)e.getX()/Shape.TILESIZE;
                int y = (int)e.getY()/Shape.TILESIZE;
                board.addShape(new ShapeAffineDecorator(new Shape(ShapeEnum.CROSS), x ,y), x, y);
                repaint();
            }
        });
    }
}
