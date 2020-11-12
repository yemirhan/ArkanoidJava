package arkanoid.entities;

import arkanoid.Input;
import arkanoid.Rectangle;
import arkanoid.screens.Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Player extends Entity {

    private static final float SPEED = 340f;
    public static int WIDTH = 64;
    public static int HEIGHT = 16;
    public int lives = 3;
    private int size = 1;
    private boolean laser = false;
    private boolean sticky = false;

    private ArrayList<Laser> lasers = new ArrayList<>();

    public Player() {
        super((Game.WIDTH - WIDTH) / 2.0f, Game.HEIGHT - HEIGHT * 2.0f, WIDTH, HEIGHT);
    }

    /* Powerups */

    public void increaseLength() {
        switch (size) {
            // small
            case 0:
                w = 64;
                WIDTH = 64;
                break;
            case 1:
                w = 96;
                WIDTH = 96;
                break;
        }
        size++;
        if (size > 2) size = 2;
    }

    public void decreaseLength() {
        switch (size) {
            // small
            case 2:
                w = 64;
                WIDTH = 64;
                break;
            case 1:
                w = 48;
                WIDTH = 48;
                break;
        }
        size--;
        if (size < 0) size = 0;
    }

    public void laser(boolean laser) {
        this.laser = laser;
    }

    public void sticky(boolean sticky) {
        this.sticky = sticky;
    }

    public boolean isSticky() {
        return sticky;
    }

    @Override
    public void restart() {
        super.restart();
        x = (Game.WIDTH - WIDTH) / 2.0f;
        y = Game.HEIGHT - HEIGHT * 2.0f;
        lasers.clear();
    }

    @Override
    public void update(float dt) {
        dx = 0;
        x = Input.getMouseX() - WIDTH / 2.0f;
        if (x < 0) {
            x = 0;
        }
        if (x > Game.WIDTH - w) {
            x = Game.WIDTH - w;
        }
        if (laser) {
            if (Input.isMouseClicked(MouseEvent.BUTTON1)) {
                lasers.add(new Laser(x, y));
                lasers.add(new Laser(x + w, y));
            }
        }
        for (int i = lasers.size() - 1; i >= 0; i--) {
            Laser laser = lasers.get(i);
            laser.update(dt);
            if (laser.y < -laser.h) {
                lasers.remove(i);
                continue;
            }
            Rectangle laserRect = new Rectangle(laser.x, laser.y, laser.w, laser.h);
            for (int j = Game.bricks.size() - 1; j >= 0; j--) {
                Brick brick = Game.bricks.get(j);
                Rectangle brickRect = new Rectangle(brick.x, brick.y, brick.w, brick.h);
                if (laserRect.intersects(brickRect)) {
                    Game.hitBrickWithLaser(j);
                    lasers.remove(i);
                    System.out.println("w");
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        if (sticky) {
            g.setColor(Color.GREEN);
        }
        if (laser) {
            g.setColor(Color.RED);
        }
        g.fillRect((int) x, (int) y, WIDTH, HEIGHT);
        for (Laser laser : lasers) {
            laser.render(g);
        }
    }
}
