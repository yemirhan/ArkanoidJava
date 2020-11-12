package arkanoid.screens;

import java.awt.*;

public interface Screen {
    void update(float dt);

    void render(Graphics g);

    void restart();
}
