package de.cobra;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread gameThread;
    private boolean isPlaying;
    private boolean isPaused;
    private SurfaceHolder holder;
    private Paint paint;
    private int screenX, screenY;
    private List<SnakePart> snake;
    private Food food;
    private int score;
    private int lives;
    private GameOverListener gameOverListener;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        paint = new Paint();
        snake = new ArrayList<>();
        food = new Food();
        score = 0;
        lives = 3;
        isPaused = false;

        // Initialize the snake
        SnakePart head = new SnakePart(0, 0);
        snake.add(head);

        // Add a callback to get the screen dimensions when the surface is created
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                screenX = getWidth();
                screenY = getHeight();
                resetGame();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                screenX = width;
                screenY = height;
                resetGame();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                isPlaying = false;
            }
        });
    }

    @Override
    public void run() {
        while (isPlaying) {
            if (!isPaused) {
                update();
                draw();
                control();
            }
        }
    }

    private void update() {
        // Update the position of the snake and check for collisions
        SnakePart head = snake.get(0);
        head.move();

        // Check if the snake eats the food
        if (head.x == food.x && head.y == food.y) {
            score++;
            food.spawn(screenX, screenY);
            SnakePart newPart = new SnakePart(head.x, head.y);
            snake.add(newPart);
        }

        // Check for collisions with the wall
        if (head.x < 0 || head.x >= screenX || head.y < 0 || head.y >= screenY) {
            lives--;
            if (lives == 0) {
                isPlaying = false;
                if (gameOverListener != null) {
                    gameOverListener.onGameOver(false);
                }
            } else {
                resetGame();
            }
        }

        // Check for collisions with itself
        for (int i = 1; i < snake.size(); i++) {
            SnakePart part = snake.get(i);
            if (head.x == part.x && head.y == part.y) {
                lives--;
                if (lives == 0) {
                    isPlaying = false;
                    if (gameOverListener != null) {
                        gameOverListener.onGameOver(false);
                    }
                } else {
                    resetGame();
                }
                break;
            }
        }
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.GREEN);
            for (SnakePart part : snake) {
                canvas.drawRect(part.x, part.y, part.x + 20, part.y + 20, paint);
            }
            paint.setColor(Color.RED);
            canvas.drawRect(food.x, food.y, food.x + 20, food.y + 20, paint);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pauseGame() {
        isPaused = true;
    }

    public void resumeGame() {
        isPaused = false;
        synchronized (this) {
            notify();
        }
    }

    public void resetGame() {
        snake.clear();
        SnakePart head = new SnakePart(screenX / 2, screenY / 2);
        snake.add(head);
        food.spawn(screenX, screenY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Handle touch events for snake control
                // This is a placeholder for touch control logic
                break;
        }
        return true;
    }

    public void startGame() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGame() {
        isPlaying = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setGameOverListener(GameOverListener listener) {
        this.gameOverListener = listener;
    }

    public int getScore() {
        return score;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public interface GameOverListener {
        void onGameOver(boolean hasWon);
    }
}
