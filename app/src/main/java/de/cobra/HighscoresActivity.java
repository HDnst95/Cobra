package de.cobra;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import androidx.appcompat.app.AppCompatActivity;

        /**
         * HighscoresActivity displays the high scores of the game.
         * It includes a button to go back to the previous screen.
         */
        public class HighscoresActivity extends AppCompatActivity {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_highscores);

                Button backButton = findViewById(R.id.back_button);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish(); // Returns to the previous screen
                    }
                });
            }
        }