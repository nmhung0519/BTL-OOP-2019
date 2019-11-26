package entity.tile;

import java.awt.*;
import drawer.Road.*;

public class Mountain extends Road {
    private double distance;

    public Mountain(int posX, int posY) {
        super(posX, posY);
    }

    @Override
    public void update() {
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    @Override
    public void doDrawing(Graphics g) {
        drawMountain.draw(super.getPosX() - 50, super.getPosY() - 50, (Graphics2D) g);
    }
}
