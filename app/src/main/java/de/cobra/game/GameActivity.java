package de.cobra.game;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import de.cobra.R;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;
    private LinearLayout pauseMenu;
    private LinearLayout gameOverLayout;
    private int gridSize;
    private int speed;
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gridSize = getIntent().getIntExtra("gridSize", 40);
        speed = getIntent().getIntExtra("speed", 3);
        playerName = getIntent().getStringExtra("playerName");

        gameView = findViewById(R.id.game_view);
        gameView.setGridSize(gridSize);
        gameView.setSpeed(speed);
        gameView.setOnGameOverListener(this::showGameOverMenu);

        pauseMenu = findViewById(R.id.pause_menu);
        gameOverLayout = findViewById(R.id.game_over_layout);

        SeekBar sizeSeekBar = findViewById(R.id.pause_size_seekbar);
        sizeSeekBar.setProgress(gridSize / 8); // Da die Standardgröße 40 ist
        sizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gridSize = (progress + 1) * 8;
                gameView.setGridSize(gridSize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar speedSeekBar = findViewById(R.id.pause_speed_seekbar);
        speedSeekBar.setProgress(speed);
        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speed = progress;
                gameView.setSpeed(speed);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        EditText nameEditText = findViewById(R.id.pause_name_edittext);
        nameEditText.setText(playerName);
        nameEditText.setOnEditorActionListener((v, actionId, event) -> {
            playerName = nameEditText.getText().toString();
            return false;
        });

        Button resumeButton = findViewById(R.id.resume_button);
        resumeButton.setOnClickListener(v -> {
            pauseMenu.setVisibility(View.GONE);
            gameView.resumeGame();
        });

        Button restartButtonPause = findViewById(R.id.restart_button_pause);
        restartButtonPause.setOnClickListener(v -> {
            gameView.initializeGame();
            pauseMenu.setVisibility(View.GONE);
            gameView.resumeGame();
        });

        Button mainMenuButtonPause = findViewById(R.id.main_menu_button_pause);
        mainMenuButtonPause.setOnClickListener(v -> finish());

        Button restartButton = findViewById(R.id.restart_button);
        restartButton.setOnClickListener(v -> {
            gameView.initializeGame();
            gameOverLayout.setVisibility(View.GONE);
            gameView.resumeGame();
        });

        Button mainMenuButton = findViewById(R.id.main_menu_button);
        mainMenuButton.setOnClickListener(v -> finish());
    }

    public void showPauseMenu() {
        pauseMenu.setVisibility(View.VISIBLE);
        gameView.pauseGame();
    }

    public void showGameOverMenu() {
        runOnUiThread(() -> gameOverLayout.setVisibility(View.VISIBLE));
    }

    @Override
    public void onBackPressed() {
        if (pauseMenu.getVisibility() == View.VISIBLE) {
            pauseMenu.setVisibility(View.GONE);
            gameView.resumeGame();
        } else if (gameOverLayout.getVisibility() == View.VISIBLE) {
            gameOverLayout.setVisibility(View.GONE);
        } else {
            showPauseMenu();
        }
    }
}
