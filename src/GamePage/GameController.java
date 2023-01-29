package GamePage;

import Board.Board;
import Command.ICommand;
import Command.MoveCommand;
import Board.GameState;
import Command.UndoCommand;
import Game.MatchState;
import Shape.IShape;
import Shape.ShapeEnum;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private GameModel model;
    private GameView view;

    private MouseAdapter getAdapter()
    {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(model.getGame().getState() == MatchState.ABANDONED || model.getGame().getState() == MatchState.FINISHED)
                {
                    model.rematch();
                    view.updateView(model);
                    return;
                }
                int x = (int)e.getX()/ IShape.TILESIZE;
                int y = (int)e.getY()/ IShape.TILESIZE;
                Board board = model.getBoard();
                if(board.isSpotEmpty(x, y))
                {
                    if(model.commandPointer != model.commands.size())
                    {
                        for(int i = model.commands.size() - 1; i >= model.commandPointer ; i--)
                        {
                            model.commands.remove(i);
                        }
                    }
                    model.commandPointer++;
                    ICommand command = new MoveCommand(model.commands.size() % 2 == 1 ? ShapeEnum.CIRCLE : ShapeEnum.CROSS, x, y);
                    model.commands.add(command);
                    model.getGame().executeCommand(command);
                    view.getUndo().setEnabled(true);
                    view.updateView(model);
                }
            }
        };
    }

    private ActionListener getUndoAction()
    {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!model.canUndo())
                {
                    return;
                }
                model.commandPointer--;
                ICommand command = new UndoCommand((MoveCommand) model.commands.get(model.commandPointer));
                model.getGame().executeCommand(command);
                view.updateView(model);
            }
        };
    }

    private ActionListener getRedoAction()
    {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!model.canRedo())
                {
                    return;
                }
                model.getGame().executeCommand(model.commands.get(model.commandPointer));
                model.commandPointer++;
                view.updateView(model);
            }
        };
    }

    public GameController(GameModel model, GameView view)
    {
        this.model = model;
        this.view = view;
        view.addMouseListener(getAdapter());
        view.setImage(model.getBoard());
        view.getUndo().addActionListener(getUndoAction());
        view.getRedo().addActionListener(getRedoAction());
    }
}
