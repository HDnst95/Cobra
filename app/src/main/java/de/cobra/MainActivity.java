package de.cobra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startGameButton = findViewById(R.id.start_game_button);
        Button optionsButton = findViewById(R.id.options_button);
        Button shopButton = findViewById(R.id.shop_button);
        Button multiplayerButton = findViewById(R.id.multiplayer_button);

        View.OnClickListener onClickListener = v -> {
            Class<?> targetActivity = null;
            Intent intent = new Intent(MainActivity.this, OptionsActivity.class);

            switch (v.getId()) {
                case R.id.start_game_button:
                    intent.putExtra("mode", "singleplayer");
                    break;
                case R.id.options_button:
                    targetActivity = OptionsActivity.class;
                    break;
                case R.id.shop_button:
                    targetActivity = ShopActivity.class;
                    break;
                case R.id.multiplayer_button:
                    targetActivity = MultiplayerActivity.class;
                    break;
            }

            if (targetActivity != null) {
                intent.setClass(MainActivity.this, targetActivity);
            }

            startActivity(intent);
        };

        startGameButton.setOnClickListener(onClickListener);
        optionsButton.setOnClickListener(onClickListener);
        shopButton.setOnClickListener(onClickListener);
        multiplayerButton.setOnClickListener(onClickListener);
    }
}

