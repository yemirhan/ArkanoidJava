package arkanoid.screens;

import arkanoid.Application;
import arkanoid.Main;

import javax.swing.*;
import java.awt.*;

public class Options implements Screen {

    public static String difficulty = "Novice";

    private String[] difficultyOptions = {
            "Novice",
            "Intermediate",
            "Advanced",
    };

    private Button button1 = new Button(difficulty, 100, 165, () -> {
        difficulty = (String) JOptionPane.showInputDialog(Application.window.frame, "Select difficulty:", "Difficulty", JOptionPane.QUESTION_MESSAGE, null, difficultyOptions, difficulty);
        System.out.println(difficulty);
    });

    private Button button2 = new Button("Back to menu", 150, 165, () -> ScreenManager.setCurrentScreen(ScreenManager.MENU));

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(Graphics g) {
        int textWidth = g.getFontMetrics(Application.font).stringWidth("Difficulty") + 10;
        int textHeight = g.getFontMetrics(Application.font).getHeight();
        int x = (Main.WIDTH - textWidth) / 2;
        g.setFont(Application.font);
        g.setColor(Color.WHITE);
        g.drawString("Difficulty", x, 100 - textHeight - 5);
        button1.text = difficulty;
        button1.render(g);
        button2.render(g);
    }

    @Override
    public void restart() {

    }
}
