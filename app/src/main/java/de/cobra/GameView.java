package de.cobra;

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
    private static final int DEFAULT_SPEED = 3;
    private int gridSize = DEFAULT_GRID_SIZE;
    private int speed = DEFAULT_SPEED;

    private static final int MOVE_RIGHT = 0;
    private static final int MOVE_LEFT = 1;
    private static final int MOVE_UP = 2;
    private static final int MOVE_DOWN = 3;

    private float touchX;
    private float touchY;
    private boolean showTouchIndicator;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        initializeGame();
    }

    public void initializeGame() {
        snake = new ArrayList<>();
        snake.add(new Point(5, 5));
        snake.add(new Point(4, 5));
        snake.add(new Point(3, 5));

        food = new Point(10, 10);

        paint = new Paint();
        direction = MOVE_RIGHT;
        running = true;
        paused = false;
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
            canvas.drawColor(Color.WHITE);

            // Zeichne die Richtungszonen
            paint.setColor(Color.TRANSPARENT);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            canvas.drawRect(0, 0, getWidth(), getHeight() / 3, paint); // Oben
            canvas.drawRect(0, 2 * getHeight() / 3, getWidth(), getHeight(), paint); // Unten
            canvas.drawRect(0, getHeight() / 3, getWidth() / 3, 2 * getHeight() / 3, paint); // Links
            canvas.drawRect(2 * getWidth() / 3, getHeight() / 3, getWidth(), 2 * getHeight() / 3, paint); // Rechts

            // Zeichne die Pfeile
            paint.setColor(Color.LTGRAY);
            paint.setStyle(Paint.Style.FILL);
            paint.setAlpha(100); // Setze Transparenz

            // Pfeil nach oben
            Path pathUp = new Path();
            pathUp.moveTo(getWidth() / 2, getHeight() / 6);
            pathUp.lineTo(getWidth() / 2 - 50, getHeight() / 3 - 50);
            pathUp.lineTo(getWidth() / 2 + 50, getHeight() / 3 - 50);
            pathUp.close();
            canvas.drawPath(pathUp, paint);

            // Pfeil nach unten
            Path pathDown = new Path();
            pathDown.moveTo(getWidth() / 2, 5 * getHeight() / 6);
            pathDown.lineTo(getWidth() / 2 - 50, 2 * getHeight() / 3 + 50);
            pathDown.lineTo(getWidth() / 2 + 50, 2 * getHeight() / 3 + 50);
            pathDown.close();
            canvas.drawPath(pathDown, paint);

            // Pfeil nach links
            Path pathLeft = new Path();
            pathLeft.moveTo(getWidth() / 6, getHeight() / 2);
            pathLeft.lineTo(getWidth() / 3 - 50, getHeight() / 2 - 50);
            pathLeft.lineTo(getWidth() / 3 - 50, getHeight() / 2 + 50);
            pathLeft.close();
            canvas.drawPath(pathLeft, paint);

            // Pfeil nach rechts
            Path pathRight = new Path();
            pathRight.moveTo(5 * getWidth() / 6, getHeight() / 2);
            pathRight.lineTo(2 * getWidth() / 3 + 50, getHeight() / 2 - 50);
            pathRight.lineTo(2 * getWidth() / 3 + 50, getHeight() / 2 + 50);
            pathRight.close();
            canvas.drawPath(pathRight, paint);

            // Zeichne die Schlange
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.FILL);
            paint.setAlpha(255);
            for (Point p : snake) {
                canvas.drawRect(p.x * gridSize, p.y * gridSize, (p.x + 1) * gridSize, (p.y + 1) * gridSize, paint);
            }

            // Zeichne die Nahrung
            paint.setColor(Color.RED);
            canvas.drawRect(food.x * gridSize, food.y * gridSize, (food.x + 1) * gridSize, (food.y + 1) * gridSize, paint);

            // Zeichne den Berührungsindikator
            if (showTouchIndicator) {
                paint.setColor(Color.BLUE);
                canvas.drawCircle(touchX, touchY, 50, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchX = event.getX();
            touchY = event.getY();
            showTouchIndicator = true;

            if (touchY < getHeight() / 3 && direction != MOVE_DOWN) {
                direction = MOVE_UP;
            } else if (touchY > 2 * getHeight() / 3 && direction != MOVE_UP) {
                direction = MOVE_DOWN;
            } else if (touchX < getWidth() / 3 && direction != MOVE_RIGHT) {
                direction = MOVE_LEFT;
            } else if (touchX > 2 * getWidth() / 3 && direction != MOVE_LEFT) {
                direction = MOVE_RIGHT;
            }

            invalidate(); // Aktualisiere die Ansicht, um den Berührungsindikator anzuzeigen
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            showTouchIndicator = false;
            invalidate(); // Aktualisiere die Ansicht, um den Berührungsindikator zu entfernen
        }
        return true;
    }

    public void update() {
        if (!running || paused) return;

        Point head = snake.get(0);
        Point newHead = new Point(head.x, head.y);

        switch (direction) {
            case MOVE_RIGHT:
                newHead.x++;
                break;
            case MOVE_LEFT:
                newHead.x--;
                break;
            case MOVE_UP:
                newHead.y--;
                break;
            case MOVE_DOWN:
                newHead.y++;
                break;
        }

        if (newHead.equals(food)) {
            snake.add(0, newHead);
            generateFood();
        } else {
            for (int i = snake.size() - 1; i > 0; i--) {
                snake.set(i, snake.get(i - 1));
            }
            snake.set(0, newHead);
        }

        if (checkCollision(newHead)) {
            running = false;
            if (onGameOverListener != null) {
                onGameOverListener.onGameOver();
            }
        }
    }

    private void generateFood() {
        Random random = new Random();
        food.set(random.nextInt(getWidth() / gridSize), random.nextInt(getHeight() / gridSize));
    }

    private boolean checkCollision(Point head) {
        if (head.x < 0 || head.x >= getWidth() / gridSize || head.y < 0 || head.y >= getHeight() / gridSize) {
            return true;
        }

        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(i).equals(head)) {
                return true;
            }
        }

        return false;
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
        thread.setFPS(speed * 10); // Beispiel: Geschwindigkeit anpassen
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
