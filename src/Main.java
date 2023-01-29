import Game.TicTacToe;
import GamePage.GameController;
import GamePage.GameModel;
import GamePage.GameView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameView view = new GameView();
        GameModel model = new GameModel(new TicTacToe());
        GameController controller = new GameController(model, view);
        frame.add(view);
        frame.add(view.getBar(), BorderLayout.PAGE_START);

        frame.pack();
        frame.setVisible(true);
    }
}