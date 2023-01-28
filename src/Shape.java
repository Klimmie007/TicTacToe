import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Shape implements IShape{
    private Image img;
    private int x, y;
    public Shape(ShapeEnum choice)
    {
        try{
            if(choice == ShapeEnum.CROSS)
            {
                img = ImageIO.read(Shape.class.getResourceAsStream("/img/Cross.png"));
            }
            else
            {
                img = ImageIO.read(Shape.class.getResourceAsStream("/img/Circle.png"));
            }
        } catch(IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void draw(Graphics2D g, int x, int y) {
        g.drawImage(img, x, y, 1, 1,  null);
    }

    @Override
    public IShape getDecorated() {
        return null;
    }
}
