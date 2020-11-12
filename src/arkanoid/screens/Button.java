package arkanoid.screens;

import arkanoid.Application;
import arkanoid.Input;
import arkanoid.Main;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Button {
    public String text;
    public int y;
    public int width;
    public Runnable callback;

    public Button(String text, int y, int width, Runnable callback) {
        this.text = text;
        this.y = y;
        this.width = width;
        this.callback = callback;
    }

    public void update() {

    }

    public void render(Graphics g) {
        int textWidth = g.getFontMetrics(Application.font).stringWidth(text) + 10;
        int textHeight = g.getFontMetrics(Application.font).getHeight();
        int x = (Main.WIDTH - width) / 2;
        int y = this.y - textHeight;
        int w = width;
        int h = textHeight + 5;
        int textX = (Main.WIDTH - textWidth) / 2;
        g.setFont(Application.font);
        int mouseX = Input.getMouseX();
        int mouseY = Input.getMouseY();
        if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + h) {
            g.setColor(Color.LIGHT_GRAY);
            if (Input.isMouseButtonDown(MouseEvent.BUTTON1)) {
                g.setColor(Color.DARK_GRAY);
            }
            if (Input.isMouseClicked(MouseEvent.BUTTON1)) {
                callback.run();
            }
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillRect(x, y, width, textHeight + 5);
        g.setColor(Color.BLACK);
        g.drawString(text, textX + 5, y + textHeight);
    }
}
