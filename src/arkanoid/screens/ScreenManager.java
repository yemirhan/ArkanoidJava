package arkanoid.screens;

public class ScreenManager {

    public static final Screen MENU = new Menu();
    public static final Screen GAME = Game.getInstance();
    public static final Screen HIGHSCORES = new HighScores();
    public static final Screen HELP = new Help();
    public static final Screen OPTIONS = new Options();
    public static final Screen NULL = null;

    private static Screen currentScreen;

    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    public static void setCurrentScreen(Screen screen) {
        currentScreen = screen;
        currentScreen.restart();
    }

}
