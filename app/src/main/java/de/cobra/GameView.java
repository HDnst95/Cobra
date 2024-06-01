package de.cobra;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Player player;
    private Enemy enemy;
    private Paint paint;
    private OnGameOverListener onGameOverListener;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        player = new Player(new Rect(100, 100, 200, 200), Color.BLUE);
        enemy = new Enemy(new Rect(300, 300, 400, 400), Color.RED);

        paint = new Paint();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        player.draw(canvas);
        enemy.draw(canvas);

        // Check for collision
        if (Rect.intersects(player.getRectangle(), enemy.getRectangle())) {
            // Handle collision
            paint.setColor(Color.BLACK);
            paint.setTextSize(100);
            canvas.drawText("Game Over", getWidth() / 2 - 200, getHeight() / 2, paint);
            thread.setRunning(false);
            if (onGameOverListener != null) {
                onGameOverListener.onGameOver();
            }
        }
    }

    @Override
    public boolean onTouchEvent(android.view.MotionEvent event) {
        switch (event.getAction()) {
            case android.view.MotionEvent.ACTION_DOWN:
            case android.view.MotionEvent.ACTION_MOVE:
                player.setPosition((int) event.getX(), (int) event.getY());
                break;
        }
        return true;
    }

    public void setOnGameOverListener(OnGameOverListener listener) {
        this.onGameOverListener = listener;
    }

    public interface OnGameOverListener {
        void onGameOver();
    }
}
