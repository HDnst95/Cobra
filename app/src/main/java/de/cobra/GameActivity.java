package de.cobra;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    private TextView tvScore;
    private TextView tvLives;
    private Button btnPause;

    private SharedPreferences prefs;
    private int playerRating;
    private static final String PREFS_NAME = "game_prefs";
    private static final String KEY_ELO_RATING = "elo_rating";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        playerRating = prefs.getInt(KEY_ELO_RATING, 1000);

        gameView = findViewById(R.id.gameView);
        tvScore = findViewById(R.id.tvScore);
        tvLives = findViewById(R.id.tvLives);
        btnPause = findViewById(R.id.btnPause);

        btnPause.setOnClickListener(v -> {
            if (gameView.isPaused()) {
                gameView.resumeGame();
                btnPause.setText("Pause");
            } else {
                gameView.pauseGame();
                btnPause.setText("Resume");
            }
        });

        gameView.setGameOverListener(new GameView.GameOverListener() {
            @Override
            public void onGameOver(boolean hasWon) {
                int opponentRating = 1200; // Beispielwert für den Gegner, sollte angepasst werden
                playerRating = EloRating.calculateNewRating(playerRating, opponentRating, hasWon);
                prefs.edit().putInt(KEY_ELO_RATING, playerRating).apply();
                saveHighscore("Player", gameView.getScore());
            }
        });

        gameView.startGame();
    }

    private void saveHighscore(String player, int score) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String highscoreString = prefs.getString("highscores", "");
        highscoreString += player + "," + score + ";";
        prefs.edit().putString("highscores", highscoreString).apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pauseGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resumeGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameView.stopGame();
    }
}
