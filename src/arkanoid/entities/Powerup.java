package arkanoid.entities;

import arkanoid.Rectangle;
import arkanoid.screens.Game;

import java.awt.*;

public class Powerup extends Entity {

    public static final int INCREASE_PADDLE_LENGTH = 0;
    public static final int DECREASE_PADDLE_LENGTH = 1;
    public static final int INCREASE_BALL_SPEED = 2;
    public static final int DECREASE_BALL_SPEED = 3;
    public static final int FIREBALL = 4;
    public static final int SOFTBALL = 5;
    public static final int EXTRA_LIFE = 6;
    public static final int LOSE_LIFE = 7;
    public static final int STICKY = 8;
    public static final int SHIELD = 9;
    public static final int LASER = 10;

    public static final int[] TYPES = {
            INCREASE_PADDLE_LENGTH,
            DECREASE_PADDLE_LENGTH,
            INCREASE_BALL_SPEED,
            DECREASE_BALL_SPEED,
            FIREBALL,
            SOFTBALL,
            EXTRA_LIFE,
            LOSE_LIFE,
            STICKY,
            SHIELD,
            LASER
    };
    private static final int SIZE = 16;
    private static long fireBallTimer = 0;
    private static long softBallTimer = 0;
    private static long laserTimer = 0;
    private static long stickyTimer = 0;
    private static long shieldTimer = 0;
    public int type;
    public boolean toBeRemoved = false;

    public Powerup(float x, float y, int type) {
        super(x, y, SIZE, SIZE);
        this.type = type;
    }

    public static void increasePaddleLength() {
        Game.player.increaseLength();
    }

    public static void decreasePaddleLength() {
        Game.player.decreaseLength();
    }

    public static void increaseBallSpeed() {
        Game.ball.dx = Game.ball.dx * 1.25f;
        Game.ball.dy = Game.ball.dy * 1.25f;
    }

    public static void decreaseBallSpeed() {
        Game.ball.dx = Game.ball.dx * 0.75f;
        Game.ball.dy = Game.ball.dy * 0.75f;
    }

    public static void fireBall() {
        Game.ball.fireball(true);
        fireBallTimer = System.currentTimeMillis();
        softBallTimer = 0;
    }

    public static void softBall() {
        Game.ball.softball(true);
        softBallTimer = System.currentTimeMillis();
        fireBallTimer = 0;
    }

    public static void shield() {
        Game.shield(true);
        shieldTimer = System.currentTimeMillis();
    }

    public static void laser() {
        Game.player.laser(true);
        laserTimer = System.currentTimeMillis();
    }

    public static void sticky() {
        Game.player.sticky(true);
        stickyTimer = System.currentTimeMillis();
    }

    public static void extraLife() {
        Game.player.lives++;
    }

    public static void loseLife() {
        Game.player.lives--;
        if (Game.player.lives <= 0) {
            Game.getInstance().die();
        }
    }

    public static void staticUpdate() {
        if (fireBallTimer != 0 && System.currentTimeMillis() - fireBallTimer > 10000) {
            fireBallTimer = 0;
            Game.ball.fireball(false);
        }
        if (softBallTimer != 0 && System.currentTimeMillis() - softBallTimer > 10000) {
            softBallTimer = 0;
            Game.ball.softball(false);
        }
        if (shieldTimer != 0 && System.currentTimeMillis() - shieldTimer > 15000) {
            shieldTimer = 0;
            Game.shield(false);
        }
        if (laserTimer != 0 && System.currentTimeMillis() - laserTimer > 15000) {
            laserTimer = 0;
            Game.player.laser(false);
        }
        if (stickyTimer != 0 && System.currentTimeMillis() - stickyTimer > 15000) {
            stickyTimer = 0;
            Game.player.sticky(false);
        }
    }

    public static void reset() {
        fireBallTimer = 0;
        softBallTimer = 0;
        shieldTimer = 0;
        laserTimer = 0;
        stickyTimer = 0;
        Game.ball.fireball(false);
        Game.ball.softball(false);
        Game.shield(false);
        Game.player.laser(false);
        Game.player.sticky(false);
    }

    @Override
    public void update(float dt) {
        y += 80 * dt;
        Rectangle playerRect = new Rectangle(Game.player.x, Game.player.y, Player.WIDTH, Player.HEIGHT);
        Rectangle rect = new Rectangle(x, y, SIZE, SIZE);
        if (playerRect.intersects(rect)) {
            switch (type) {
                case INCREASE_PADDLE_LENGTH:
                    increasePaddleLength();
                    break;
                case DECREASE_PADDLE_LENGTH:
                    decreasePaddleLength();
                    break;
                case INCREASE_BALL_SPEED:
                    increaseBallSpeed();
                    break;
                case DECREASE_BALL_SPEED:
                    decreaseBallSpeed();
                    break;
                case FIREBALL:
                    fireBall();
                    break;
                case SOFTBALL:
                    softBall();
                    break;
                case EXTRA_LIFE:
                    extraLife();
                    break;
                case LOSE_LIFE:
                    loseLife();
                    break;
                case SHIELD:
                    shield();
                    break;
                case LASER:
                    laser();
                    break;
                case STICKY:
                    sticky();
                    break;
                default:
                    break;
            }
            toBeRemoved = true;
        }
    }

    @Override
    public void render(Graphics g) {
        int[] xs = {(int) x, (int) x + SIZE, (int) x + SIZE / 2};
        int[] ys = {(int) y + SIZE, (int) y + SIZE, (int) y};
        switch (type) {
            case INCREASE_PADDLE_LENGTH:
                g.setColor(Color.BLUE);
                break;
            case DECREASE_PADDLE_LENGTH:
                g.setColor(Color.YELLOW);
                break;
            case INCREASE_BALL_SPEED:
                g.setColor(Color.ORANGE);
                break;
            case DECREASE_BALL_SPEED:
                g.setColor(Color.CYAN);
                break;
            case FIREBALL:
                g.setColor(Color.RED);
                break;
            case SOFTBALL:
                g.setColor(Color.DARK_GRAY);
                break;
            case SHIELD:
                g.setColor(Color.MAGENTA);
                break;
            case EXTRA_LIFE:
                g.setColor(Color.WHITE);
                break;
            case LOSE_LIFE:
                g.setColor(Color.BLACK);
                break;
            case LASER:
                g.setColor(Color.PINK);
                break;
            case STICKY:
                g.setColor(Color.GREEN);
                break;
        }
        g.fillPolygon(xs, ys, 3);
    }
}
