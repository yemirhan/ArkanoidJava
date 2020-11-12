package arkanoid.entities;

import java.awt.*;

public abstract class Entity {
    public float x;
    public float y;
    public float w;
    public float h;
    public float dx;
    public float dy;

    public Entity(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void render(Graphics g) {
        g.fillRect((int) x, (int) y, (int) w, (int) h);
    }

    public void update(float dt) {
        x += dx * dt;
        y += dy * dt;
    }

    public void restart() {
        dx = 0;
        dy = 0;
    }
}
