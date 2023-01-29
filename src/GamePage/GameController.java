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
    private List<ICommand> commands = new ArrayList<>();
    private int commandPointer;

    public GameController(GameModel model, GameView view)
    {
        commandPointer = 0;
        this.model = model;
        this.view = view;
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(model.getGame().getState() == MatchState.ABANDONED || model.getGame().getState() == MatchState.FINISHED)
                {
                    return;
                }
                int x = (int)e.getX()/ IShape.TILESIZE;
                int y = (int)e.getY()/ IShape.TILESIZE;
                Board board = model.getBoard();
                if(board.isSpotEmpty(x, y))
                {
                    if(commandPointer != commands.size())
                    {
                        for(int i = commands.size() - 1; i >= commandPointer ; i--)
                        {
                            commands.remove(i);
                        }
                        view.getRedo().setEnabled(false);
                    }
                    commandPointer++;
                    ICommand command = new MoveCommand(commands.size() % 2 == 1 ? ShapeEnum.CIRCLE : ShapeEnum.CROSS, x, y);
                    commands.add(command);
                    model.getGame().executeCommand(command);
                    view.getUndo().setEnabled(true);
                    view.setImage(model.getBoard());
                    view.repaint();
                }
            }
        });
        view.setImage(model.getBoard());
        view.getUndo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(commands.size() == 0)
                {
                    return;
                }
                commandPointer--;
                if(commandPointer == 0)
                {
                    view.getUndo().setEnabled(false);
                }
                ICommand command = new UndoCommand((MoveCommand) commands.get(commandPointer));
                model.getGame().executeCommand(command);
                view.getRedo().setEnabled(true);
                view.setImage(model.getBoard());
                view.repaint();
            }
        });

        view.getRedo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(commandPointer == commands.size())
                {
                    return;
                }
                model.getGame().executeCommand(commands.get(commandPointer));
                commandPointer++;
                if(commandPointer == commands.size())
                {
                    view.getRedo().setEnabled(false);
                }
                view.getUndo().setEnabled(true);
                view.setImage(model.getBoard());
                view.repaint();
            }
        });
    }
}
