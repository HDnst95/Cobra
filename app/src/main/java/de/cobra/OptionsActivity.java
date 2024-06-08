package de.cobra;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;

public class OptionsActivity extends AppCompatActivity {

    private SeekBar sbGraphics;
    private SeekBar sbSound;
    private RadioGroup rgControl;
    private Button btnSaveOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        sbGraphics = findViewById(R.id.sbGraphics);
        sbSound = findViewById(R.id.sbSound);
        rgControl = findViewById(R.id.rgControl);
        btnSaveOptions = findViewById(R.id.btnSaveOptions);

        btnSaveOptions.setOnClickListener(v -> saveOptions());
    }

    private void saveOptions() {
        // Code to save options
    }
}
