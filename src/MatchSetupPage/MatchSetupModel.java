package MatchSetupPage;

import Player.Player;
import Player.PlayerDB;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;

public class MatchSetupModel implements ComboBoxModel<String> {
    private Player host;
    public int rows, columns;
    public String colors[] = new String[]{"White", "Cyan"};
    public String rowError, columnError;
    public Color color;
    public boolean standard;
    private ListDataListener listener;

    public Player getHost()
    {
        return host;
    }

    public MatchSetupModel()
    {
        rows = 3;
        columns = 3;
        color = Color.WHITE;
        standard = true;
        host = PlayerDB.player;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        try {
            color = (Color) Color.class.getField(((String) anItem).toLowerCase()).get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getSelectedItem() {
        return color == Color.cyan ? "Cyan" : "White";
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
