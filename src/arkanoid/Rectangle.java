package arkanoid;

public class Rectangle {
    public float x, y, w, h;

    public Rectangle(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    boolean valueInRange(float value, float min, float max) {
        return (value >= min) && (value <= max);
    }

    public boolean intersects(Rectangle other) {
        return !(this.x + this.w < other.x || this.y + this.h < other.y || this.x > other.x + other.w || this.y > other.y + other.h);
    }
}
