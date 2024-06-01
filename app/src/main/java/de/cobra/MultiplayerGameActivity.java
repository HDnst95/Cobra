package de.cobra;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MultiplayerGameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiplayerGameView multiplayerGameView = new MultiplayerGameView(this);
        setContentView(multiplayerGameView);
    }
}
