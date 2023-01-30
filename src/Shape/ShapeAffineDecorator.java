package Shape;

import Board.Board;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class ShapeAffineDecorator implements IShape {

    private IShape decorated;

    public ShapeAffineDecorator(IShape shape)
    {
        decorated = shape;
    }

    @Override
    public void draw(Graphics2D g, int x, int y) {
        AffineTransform save = g.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.translate(Board.ZEROX + x * TILESIZE, Board.ZEROY + y * TILESIZE);
        transform.scale(TILESIZE - 2 * Board.ZEROX, TILESIZE - 2 * Board.ZEROY);
        g.transform(transform);
        decorated.draw(g, x, y);
        g.setTransform(save);
    }

    @Override
    public IShape getDecorated() {
        return decorated;
    }
}
