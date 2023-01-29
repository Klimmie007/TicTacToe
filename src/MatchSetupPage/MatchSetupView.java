package MatchSetupPage;

import GamePage.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class MatchSetupView extends JPanel {
    public Checkbox isStandard;
    public JLabel rowLabel, columnLabel, colorLabel;
    public JTextField rowField, columnField;
    public String colors[] = new String[]{"White", "Cyan"};
    public JButton OKButton;
    public JComboBox<String> colorChoices;

    public MatchSetupView()
    {
        rowLabel = new JLabel("Rows:");
        columnLabel = new JLabel("Columns:");
        colorLabel = new JLabel("Background color");
        isStandard = new Checkbox("Make standard game", true);
        rowField = new JTextField("3", 10);
        rowField.setEnabled(false);
        columnField = new JTextField("3", 10);
        columnField.setEnabled(false);
        colorChoices = new JComboBox<>();
        colorChoices.setEnabled(false);
        OKButton = new JButton("OK");
        add(rowLabel);
        add(rowField);
        add(columnLabel);
        add(columnField);
        add(colorLabel);
        add(colorChoices);
        add(isStandard);
        add(OKButton);
    }

    public void updateView(MatchSetupModel model)
    {
        rowField.setText(Integer.toString(model.rows));
        columnField.setText(Integer.toString(model.columns));
        colorChoices.setModel(model);
        isStandard.setState(model.standard);
        rowField.setEnabled(!model.standard);
        columnField.setEnabled(!model.standard);
        colorChoices.setEnabled(!model.standard);
    }
}
