package de.cobra;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameEngine gameEngine;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameEngine = new GameEngine(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameEngine.setRunning(true);
        gameEngine.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameEngine.setRunning(false);
        try {
            gameEngine.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Zeichne das Spielfeld
    }
}
