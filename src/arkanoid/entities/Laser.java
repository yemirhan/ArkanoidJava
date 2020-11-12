package arkanoid.entities;

import java.awt.*;

public class Laser extends Entity {
    public Laser(float x, float y) {
        super(x, y, 2, 8);
        dx = 0;
        dy = -200;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        super.render(g);
    }
}
