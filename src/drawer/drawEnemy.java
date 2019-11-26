package drawer;

import Config.Config;
import Effect.AbstractEffect;
import entity.tile.Enemy;

import java.awt.*;

public class drawEnemy {
    public static void draw(Image img, Enemy enemy, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.red);
        g2d.fillRect(enemy.getPosX() - 40, enemy.getPosY() - 55, (int)(80 * ((double)enemy.getHealth() / (double) enemy.getMaxHealth())), 5);
        g2d.setPaint(Color.BLACK);
        g2d.drawRect(enemy.getPosX() - 40, enemy.getPosY() - 55, 80, 5);
        g2d.rotate(enemy.getRad(), enemy.getPosX(), enemy.getPosY());
        g2d.drawImage(img, enemy.getPosX() - img.getWidth(null)/2, enemy.getPosY() - img.getHeight(null)/2, null);
        g2d.rotate(-enemy.getRad(), enemy.getPosX(), enemy.getPosY());
    }
}
