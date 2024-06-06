package de.cobra.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private ArrayList<Point> snake;
    private Point food;
    private Paint paint;
    private int direction;
    private boolean running;
    private boolean paused;
    private OnGameOverListener onGameOverListener;

    private static final int DEFAULT_GRID_SIZE = 40;
    private static final int DEFAULT_SPEED = 2;
    private int gridSize = DEFAULT_GRID_SIZE;
    private int speed = DEFAULT_SPEED;

    private static final int MOVE_RIGHT = 0;
    private static final int MOVE_LEFT = 1;
    private static final int MOVE_UP = 2;
    private static final int MOVE_DOWN = 3;

    private float touchX;
    private float touchY;
    private boolean showTouchIndicator;

    private int playableHeight;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        initializeGame();
    }

    private void initializeGame() {
        snake = new ArrayList<>();
        snake.add(new Point(5, 5));
        snake.add(new Point(5, 4));
        snake.add(new Point(5, 3));

        food = new Point(10, 10);

        paint = new Paint();
        direction = MOVE_RIGHT;
        running = true;
        paused = false;
        playableHeight = (int) (getHeight() * 0.8);

        setGridSize(DEFAULT_GRID_SIZE);
        setSpeed(DEFAULT_SPEED);
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
        if (canvas != null) {
            drawGameElements(canvas);
            drawTouchIndicator(canvas);
        }
    }

    private void drawGameElements(Canvas canvas) {
        // Draw game elements such as snake, food, and direction indicators
    }

    private void drawTouchIndicator(Canvas canvas) {
        // Draw touch indicator if needed
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handleTouchEvent(event);
        return true;
    }

    private void handleTouchEvent(MotionEvent event) {
        // Handle touch events to change direction or show touch indicator
    }

    public void update() {
        if (!running || paused) return;

        moveSnake();
        checkCollision();
    }

    private void moveSnake() {
        // Move the snake based on the current direction
    }

    private void checkCollision() {
        // Check for collisions with walls, snake body, and food
    }

    private void generateFood() {
        // Generate new food location
    }

    public void setOnGameOverListener(OnGameOverListener listener) {
        this.onGameOverListener = listener;
    }

    public void setGridSize(int size) {
        gridSize = size;
        initializeGame();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        thread.setFPS(speed * 10);
    }

    public void pauseGame() {
        paused = true;
    }

    public void resumeGame() {
        paused = false;
    }

    public interface OnGameOverListener {
        void onGameOver();
    }
}
