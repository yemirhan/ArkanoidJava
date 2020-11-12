package arkanoid.screens;

import arkanoid.*;
import arkanoid.entities.Ball;
import arkanoid.entities.Brick;
import arkanoid.entities.Player;
import arkanoid.entities.Powerup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Game implements Screen {

    public static final int WIDTH = 352;
    public static final int HEIGHT = 512;
    private static final int PLAYING = 0;
    private static final int PAUSED = 1;
    private static final int DEATHSCREEN = 2;
    private static final int LEVELCHANGE = 3;
    private static final int DEATHSCREEN_TIME = 1000;
    public static Player player;
    public static Ball ball;
    public static int score = 0;
    public static ArrayList<Brick> bricks;
    public static ArrayList<Powerup> powerups;
    public static boolean shield = false;
    private static Image heartIcon;
    private static int level = -1;
    private static String[][] map;
    private static Game instance = null;
    private static int state = PLAYING;
    private static long stateTimer = System.currentTimeMillis();

    public Game() {
        player = new Player();
        ball = new Ball();
        bricks = new ArrayList<>();
        powerups = new ArrayList<>();
        switch (Options.difficulty) {
            case "Novice":
                map = Levels.map1;
                break;
            case "Intermediate":
                map = Levels.map2;
                break;
            case "Advanced":
                map = Levels.map3;
                break;
        }
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (!map[y][x].equals("0")) {
                    bricks.add(new Brick(x * Brick.WIDTH, y * Brick.HEIGHT, map[y][x]));
                }
            }
        }
        heartIcon = new ImageIcon("res/heart.png").getImage();
    }

    public static Game getInstance() {
        if (instance == null) instance = new Game();
        return instance;
    }

    public static void hitBrickWithLaser(int i) {
        Brick brick = bricks.get(i);
        boolean remove = false;
        brick.lives--;
        brick.reload();
        if (brick.lives <= 0) {
            remove = true;
            score += 10;
        }
        if (remove) {
            removeBrick(brick);
        }
    }

    public static void hitBrick(int i) {
        Brick brick = bricks.get(i);
        boolean remove = false;
        if (ball.isFireball() && brick.lives <= 2) {
            remove = true;
        } else {
            if (ball.isSoftball()) return;
            brick.lives--;
            brick.reload();
            if (brick.lives <= 0) {
                remove = true;
                score += 10;
            }
        }
        if (remove) {
            removeBrick(brick);
        }
    }

    private static void removeBrick(Brick brick) {
        bricks.remove(brick);
        if (MathUtil.random(0, 1) < 0.2f) {
            int index = (int) MathUtil.random(0, Powerup.TYPES.length);
            int type = Powerup.TYPES[index];
            powerups.add(new Powerup(brick.x, brick.y, type));
        }
    }

    public static void shield(boolean shield) {
        Game.shield = shield;
    }

    public static void setState(int state) {
        Game.state = state;
        stateTimer = System.currentTimeMillis();
    }

    @Override
    public void restart() {
        setState(PLAYING);
        player.restart();
        player.lives = 3;
        ball.restart();
        bricks.clear();
        powerups.clear();
        Powerup.reset();
        switch (Options.difficulty) {
            case "Novice":
                map = Levels.map1;
                level = 0;
                break;
            case "Intermediate":
                map = Levels.map2;
                level = 1;
                break;
            case "Advanced":
                map = Levels.map3;
                level = 2;
                break;
        }
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (!map[y][x].equals("0")) {
                    bricks.add(new Brick(x * Brick.WIDTH, y * Brick.HEIGHT, map[y][x]));
                }
            }
        }
    }

    public void nextLevel() {
        level++;
        map = Levels.maps[level];
        setState(PLAYING);
        player.restart();
        ball.restart();
        bricks.clear();
        powerups.clear();
        Powerup.reset();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (!map[y][x].equals("0")) {
                    bricks.add(new Brick(x * Brick.WIDTH, y * Brick.HEIGHT, map[y][x]));
                }
            }
        }
    }

    public void die() {
        player.lives--;
        if (player.lives <= 0) {
            HighScores.newScore();
            ScreenManager.setCurrentScreen(ScreenManager.MENU);
        } else {
            setState(DEATHSCREEN);
        }
    }

    @Override
    public void update(float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_Q)) {
            ScreenManager.setCurrentScreen(ScreenManager.MENU);
        }
        if (state == PLAYING) {
            if (level == -1) {
                nextLevel();
                return;
            }
            if (Input.isKeyPressed(KeyEvent.VK_R)) {
                restart();
            }
            if (Input.isKeyPressed(KeyEvent.VK_N)) {
                setState(LEVELCHANGE);
            }
            if (Input.isKeyPressed(KeyEvent.VK_UP) || Input.isMouseClicked(MouseEvent.BUTTON1)) {
                ((Ball) ball).shoot();
            }
            if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
                setState(PAUSED);
            }
            player.update(dt);
            ball.update(dt);
            for (int i = powerups.size() - 1; i >= 0; i--) {
                powerups.get(i).update(dt);
                if (powerups.get(i).toBeRemoved) {
                    powerups.remove(i);
                }
            }
            Powerup.staticUpdate();
            boolean foundBreakableBrick = false;
            for (Brick brick : bricks) {
                if (brick.lives <= 2) {
                    foundBreakableBrick = true;
                    break;
                }
            }
            if (!foundBreakableBrick) {
                setState(LEVELCHANGE);
            }
        } else if (state == PAUSED) {
            if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
                setState(PLAYING);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(0.0f, 0.5f, 0.5f, 0.3f));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        if (state == PLAYING || state == PAUSED) {
            player.render(g);
            ball.render(g);
            if (shield) {
                g.setColor(new Color(1, 1, 1, 0.5f));
                g.fillRect(0, HEIGHT - 32, WIDTH, 16);
            }
            for (Brick brick : bricks) {
                brick.render(g);
            }
            for (Powerup powerup : powerups) {
                powerup.render(g);
            }

            // Draw the UI
            g.setColor(Color.BLACK);
            g.fillRect(0, HEIGHT, WIDTH, Main.HEIGHT - HEIGHT);
            g.setFont(Application.font);
            g.setColor(new Color(0.0f, 0.5f, 0.5f, 0.5f));
            g.drawString("Score", 10, HEIGHT + Application.font.getSize() + 10);
            g.drawString("Level", 130, HEIGHT + Application.font.getSize() + 10);
            g.drawString("Lives", 230, HEIGHT + Application.font.getSize() + 10);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(score), 10, HEIGHT + Application.font.getSize() * 2 + 18);
            g.drawString(String.valueOf(level + 1), 130, HEIGHT + Application.font.getSize() * 2 + 18);
            for (int i = 0; i < player.lives; i++) {
                g.drawImage(heartIcon, 230 + i * 16, HEIGHT + Application.font.getSize() * 2, null);
            }
            if (state == PAUSED) {
                int w = g.getFontMetrics(Application.font).stringWidth("PAUSED");
                g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
                g.fillRect(0, 0, WIDTH, HEIGHT);
                g.setColor(Color.WHITE);
                g.drawString("PAUSED", (WIDTH - w) / 2, HEIGHT / 2);
            }
        } else if (state == DEATHSCREEN) {
            g.setFont(Application.font);
            g.setColor(Color.WHITE);
            g.drawString("Lives", 100, 100);
            for (int i = 0; i < player.lives; i++) {
                g.drawImage(heartIcon, 100 + i * 16, 110, null);
            }
            if (System.currentTimeMillis() - stateTimer > DEATHSCREEN_TIME) {
                setState(PLAYING);
                player.restart();
                ball.restart();
                powerups.clear();
                Powerup.reset();
            }
        } else if (state == LEVELCHANGE) {
            if (level == 5) {
                HighScores.newScore();
                ScreenManager.setCurrentScreen(ScreenManager.MENU);
                return;
            }
            g.setFont(Application.font);
            g.setColor(Color.WHITE);
            g.drawString("Level", 100, 100);
            g.drawString(String.valueOf(level + 2), 100, 100 + Application.font.getSize() + 10);
            if (System.currentTimeMillis() - stateTimer > DEATHSCREEN_TIME) {
                nextLevel();
            }
        }
    }

}
