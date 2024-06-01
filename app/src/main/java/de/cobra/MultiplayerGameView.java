package de.cobra;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MultiplayerGameView extends SurfaceView implements SurfaceHolder.Callback {
    private MultiplayerGameEngine multiplayerGameEngine;

    public MultiplayerGameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        multiplayerGameEngine = new MultiplayerGameEngine(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        multiplayerGameEngine.setRunning(true);
        multiplayerGameEngine.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        multiplayerGameEngine.setRunning(false);
        try {
            multiplayerGameEngine.join();
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
