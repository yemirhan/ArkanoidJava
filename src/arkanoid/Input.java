package arkanoid;

import java.awt.event.*;
import java.util.Arrays;

public class Input extends KeyAdapter implements MouseListener, MouseMotionListener {

    private static boolean[] keys = new boolean[65536];
    private static boolean[] previousKeys = new boolean[65536];
    private static boolean[] mouseButtons = new boolean[255];
    private static boolean[] previousMouseButtons = new boolean[255];
    private static boolean[] clickedButtons = new boolean[255];
    private static boolean[] previousClickedButtons = new boolean[255];
    private static int mouseX;
    private static int mouseY;

    public static boolean isKeyDown(int key) {
        return keys[key];
    }

    public static boolean isKeyPressed(int key) {
        return keys[key] && !previousKeys[key];
    }

    public static boolean isKeyReleased(int key) {
        return !keys[key] && previousKeys[key];
    }

    public static boolean isMouseButtonDown(int button) {
        return mouseButtons[button];
    }

    public static boolean isMouseClicked(int button) {
        return previousClickedButtons[button];
    }

    public static boolean isMouseReleased(int button) {
        return !mouseButtons[button] && previousMouseButtons[button];
    }

    public static int getMouseX() {
        return mouseX;
    }

    public static int getMouseY() {
        return mouseY;
    }

    public static void update() {
        System.arraycopy(keys, 0, previousKeys, 0, keys.length);
        System.arraycopy(mouseButtons, 0, previousMouseButtons, 0, mouseButtons.length);
        System.arraycopy(clickedButtons, 0, previousClickedButtons, 0, clickedButtons.length);
        Arrays.fill(clickedButtons, false);
    }

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        clickedButtons[mouseEvent.getButton()] = true;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        mouseButtons[mouseEvent.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        mouseButtons[mouseEvent.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseX = mouseEvent.getX();
        mouseY = mouseEvent.getY();
        mouseButtons[mouseEvent.getButton()] = true;
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseX = mouseEvent.getX();
        mouseY = mouseEvent.getY();
    }
}
