package de.cobra;

        import android.os.Bundle;
        import androidx.appcompat.app.AppCompatActivity;

        /**
         * MultiplayerActivity handles the user interface for the multiplayer mode of the game.
         */
        public class MultiplayerActivity extends AppCompatActivity {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_multiplayer);
            }
        }