package de.cobra;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Player {
    private Rect rectangle;
    private int color;

    public Player(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    public void setPosition(int x, int y) {
        rectangle.set(x - rectangle.width() / 2, y - rectangle.height() / 2,
                x + rectangle.width() / 2, y + rectangle.height() / 2);
    }

    public Rect getRectangle() {
        return rectangle;
    }
}
