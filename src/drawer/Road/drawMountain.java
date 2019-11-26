package drawer.Road;

import drawer.drawEntity;
import entity.tile.Point;

import javax.swing.*;
import java.awt.*;

public class drawMountain implements drawEntity {
    public static void draw(int posX, int posY, Graphics2D g) {
        Image img = new ImageIcon("out/production/Picture/Map/Mountain.png").getImage();
        g.drawImage(img, posX - img.getHeight(null)/2, posY - img.getWidth(null)/2, null);
    }
}
