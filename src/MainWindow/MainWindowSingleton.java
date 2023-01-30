package MainWindow;

import javax.swing.*;

public class MainWindowSingleton extends JFrame {
    private static MainWindowSingleton instance;

    private MainWindowSingleton()
    {
        super("Tic Tac Toe");
    }

    private static boolean isUsed = false;

    public static MainWindowSingleton getInstance()
    {
        if(isUsed)
        {
            return null;
        }
        if(instance == null)
        {
            instance = new MainWindowSingleton();
        }
        isUsed = true;
        return instance;
    }
    public void release()
    {
        isUsed = false;
    }
}
