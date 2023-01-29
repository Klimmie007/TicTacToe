package MainWindow;

import javax.swing.*;

public class MainWindowSingleton extends JFrame {
    private static MainWindowSingleton instance;

    private MainWindowSingleton()
    {
        super("Tic Tac Toe");
    }

    public static MainWindowSingleton getInstance()
    {
        if(instance == null)
        {
            instance = new MainWindowSingleton();
        }
        return instance;
    }
}
