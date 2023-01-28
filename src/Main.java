import Game.TicTacToe;
import GamePage.GameController;
import GamePage.GameModel;
import GamePage.GameView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameView view = new GameView();
        GameModel model = new GameModel(new TicTacToe());
        GameController controller = new GameController(model, view);
        frame.add(view);

        frame.pack();
        frame.setVisible(true);
    }
}