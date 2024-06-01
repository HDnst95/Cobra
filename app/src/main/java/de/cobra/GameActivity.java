package de.cobra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private int score = 0;
    private int lives = 3;
    private int eloPoints = 1500;

    private TextView scoreTextView;
    private TextView livesTextView;
    private TextView eloTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        scoreTextView = findViewById(R.id.score_text);
        livesTextView = findViewById(R.id.lives_text);
        eloTextView = findViewById(R.id.elo_text);

        updateUI();

        Button pauseButton = findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, PauseMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateUI() {
        scoreTextView.setText("Score: " + score);
        livesTextView.setText("Lives: " + lives);
        eloTextView.setText("Elo: " + eloPoints);
    }
}
