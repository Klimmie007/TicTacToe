package GamePage;

import Board.Board;
import Command.ICommand;
import Command.MoveCommand;
import Board.GameState;
import Game.MatchState;
import Shape.IShape;
import Shape.ShapeEnum;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private GameModel model;
    private GameView view;
    private List<ICommand> commands = new ArrayList<>();

    public GameController(GameModel model, GameView view)
    {
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
                    ICommand command = new MoveCommand(commands.size() % 2 == 1 ? ShapeEnum.CIRCLE : ShapeEnum.CROSS, x, y);
                    commands.add(command);
                    model.getGame().executeCommand(command);
                    view.setImage(model.getBoard());
                    view.repaint();
                }
            }
        });
        view.setImage(model.getBoard());
    }
}
