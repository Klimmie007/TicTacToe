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
                return result > 1;
            } catch (NumberFormatException e) {
                return false;
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
                // warn the user and don't allow the insert
            }

        }
    }

    public DocumentListener getRowListener()
    {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(e.getDocument().getLength() == 0)
                    return;
                try {
                    model.rows = Integer.parseInt(e.getDocument().getText(0, e.getDocument().getLength()));
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(e.getDocument().getLength() == 0)
                    return;
                try {
                    model.rows = Integer.parseInt(e.getDocument().getText(0, e.getDocument().getLength()));
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        };
    }

    public DocumentListener getColumnListener()
    {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(e.getDocument().getLength() == 0)
                    return;
                try {
                    model.columns = Integer.parseInt(e.getDocument().getText(0, e.getDocument().getLength()));
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(e.getDocument().getLength() == 0)
                    return;
                try {
                    model.columns = Integer.parseInt(e.getDocument().getText(0, e.getDocument().getLength()));
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        };
    }

    public MatchSetupController(MatchSetupModel model, MatchSetupView view)
    {
        this.view = view;
        this.model = model;
        view.updateView(model);
        IntFilter filter = new IntFilter();
        PlainDocument document = (PlainDocument) view.rowField.getDocument();
        document.setDocumentFilter(filter);
        document.addDocumentListener(getRowListener());
        document = (PlainDocument) view.columnField.getDocument();
        document.setDocumentFilter(filter);
        document.addDocumentListener(getColumnListener());
        view.isStandard.addItemListener(new ItemListener() {
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
        });
        view.OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setEnabled(false);
                view.setVisible(false);
                JFrame frame = MainWindowSingleton.getInstance();
                GameView view1 = new GameView();
                GameModel model1 = new GameModel(new TicTacToe(model));
                GameController controller = new GameController(model1, view1);
                frame.add(view1);
                frame.add(view1.getBar(), BorderLayout.PAGE_START);
                frame.pack();
            }
        });
    }
}
