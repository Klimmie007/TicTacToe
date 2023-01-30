package MatchSetupPage;

import GamePage.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class MatchSetupView extends JPanel {
    public Checkbox isStandard, allowDiagonalWins;
    public JLabel rowLabel, columnLabel, colorLabel, rowErrorLabel, columnErrorLabel;
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
        allowDiagonalWins = new Checkbox("Allow diagonal wins", true);
        rowField = new JTextField("3", 10);
        rowField.setEnabled(false);
        rowErrorLabel = new JLabel("");
        rowErrorLabel.setPreferredSize(new Dimension(500, 30));
        columnField = new JTextField("3", 10);
        columnField.setEnabled(false);
        columnErrorLabel = new JLabel();
        columnErrorLabel.setPreferredSize(new Dimension(500, 30));
        colorChoices = new JComboBox<>();
        colorChoices.setEnabled(false);
        OKButton = new JButton("OK");
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel rowGroup = new JPanel();
        rowGroup.add(rowLabel);
        rowGroup.add(rowField);
        add(rowGroup);
        add(rowErrorLabel);
        JPanel columnGroup = new JPanel();
        columnGroup.add(columnLabel);
        columnGroup.add(columnField);
        add(columnGroup);
        add(columnErrorLabel);
        JPanel colorGroup = new JPanel();
        colorGroup.add(colorLabel);
        colorGroup.add(colorChoices);
        add(colorGroup);
        JPanel ruleGroup = new JPanel();
        ruleGroup.add(allowDiagonalWins);
        add(ruleGroup);
        JPanel importantGroup = new JPanel();
        importantGroup.add(isStandard);
        importantGroup.add(OKButton);
        add(importantGroup);
        setPreferredSize(new Dimension(500, 160));
    }

    public void updateView(MatchSetupModel model)
    {
        rowErrorLabel.setText(model.standard ? "" : model.rowError);
        columnErrorLabel.setText(model.standard ? "" : model.columnError);
        colorChoices.setModel(model);
        isStandard.setState(model.standard);
        allowDiagonalWins.setEnabled(!model.standard);
        rowField.setEnabled(!model.standard);
        columnField.setEnabled(!model.standard);
        colorChoices.setEnabled(!model.standard);
        OKButton.setEnabled(model.standard || (model.rowError == "" && model.columnError == ""));
    }
}
