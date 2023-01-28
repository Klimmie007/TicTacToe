package Shape;

import java.awt.*;

public interface IShape {
    public static final int TILESIZE = 200;

    ShapeEnum getChosen();

    void draw(Graphics2D g, int x, int y);

    IShape getDecorated();

}
