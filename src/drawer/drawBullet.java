package drawer;

import entity.tile.Bullet;

import java.awt.*;

public class drawBullet {
    public static void draw(Bullet bullet,  Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Image img = bullet.getImg();
            g2d.rotate(bullet.getRad(), bullet.getPosX(), bullet.getPosY());
            g2d.drawImage(bullet.getImg(), bullet.getPosX() - img.getWidth(null) / 2, bullet.getPosY() - img.getHeight(null) / 2, null);
            g2d.rotate(- bullet.getRad(), bullet.getPosX(), bullet.getPosY());
    }
}
