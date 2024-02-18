import java.awt.*;

public class Kula {
    public int x, y, size, xspeed, yspeed;
    public Color color;
    private final int MAX_SPEED = 5;

    public Kula(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
        do {
            xspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
        } while (xspeed==0);
        do {
            yspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
        } while (yspeed==0);
    }

    public void update(int maxWidth, int maxHeight) {
        x += xspeed;
        y += yspeed;
        if (x <= 0 || x + size >= maxWidth) {
            xspeed = -xspeed;
        }
        if (y <= 0 || y + size >= maxHeight) {
            yspeed = -yspeed;
        }
    }
}