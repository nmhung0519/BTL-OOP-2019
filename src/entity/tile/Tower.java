package entity.tile;

import java.awt.*;
import java.util.List;
import drawer.drawTower;

public class Tower extends GameTile {
    private int type;
    private int speed;
    private double range;
    private int count;
    private Enemy target;
    protected Image tower, gun;
    private double rad = 0;
    public Tower(int type, Image tower, Image gun, int posX, int posY, double range, int speed) {
        super(posX, posY);
        this.type = type;
        target = null;
        this.range = range;
        this.speed = speed;
        count = 1;
        rad = 0;
        this.tower = tower;
        this.gun = gun;
    }
    @Override
    public void update() {
        if (target != null) {
            int x = target.getPosX() - getPosX();
            int y = target.getPosY() - getPosY();
            double delta = Math.sqrt(Math.pow((double) x, 2) + Math.pow((double) y, 2));
            rad = Math.acos(- y / delta);
            if (x < 0) rad = - rad;
        }
    }
    @Override
    public void doDrawing(Graphics g) {
        update();
        drawTower.draw(tower, gun, getPoint(), rad, target, g);
    }
    public boolean inDistance(Point point) {
        double distance = Math.sqrt((double) (Math.pow(this.getPosX() - point.getPosX(), 2) + Math.pow(this.getPosY() - point.getPosY(), 2)));
        if (distance <= range) return true;
        else return false;
    }
    public Enemy targetEnemy(List<Enemy> enemies) {
        Enemy result = null;
        for (Enemy enemy : enemies) {
            if (inDistance(enemy.getPoint())) {
                if (result == null) result = enemy;
                else {
                    if (result.getDistance() < enemy.getDistance()) result = enemy;
                }
            }
        }
        target = result;
        return result;
    }
    public double getRange() {
        return this.range;
    }
    public boolean canShoot() {
        count--;
        if (count == 0) {
            count = speed;
            return true;
        }
        return false;
    }
    public Image getGun() {
        return this.gun;
    }
    public Image getImage() {
        return tower;
    }
    public Enemy getTarget() {
        return target;
    }
    public int getType() {
        return type;
    }
}
