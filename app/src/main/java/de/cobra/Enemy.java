package de.cobra;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Enemy {
    private Rect rectangle;
    private int color;

    public Enemy(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    public Rect getRectangle() {
        return rectangle;
    }
}
