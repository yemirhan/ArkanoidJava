package arkanoid.entities;

import arkanoid.MathUtil;

import java.awt.*;

public class Brick extends Entity {

    public static final int WIDTH = 32;
    public static final int HEIGHT = 16;
    public int lives;
    private Color color;

    public Brick(float x, float y, String type) {
        super(x, y, WIDTH, HEIGHT);
        switch (type) {
            case "1":
                color = Color.getHSBColor(MathUtil.random(0.0f, 1.0f), MathUtil.random(0.6f, 1.0f), MathUtil.random(0.4f, 1.0f));
                lives = 1;
                break;
            case "2":
                color = Color.getHSBColor(0.0f, 0.0f, MathUtil.random(0.6f, 0.8f));
                lives = 2;
                break;
            case "x":
                color = Color.getHSBColor(0.0f, 0.0f, MathUtil.random(0.2f, 0.3f));
                lives = Integer.MAX_VALUE;
                break;
        }
    }

    public void reload() {
        if (lives == 1) {
            color = Color.getHSBColor(MathUtil.random(0.0f, 1.0f), MathUtil.random(0.6f, 1.0f), MathUtil.random(0.4f, 1.0f));
        }
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        super.render(g);
    }
}
