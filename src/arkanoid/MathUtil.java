package arkanoid;

public class MathUtil {

    public static float random(float min, float max) {
        return min + (float) Math.random() * (max - min);
    }

    public static float lerp(float v0, float v1, float t) {
        return (1 - t) * v0 + t * v1;
    }
}
