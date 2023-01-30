package MatchSetupPage;

import Game.TicTacToe;
import GamePage.GameController;
import GamePage.GameModel;
import GamePage.GameView;
import MainWindow.MainWindowSingleton;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MatchSetupController {
    private MatchSetupView view;
    private MatchSetupModel model;
    class IntFilter extends DocumentFilter
    {
        @Override
        public void insertString(FilterBypass fb, int offset, String string,
                                 AttributeSet attr) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.insert(offset, string);

            if (test(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            } else {
                // warn the user and don't allow the insert
            }
        }

        private boolean test(String text) {
            try {
                int result = Integer.parseInt(text);
                return true;
            } catch (NumberFormatException e) {
                return text.equals("");
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text,
                            AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (test(sb.toString())) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                // warn the user and don't allow the insert
            }

        }

        @Override
        public void remove(FilterBypass fb, int offset, int length)
                throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (test(sb.toString())) {
                super.remove(fb, offset, length);
            } else {
                if(sb.charAt(0) == '-');
                {
                    super.remove(fb, offset, length);
                }
                // warn the user and don't allow the insert
            }

        }
    }

    public String getErrorMsg(int value)
    {
        if(value <= 1)
        {
            return "Value has to be at least equal to 2";
        }
        else if (value > 5)
        {
            return "Game currently does not support values above 5";
        }
        else
        {
            return "";
        }
    }

    public DocumentListener getRowListener()
    {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    model.rows = Integer.parseInt(e.getDocument().getText(0, e.getDocument().getLength()));
                    model.rowError = getErrorMsg(model.rows);
                } catch (Exception ex) {
                    model.rowError = "Field cannot be empty";
                }
                view.updateView(model);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    model.rows = Integer.parseInt(e.getDocument().getText(0, e.getDocument().getLength()));
                    model.rowError = getErrorMsg(model.rows);
                } catch (Exception ex) {
                    model.rowError = "Field cannot be empty";
                }
                view.updateView(model);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    model.rows = Integer.parseInt(e.getDocument().getText(0, e.getDocument().getLength()));
                    model.rowError = getErrorMsg(model.rows);
                } catch (Exception ex) {
                    model.rowError = "Field cannot be empty";
                }
                view.updateView(model);
            }
        };
    }

    public DocumentListener getColumnListener()
    {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    model.columns = Integer.parseInt(e.getDocument().getText(0, e.getDocument().getLength()));
                    model.columnError = getErrorMsg(model.columns);
                } catch (Exception ex) {
                    model.columnError = "Field cannot be empty";
                }
                view.updateView(model);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    model.columns = Integer.parseInt(e.getDocument().getText(0, e.getDocument().getLength()));
                    model.columnError = getErrorMsg(model.columns);
                } catch (Exception ex) {
                    model.columnError = "Field cannot be empty";
                }
                view.updateView(model);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    model.columns = Integer.parseInt(e.getDocument().getText(0, e.getDocument().getLength()));
                    model.columnError = getErrorMsg(model.columns);
                } catch (Exception ex) {
                    model.columnError = "Field cannot be empty";
                }
                view.updateView(model);
            }
        };
    }

    public ItemListener getStandardListener()
    {
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                {
                    model.standard = true;
                }
                else
                {
                    model.standard = false;
                }
                view.updateView(model);
            }
        };
    }

    public ItemListener getDiagonalListener()
    {
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                {
                    model.allowDiagonalWins = true;
                }
                else
                {
                    model.allowDiagonalWins = false;
                }
                view.updateView(model);
            }
        };
    }

    public MatchSetupController(MatchSetupModel model, MatchSetupView view)
    {
        this.view = view;
        this.model = model;
        view.updateView(model);
        MainWindowSingleton frame = MainWindowSingleton.getInstance();
        frame.getContentPane().removeAll();
        frame.add(view);
        frame.pack();
        frame.release();
        IntFilter filter = new IntFilter();
        PlainDocument document = (PlainDocument) view.rowField.getDocument();
        document.setDocumentFilter(filter);
        document.addDocumentListener(getRowListener());
        document = (PlainDocument) view.columnField.getDocument();
        document.setDocumentFilter(filter);
        document.addDocumentListener(getColumnListener());
        view.isStandard.addItemListener(getStandardListener());
        view.allowDiagonalWins.addItemListener(getDiagonalListener());
        view.OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameView view1 = new GameView();
                GameModel model1 = new GameModel(new TicTacToe(model));
                GameController controller = new GameController(model1, view1);
            }
        });
    }
}
