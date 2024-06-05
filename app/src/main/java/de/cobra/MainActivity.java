package de.cobra;

        import android.content.Intent;
        import android.os.Bundle;
        import android.widget.Button;
        import androidx.appcompat.app.AppCompatActivity;

        /**
         * MainActivity is the entry point of the application. It provides 
         * navigation to different parts of the app, such as starting a game, 
         * going to options, opening the shop, and accessing multiplayer mode.
         */
        public class MainActivity extends AppCompatActivity {

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                Button startGameButton = findViewById(R.id.start_game_button);
                startGameButton.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
                    intent.putExtra("mode", "singleplayer");
                    startActivity(intent);
                });

                Button optionsButton = findViewById(R.id.options_button);
                optionsButton.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
                    startActivity(intent);
                });

                Button shopButton = findViewById(R.id.shop_button);
                shopButton.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                    startActivity(intent);
                });

                Button multiplayerButton = findViewById(R.id.multiplayer_button);
                multiplayerButton.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, MultiplayerGameActivity.class);
                    startActivity(intent);
                });
            }
        }