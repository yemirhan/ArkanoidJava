package arkanoid.screens;

import arkanoid.Application;
import arkanoid.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Menu implements Screen {

    private static final String[] options = {
            "New Game",
            "Options",
            "High Scores",
            "Help",
            "About",
            "Exit"
    };

    private static Button[] buttons = new Button[6];

    @Override
    public void update(float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            ScreenManager.setCurrentScreen(ScreenManager.GAME);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(Application.font);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].render(g);
        }
    }

    @Override
    public void restart() {
        int startY = 100;
        int textHeight = 35;
        buttons = new Button[]{
                new Button("New Game", startY, 150, () -> ScreenManager.setCurrentScreen(ScreenManager.GAME)),
                new Button("Options", startY + textHeight, 150, () -> ScreenManager.setCurrentScreen(ScreenManager.OPTIONS)),
                new Button("High Scores", startY + 2 * textHeight, 150, () -> ScreenManager.setCurrentScreen(ScreenManager.HIGHSCORES)),
                new Button("Help", startY + 3 * textHeight, 150, () -> ScreenManager.setCurrentScreen(ScreenManager.HELP)),
                new Button("About", startY + 4 * textHeight, 150, () -> {
                    JOptionPane.showMessageDialog(null, "Omer Afsin Pirim\n20150702048\nomerafsin.pirim@std.yeditepe.edu.tr");
                }),
                new Button("Exit", startY + 5 * textHeight, 150, () -> System.exit(0))
        };
    }
}
