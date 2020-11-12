package arkanoid;

import arkanoid.screens.ScreenManager;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.FileInputStream;
import java.io.IOException;

public class Application implements Runnable {
    public static BufferStrategy bs;
    public static Graphics g;
    public static int width;
    public static int height;
    public static Window window;
    public static Font font = null;
    public static Font smallFont = null;
    private boolean isRunning = false;
    private long lastFrameTime;
    private float deltaTime;
    private String title;

    public Application(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        ScreenManager.setCurrentScreen(ScreenManager.MENU);
        window = new Window(title, width, height);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("res/ttf/pusab.ttf"));
            font = font.deriveFont(14F);
            smallFont = font.deriveFont(11F);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render() {
        bs = window.getCanvas().getBufferStrategy();
        if (bs == null) {
            window.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        ScreenManager.getCurrentScreen().render(g);
        bs.show();
        g.dispose();
        Toolkit.getDefaultToolkit().sync();

    }

    public void run() {
        isRunning = true;
        while (isRunning) {
            long currentTime = System.nanoTime();
            deltaTime = (currentTime - lastFrameTime) / 1e9f;
            ScreenManager.getCurrentScreen().update(deltaTime);
            Input.update();
            render();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lastFrameTime = currentTime;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
