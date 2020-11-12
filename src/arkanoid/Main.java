package arkanoid;

public class Main {
    public static final int WIDTH = 352;
    public static final int HEIGHT = 576;

    public static void main(String[] args) {
        Application game = new Application("Arkanoid", WIDTH, HEIGHT);
        game.run();
    }
}
