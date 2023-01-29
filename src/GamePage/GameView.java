package GamePage;

import Board.Board;
import Board.GameState;
import Game.MatchState;
import Shape.IShape;

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

    public void updateView(GameModel model)
    {
        setImage(model.getBoard());
        switch(model.getGame().getState())
        {
            case FINISHED -> {
                Graphics2D g = (Graphics2D) image.getGraphics();
                g.setPaint(new Color(0f, 0f, 0f, 0.7f));
                g.fillRect(IShape.TILESIZE, IShape.TILESIZE, IShape.TILESIZE, IShape.TILESIZE);
                g.setPaint(Color.white);
                String result;
                switch (model.getBoard().getState())
                {
                    case DRAW -> {
                        result = "It's a draw!";
                        break;
                    }
                    case CROSS_WON -> {
                        result = "Cross won!";
                        break;
                    }
                    case CIRCLE_WON -> {
                        result = "Circle won!";
                        break;
                    }
                    default -> {
                        result = "FUCK";
                        break;
                    }
                }
                g.drawString(result, (float)(IShape.TILESIZE * 1.2), (float) (IShape.TILESIZE * 1.2));
                g.drawString("Click to offer a rematch...", (float)(IShape.TILESIZE * 1.2), (float) (IShape.TILESIZE * 1.3));
            }
        }
        repaint();
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
