package arkanoid.entities;

import arkanoid.MathUtil;
import arkanoid.Rectangle;
import arkanoid.screens.Game;

import java.awt.*;

import static arkanoid.screens.Game.player;

public class Ball extends Entity {
    public static final int RADIUS = 4;
    private static final float DY_MAX = 400f;
    private static final float DX_MAX = 300f;

    private boolean isMoving = false;
    private float offset;

    private boolean fireball = false;
    private boolean softball = false;

    public Ball() {
        super(0, 0, 2 * RADIUS, 2 * RADIUS);
        x = player.x + (player.w - w) / 2.0f + offset;
        y = player.y - h;
        offset = player.w * MathUtil.random(-0.3f, 0.3f);
    }

    public Ball(float x, float y) {
        super(x, y, 2 * RADIUS, 2 * RADIUS);
    }

    public void fireball(boolean onFire) {
        if (onFire) {
            softball(false);
        }
        fireball = onFire;
    }

    public void softball(boolean soft) {
        if (soft) {
            fireball(false);
        }
        softball = soft;
    }

    public boolean isFireball() {
        return fireball;
    }

    public boolean isSoftball() {
        return softball;
    }

    public void restart() {
        super.restart();
        x = player.x + (player.w - w) / 2.0f + offset;
        y = player.y - h;
        offset = player.w * MathUtil.random(-0.3f, 0.3f);
        isMoving = false;
    }

    public void shoot() {
        if (!isMoving) {
            float amount = (x - player.x) / player.w;
            dx = MathUtil.lerp(-DX_MAX, DY_MAX, amount);
            dy = -DY_MAX / 2.0f;
            isMoving = true;
        }
    }

    @Override
    public void update(float dt) {
        if (isMoving) {
            x += dx * dt;
            y += dy * dt;

            // Collision with walls
            if (x < 0) {
                x = 0;
                dx = -dx;
            }
            if (x > Game.WIDTH - w) {
                x = Game.WIDTH - w;
                dx = -dx;
            }
            if (y < 0) {
                y = 0;
                dy = -dy;
            }
            if (Game.shield) {
                if (y + 2 * RADIUS > Game.HEIGHT - 32) {
                    y = Game.HEIGHT - 32 - 2 * RADIUS - 1;
                    dy = -dy;
                }
            }
            if (y > Game.HEIGHT) {
                Game.getInstance().die();
            }

            // Collision with player
            Rectangle playerRect = new Rectangle(player.x, player.y, player.w, player.h);
            Rectangle rect = new Rectangle(x, y, w, h);
            if (rect.intersects(playerRect)) {
                if (player.isSticky()) {
                    offset = x - (player.x + (player.w - w) / 2.0f);
                    isMoving = false;
                } else {
                    dy = -dy;
                    if (dy < 0) {
                        dy -= 20f;
                        if (dy < -DY_MAX) dy = -DY_MAX;
                    } else {
                        dy += 20f;
                        if (dy > DY_MAX) dy = DY_MAX;
                    }
                    y = player.y - 2 * RADIUS;
                    float amount = (x - player.x) / player.w;
                    dx = MathUtil.lerp(-DX_MAX, DX_MAX, amount);
                }
            }

            // Alpha is the angle from the brick's center to its corner.
            // It will be needed for collision calculations
            float alpha = (float) (Math.atan((Brick.HEIGHT / 2.0f) / (Brick.WIDTH / 2.0f)) / Math.PI * 180);


            // Collision with bricks
            for (int i = Game.bricks.size() - 1; i >= 0; i--) {
                Brick brick = Game.bricks.get(i);
                Rectangle brickRect = new Rectangle(brick.x, brick.y, brick.w, brick.h);
                if (rect.intersects(brickRect)) {
                    Game.hitBrick(i);
                    if (fireball && brick.lives <= 2) return;
                    // Angle between the ball and the brick
                    float angle = (float) Math.atan2((y + RADIUS) - (brick.y + Brick.HEIGHT / 2.0f), (x + RADIUS) - (brick.x + Brick.WIDTH / 2.0f));
                    // Convert to degrees
                    angle = (float) (angle / Math.PI * 180);
                    if (angle > 0) {
                        if (angle < alpha) {
                            //Right
                            dx = -dx;
                            x = brick.x + Brick.WIDTH + 1;
                        } else if (angle >= alpha && angle < 180 - alpha) {
                            // Bottom
                            dy = -dy;
                            y = brick.y + Brick.HEIGHT + 1;
                        } else if (angle >= 180 - alpha) {
                            // Left
                            dx = -dx;
                            x = brick.x - 2 * RADIUS - 1;
                        }
                        return;
                    } else {
                        if (angle > -alpha) {
                            //Right
                            dx = -dx;
                            x = brick.x + Brick.WIDTH + 1;
                        } else if (angle <= -alpha && angle > alpha - 180) {
                            // Top
                            dy = -dy;
                            y = brick.y - 2 * RADIUS - 1;
                        } else if (angle <= alpha - 180) {
                            // Left
                            dx = -dx;
                            x = brick.x - 2 * RADIUS - 1;
                        }
                        return;
                    }


                }
            }
        } else {
            x = player.x + (player.w - w) / 2.0f + offset;
            y = player.y - h;
            dx = 0;
            dy = 0;
        }

    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (fireball) {
            g2d.setColor(Color.ORANGE);
        } else if (softball) {
            g2d.setColor(Color.GREEN);
        } else {
            g2d.setColor(Color.WHITE);
        }
        g2d.fillOval((int) x, (int) y, (int) w, (int) h);
    }
}
