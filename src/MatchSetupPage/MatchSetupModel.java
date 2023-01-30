package MatchSetupPage;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;

public class MatchSetupModel implements ComboBoxModel<String> {
    public int rows, columns;
    public String colors[] = new String[]{"White", "Cyan"};
    public String rowError, columnError;
    public Color backgroundColor;
    public boolean isStandard, allowDiagonalWins;
    private ListDataListener listener;

    public MatchSetupModel()
    {
        rows = 3;
        columns = 3;
        backgroundColor = Color.WHITE;
        isStandard = true;
        rowError = "";
        columnError = "";
    }

    @Override
    public void setSelectedItem(Object anItem) {
        try {
            backgroundColor = (Color) Color.class.getField(((String) anItem).toLowerCase()).get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getSelectedItem() {
        return backgroundColor == Color.cyan ? "Cyan" : "White";
    }

    @Override
    public int getSize() {
        return colors.length;
    }

    @Override
    public String getElementAt(int index) {
        return colors[index];
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listener = l;
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listener = null;
    }
}
