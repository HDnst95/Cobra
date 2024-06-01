package de.cobra;

import android.graphics.Canvas;

public class MultiplayerGameEngine extends Thread {
    private boolean running;
    private MultiplayerGameView multiplayerGameView;

    public MultiplayerGameEngine(MultiplayerGameView multiplayerGameView) {
        this.multiplayerGameView = multiplayerGameView;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = null;
            try {
                canvas = multiplayerGameView.getHolder().lockCanvas();
                synchronized (multiplayerGameView.getHolder()) {
                    multiplayerGameView.onDraw(canvas);
                }
            } finally {
                if (canvas != null) {
                    multiplayerGameView.getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
