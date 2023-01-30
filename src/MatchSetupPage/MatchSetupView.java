package MatchSetupPage;

import javax.swing.*;
import java.awt.*;

public class MatchSetupView extends JPanel {
    public Checkbox isStandard, allowDiagonalWins;
    public JLabel rowLabel, columnLabel, colorLabel, rowErrorLabel, columnErrorLabel;
    public JTextField rowField, columnField;
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
        rowErrorLabel.setText(model.isStandard ? "" : model.rowError);
        columnErrorLabel.setText(model.isStandard ? "" : model.columnError);
        colorChoices.setModel(model);
        isStandard.setState(model.isStandard);
        allowDiagonalWins.setEnabled(!model.isStandard);
        rowField.setEnabled(!model.isStandard);
        columnField.setEnabled(!model.isStandard);
        colorChoices.setEnabled(!model.isStandard);
        OKButton.setEnabled(model.isStandard || (model.rowError == "" && model.columnError == ""));
    }
}
