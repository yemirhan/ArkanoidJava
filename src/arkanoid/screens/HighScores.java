package arkanoid.screens;

import arkanoid.Application;
import arkanoid.Input;
import arkanoid.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class HighScores implements Screen {

    private static ArrayList<HighScore> scores = new ArrayList<>();

    public HighScores() {

    }

    public static void newScore() {
        loadFromFile();
        String name = JOptionPane.showInputDialog(Application.window.frame, "Enter your name:", "New high score", JOptionPane.PLAIN_MESSAGE);
        scores.add(new HighScore(name, Game.score, new Date()));
        scores.sort((score1, score2) -> {
            return score2.score - score1.score;
        });
        saveToFile();
    }

    private static void loadFromFile() {
        scores = new ArrayList<>();
        File file = new File("res/highscore.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                scores.add(new HighScore(tokens[0], tokens[1], tokens[2]));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scores.sort(Comparator.comparingInt(score -> -score.score));
    }

    private static void saveToFile() {
        File file = new File("res/highscore.txt");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write("");
            for (HighScore score : scores) {
                bw.append(score.toString() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(Application.font);
        g.drawString("High Scores", 10, 100 - g.getFontMetrics(Application.font).getHeight());

        int numScores = Math.min(10, scores.size());
        int textHeight = g.getFontMetrics(Application.smallFont).getHeight();
        int textWidth = g.getFontMetrics(Application.font).stringWidth("Back to menu");
        int x = (Main.WIDTH - 160) / 2;
        int y = 100 + numScores * 2 * textHeight + 10;
        int w = 165;
        int h = textHeight + 5;
        int mouseX = Input.getMouseX();
        int mouseY = Input.getMouseY();
        if (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h) {
            g.setColor(Color.LIGHT_GRAY);
            if (Input.isMouseButtonDown(MouseEvent.BUTTON1)) {
                g.setColor(Color.DARK_GRAY);
            }
            if (Input.isMouseClicked(MouseEvent.BUTTON1)) {
                ScreenManager.setCurrentScreen(ScreenManager.MENU);
            }
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillRect(x, y, w, h);
        g.setColor(Color.BLACK);
        g.drawString("Back to menu", (Main.WIDTH - textWidth) / 2, y + textHeight);


        g.setColor(Color.WHITE);
        g.setFont(Application.smallFont);
        for (int i = 0; i < Math.min(10, scores.size()); i++) {
            int textHeight2 = g.getFontMetrics(Application.smallFont).getHeight();
            g.setColor(Color.WHITE);
            g.drawString(scores.get(i).name + ": " + scores.get(i).score, 10, 100 + i * 2 * textHeight2);
            g.setColor(Color.GRAY);
            g.drawString(" [" + scores.get(i).date.toString() + "]", 10, 100 + i * 2 * textHeight2 + textHeight2);
        }

        g.setFont(Application.font);
    }

    @Override
    public void restart() {
        loadFromFile();
    }

    private static class HighScore {
        public String name;
        public int score;
        public Date date;

        public HighScore(String name, int score, Date date) {
            this.name = name;
            this.score = score;
            this.date = date;
        }

        public HighScore(String name, String score, String date) {
            this(name, Integer.parseInt(score), new Date(Long.parseLong(date)));
        }

        public String toString() {
            return this.name + "," + this.score + "," + this.date.getTime();
        }
    }
}
