package SignInPage;

import javax.swing.*;
import java.awt.*;

public class SignInView extends JPanel {
    private JLabel nameLabel;
    public JTextField nameField;
    public JLabel errorLabel;
    public JButton submitButton;

    public SignInView()
    {
        nameLabel = new JLabel("Name");
        nameField = new JTextField("", 20);
        errorLabel = new JLabel();
        submitButton = new JButton("Submit");
        add(nameLabel);
        add(nameField);
        add(errorLabel);
        add(submitButton);
    }

    public void updateView(SignInModel model)
    {
        errorLabel.setText(model.errorMsg);
        errorLabel.setPreferredSize(new Dimension(300, 20));
        submitButton.setEnabled(model.errorMsg == "");
        repaint();
    }
}
