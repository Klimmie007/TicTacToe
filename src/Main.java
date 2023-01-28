import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TicTacToe board = new TicTacToe();
        frame.add(board);

        frame.pack();
        frame.setVisible(true);
    }
}