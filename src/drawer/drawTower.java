package drawer;

import entity.tile.*;

import java.awt.*;
import entity.tile.Point;

public class drawTower {
    public static void draw(Image img, Image gun, Point point, double rad, Enemy enemy, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, point.getPosX() - (img.getHeight(null)/2), point.getPosY() - (img.getWidth(null)/2), null);
        g2d.rotate(rad, point.getPosX(), point.getPosY());
        g2d.drawImage(gun, point.getPosX() - (gun.getHeight(null)/2), point.getPosY() - (gun.getWidth(null)/2), null);
        g2d.rotate(-rad, point.getPosX(), point.getPosY());
    }
}
