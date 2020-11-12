package arkanoid.screens;

import arkanoid.Application;
import arkanoid.Input;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Help implements Screen {

    private Button button = new Button("Back to menu", 450, 165, () -> {
        ScreenManager.setCurrentScreen(ScreenManager.MENU);
    });

    @Override
    public void update(float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            ScreenManager.setCurrentScreen(ScreenManager.MENU);
        }
    }

    public void drawTriangle(Graphics g, int x, int y, Color color) {
        int[] xs = {
                x, x + 16, x + 8
        };
        int[] ys = {
                y + 16, y + 16, y
        };
        g.setColor(color);
        g.fillPolygon(xs, ys, 3);
    }

    @Override
    public void render(Graphics g) {
        int textHeight = g.getFontMetrics(Application.font).getHeight();
        int startY = 50;
        g.setColor(new Color(0, 0.5f, 0.5f, 0.3f));
        g.fillRect(0, 0, Application.width, Application.height);
        g.setColor(Color.CYAN);
        g.setFont(Application.font);
        int textWidth = g.getFontMetrics(Application.font).stringWidth("How to play");
        g.drawString("How to play", (Game.WIDTH - textWidth) / 2, startY + 30);
        g.setColor(Color.WHITE);
        String line1 = "Move the paddle using mouse";
        String line2 = "Click to shoot the ball";
        String line3 = "Break all the bricks to win";
        g.setFont(Application.smallFont);
        textHeight = g.getFontMetrics(Application.smallFont).getHeight();
        textWidth = g.getFontMetrics(Application.smallFont).stringWidth(line1);
        g.drawString(line1, (Game.WIDTH - textWidth) / 2, startY + 50);
        textWidth = g.getFontMetrics(Application.smallFont).stringWidth(line2);
        g.drawString(line2, (Game.WIDTH - textWidth) / 2, startY + 50 + textHeight);
        textWidth = g.getFontMetrics(Application.smallFont).stringWidth(line3);
        g.drawString(line3, (Game.WIDTH - textWidth) / 2, startY + 50 + 2 * textHeight);

        g.setFont(Application.font);
        g.setColor(Color.CYAN);
        textWidth = g.getFontMetrics(Application.font).stringWidth("Powerups");
        g.drawString("Powerups", (Game.WIDTH - textWidth) / 2, startY + 50 + 4 * textHeight);
        drawTriangle(g, 20, startY + 50 + 5 * textHeight, Color.BLUE);
        drawTriangle(g, 20, startY + 50 + 6 * textHeight, Color.YELLOW);
        drawTriangle(g, 20, startY + 50 + 7 * textHeight, Color.CYAN);
        drawTriangle(g, 20, startY + 50 + 8 * textHeight, Color.ORANGE);
        drawTriangle(g, 20, startY + 50 + 9 * textHeight, Color.RED);
        drawTriangle(g, 20, startY + 50 + 10 * textHeight, Color.DARK_GRAY);
        drawTriangle(g, 20, startY + 50 + 11 * textHeight, Color.MAGENTA);
        drawTriangle(g, 20, startY + 50 + 12 * textHeight, Color.WHITE);
        drawTriangle(g, 20, startY + 50 + 13 * textHeight, Color.BLACK);
        drawTriangle(g, 20, startY + 50 + 14 * textHeight, Color.PINK);
        drawTriangle(g, 20, startY + 50 + 15 * textHeight, Color.GREEN);
        g.setColor(Color.WHITE);
        g.setFont(Application.smallFont);
        g.drawString("Increase paddle length", 50, startY + 50 + 5 * textHeight + textHeight - 4);
        g.drawString("Decrease paddle length", 50, startY + 50 + 6 * textHeight + textHeight - 4);
        g.drawString("Increase ball speed", 50, startY + 50 + 7 * textHeight + textHeight - 4);
        g.drawString("Decrease ball speed", 50, startY + 50 + 8 * textHeight + textHeight - 4);
        g.drawString("Fireball", 50, startY + 50 + 9 * textHeight + textHeight - 4);
        g.drawString("Softball", 50, startY + 50 + 10 * textHeight + textHeight - 4);
        g.drawString("Shield", 50, startY + 50 + 11 * textHeight + textHeight - 4);
        g.drawString("Extra life", 50, startY + 50 + 12 * textHeight + textHeight - 4);
        g.drawString("Lose life", 50, startY + 50 + 13 * textHeight + textHeight - 4);
        g.drawString("Laser", 50, startY + 50 + 14 * textHeight + textHeight - 4);
        g.drawString("Sticky", 50, startY + 50 + 15 * textHeight + textHeight - 4);

        button.render(g);
    }

    @Override
    public void restart() {

    }
}
