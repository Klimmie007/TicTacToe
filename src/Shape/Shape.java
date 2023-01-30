package Shape;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.IOException;

public class Shape implements IShape {
    private Image img;
    private ShapeEnum shape;

    private Image makeTransparent(Image image)
    {
        ImageFilter filter = new RGBImageFilter() {
            public int markerRGB = Color.white.getRGB() | 0xFF000000;

            @Override
            public int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return rgb & 0x00FFFFFF;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };
        ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }
    public Shape(ShapeEnum choice)
    {
        shape = choice;
        try{
            if(choice == ShapeEnum.CROSS)
            {
                img = ImageIO.read(Shape.class.getResourceAsStream("/img/Cross.png"));
            }
            else
            {
                img = ImageIO.read(Shape.class.getResourceAsStream("/img/Circle.png"));
            }
            img = makeTransparent(img);
        } catch(IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void draw(Graphics2D g, int x, int y) {
        g.drawImage(img, 0, 0, 1, 1,  null);
    }

    @Override
    public IShape getDecorated() {
        return null;
    }
}
