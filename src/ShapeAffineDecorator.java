import java.awt.*;
import java.awt.geom.AffineTransform;

public class ShapeAffineDecorator implements IShape{

    private IShape decorated;
    private AffineTransform transform;

    public ShapeAffineDecorator(IShape shape, int x, int y)
    {
        decorated = shape;
        transform = new AffineTransform();
        transform.translate(Board.ZEROX + 2 * Board.ZEROX * x, Board.ZEROY + 2 * Board.ZEROY * y);
        transform.scale(TILESIZE-6, TILESIZE-6);
    }
    @Override
    public void draw(Graphics2D g, int x, int y) {
        AffineTransform save = g.getTransform();
        g.transform(transform);
        decorated.draw(g, x, y);
        g.setTransform(save);
    }

    @Override
    public IShape getDecorated() {
        return decorated;
    }
}
