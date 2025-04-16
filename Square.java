import java.awt.Color;

public class Square {
    private boolean alive;
    private Color color;

    public Square() {
        this.alive = false;
        this.color = null;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void toggle() {
        this.alive = !this.alive;
        this.color = alive 
            ? new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)) 
            : null;
    }
}
