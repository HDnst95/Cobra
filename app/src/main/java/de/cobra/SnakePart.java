package de.cobra;

public class SnakePart {
    public int x, y;
    private int direction;

    public SnakePart(int x, int y) {
        this.x = x;
        this.y = y;
        this.direction = 0; // Initial direction, placeholder
    }

    public void move() {
        switch (direction) {
            case 0: // up
                y -= 20;
                break;
            case 1: // right
                x += 20;
                break;
            case 2: // down
                y += 20;
                break;
            case 3: // left
                x -= 20;
                break;
        }
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
