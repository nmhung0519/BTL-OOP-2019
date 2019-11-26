package drawer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class draw {
    public static void draw(BufferedImage image, Graphics2D g2d) {
        double rad = 0;
        AffineTransform tx = AffineTransform.getRotateInstance(rad, image.getWidth(), image.getHeight());
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g2d.drawImage(op.filter(image, null), 300, 300, null);
    }
}