package SignInPage;

import MainWindow.MainWindowSingleton;
import MatchChoicePage.MatchChoiceController;
import MatchChoicePage.MatchChoiceModel;
import MatchChoicePage.MatchChoiceView;
import Player.Player;
import Player.PlayerDB;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInController {
    private SignInModel model;
    private SignInView view;

    private class NameFilter extends DocumentFilter
    {
        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string,
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
            return text.length() < 20;
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,
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
        public void remove(DocumentFilter.FilterBypass fb, int offset, int length)
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

    private DocumentListener getDocumentListener()
    {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    model.name = e.getDocument().getText(0, e.getDocument().getLength());
                    if(e.getDocument().getLength() > 2)
                    {
                        model.errorMsg = "";
                    }
                    else
                    {
                        model.errorMsg = "Name cannot be shorter than 3 characters";
                    }
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
                view.updateView(model);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    model.name = e.getDocument().getText(0, e.getDocument().getLength());
                    if(e.getDocument().getLength() < 3)
                    {
                        model.errorMsg = "Name cannot be shorter than 3 characters";
                    }
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
                view.updateView(model);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    model.name = e.getDocument().getText(0, e.getDocument().getLength());
                    if(e.getDocument().getLength() < 3)
                    {
                        model.errorMsg = "Name cannot be shorter than 3 characters";
                    }
                    else
                    {
                        model.errorMsg = "";
                    }
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
                view.updateView(model);
            }
        };
    }

    public SignInController(SignInModel model, SignInView view)
    {
        this.model = model;
        this.view = view;
        view.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerDB.player = new Player(model.name);
                MatchChoiceView view = new MatchChoiceView();
                MatchChoiceModel model = new MatchChoiceModel();
                MatchChoiceController controller = new MatchChoiceController(view, model);
            }
        });
        PlainDocument document = (PlainDocument) view.nameField.getDocument();
        document.setDocumentFilter(new NameFilter());
        document.addDocumentListener(getDocumentListener());
        view.updateView(model);
        MainWindowSingleton frame = MainWindowSingleton.getInstance();
        frame.getContentPane().removeAll();
        frame.add(view);
        frame.pack();
        frame.setVisible(true);
        frame.release();
    }
}
