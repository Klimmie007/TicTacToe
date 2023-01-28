package Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class BoardPartsSingleton {
    private Image parts[] = new Image[9];

    private static BoardPartsSingleton instance;

    private BoardPartsSingleton()
    {
        try{
            parts[0] = ImageIO.read(BoardPartsSingleton.class.getResourceAsStream("/img/BoardUpperLeft.png"));
            parts[1] = ImageIO.read(BoardPartsSingleton.class.getResourceAsStream("/img/BoardUpper.png"));
            parts[2] = ImageIO.read(BoardPartsSingleton.class.getResourceAsStream("/img/BoardUpperRight.png"));
            parts[3] = ImageIO.read(BoardPartsSingleton.class.getResourceAsStream("/img/BoardLeft.png"));
            parts[4] = ImageIO.read(BoardPartsSingleton.class.getResourceAsStream("/img/BoardCentre.png"));
            parts[5] = ImageIO.read(BoardPartsSingleton.class.getResourceAsStream("/img/BoardRight.png"));
            parts[6] = ImageIO.read(BoardPartsSingleton.class.getResourceAsStream("/img/BoardLowerLeft.png"));
            parts[7] = ImageIO.read(BoardPartsSingleton.class.getResourceAsStream("/img/BoardLower.png"));
            parts[8] = ImageIO.read(BoardPartsSingleton.class.getResourceAsStream("/img/BoardLowerRight.png"));
        } catch(IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public Image getPart(int coordX, int coordY)
    {
        return parts[coordY * 3 + coordX];
    }

    public static BoardPartsSingleton getInstance() {
        if(instance == null)
        {
            instance = new BoardPartsSingleton();
        }
        return instance;
    }
}
