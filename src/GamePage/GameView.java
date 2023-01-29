package GamePage;

import Board.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameView extends JPanel {
    private Image image;
    private JToolBar bar;
    private JButton undo, redo;

    public JToolBar getBar()
    {
        return bar;
    }
    public JButton getUndo()
    {
        return  undo;
    }

    public JButton getRedo()
    {
        return redo;
    }

    public GameView()
    {
        bar = new JToolBar();
        try{
            undo = new JButton(new ImageIcon(ImageIO.read(GameView.class.getResourceAsStream("/img/Undo.png"))));
            redo = new JButton(new ImageIcon(ImageIO.read(GameView.class.getResourceAsStream("/img/Redo.png"))));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
        bar.add(undo);
        bar.add(redo);
        undo.setEnabled(false);
        redo.setEnabled(false);
    }
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

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        bar.setVisible(aFlag);
    }
}
