package de.cobra;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import de.cobra.game.GameActivity;

public class OptionsActivity extends AppCompatActivity {
    private int gridSize = 40;
    private int speed = 3;
    private String playerName = "Player";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        SeekBar sizeSeekBar = findViewById(R.id.size_seekbar);
        sizeSeekBar.setProgress(gridSize / 8);
        sizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gridSize = (progress + 1) * 8;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar speedSeekBar = findViewById(R.id.speed_seekbar);
        speedSeekBar.setProgress(speed);
        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speed = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        EditText nameEditText = findViewById(R.id.name_edittext);
        nameEditText.setText(playerName);
        nameEditText.setOnEditorActionListener((v, actionId, event) -> {
            playerName = nameEditText.getText().toString();
            return false;
        });

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            // Speichern Sie die Optionen und starten Sie das Spiel
            Intent intent = new Intent(OptionsActivity.this, GameActivity.class);
            intent.putExtra("gridSize", gridSize);
            intent.putExtra("speed", speed);
            intent.putExtra("playerName", playerName);
            startActivity(intent);
        });
    }
}
