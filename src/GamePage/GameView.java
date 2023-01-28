package GamePage;

import Board.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameView extends JPanel {
    private Image image;
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.drawImage(image, 0, 0, null);
    }

    public void setImage(Board board) {
        image = new BufferedImage(board.getDimensions().width, board.getDimensions().height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        board.draw(g);
        setPreferredSize(board.getDimensions());
    }
}
