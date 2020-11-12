package arkanoid;

import javax.swing.*;
import java.awt.*;

public class Window {
    public JFrame frame;
    private String title;
    private int width, height;
    private Canvas canvas;

    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        // Create JFrame
        frame = new JFrame();
        frame.setTitle(title);
        frame.setResizable(false);

        // Create canvas of size width x height
        canvas = new Canvas();
        Dimension size = new Dimension(width, height);
        canvas.setMinimumSize(size);
        canvas.setMaximumSize(size);
        canvas.setPreferredSize(size);
        Input input = new Input();
        canvas.addKeyListener(input);
        canvas.addMouseListener(input);
        canvas.addMouseMotionListener(input);
        canvas.setFocusable(true);

        // Add the canvas to the JFrame and resize it accordingly
        frame.add(canvas);
        frame.pack();

        frame.setLocationRelativeTo(null);
        // Show the window
        frame.setVisible(true);
        // The default is minimize to tray for some reason:
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
