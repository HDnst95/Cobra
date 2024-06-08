package de.cobra;

import java.util.Random;

public class Food {
    public int x, y;

    public Food() {
        x = 0;
        y = 0;
    }

    public void spawn(int screenX, int screenY) {
        Random random = new Random();
        if (screenX > 0 && screenY > 0) {  // Sicherstellen, dass screenX und screenY positive Werte haben
            x = random.nextInt(screenX / 20) * 20;
            y = random.nextInt(screenY / 20) * 20;
        }
    }
}
