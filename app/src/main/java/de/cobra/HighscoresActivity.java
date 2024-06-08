package de.cobra;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class HighscoresActivity extends AppCompatActivity {

    private ListView lvHighscores;
    private SharedPreferences prefs;
    private static final String PREFS_NAME = "game_prefs";
    private static final String KEY_HIGHSCORES = "highscores";

    private List<Highscore> highscores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        lvHighscores = findViewById(R.id.lvHighscores);
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        highscores = loadHighscores();

        HighscoreAdapter adapter = new HighscoreAdapter(this, highscores);
        lvHighscores.setAdapter(adapter);
    }

    private List<Highscore> loadHighscores() {
        List<Highscore> highscores = new ArrayList<>();
        String highscoreString = prefs.getString(KEY_HIGHSCORES, "");
        if (!highscoreString.isEmpty()) {
            String[] entries = highscoreString.split(";");
            for (String entry : entries) {
                String[] parts = entry.split(",");
                highscores.add(new Highscore(parts[0], Integer.parseInt(parts[1])));
            }
        }
        return highscores;
    }

    public void saveHighscore(String player, int score) {
        highscores.add(new Highscore(player, score));
        StringBuilder highscoreString = new StringBuilder();
        for (Highscore highscore : highscores) {
            highscoreString.append(highscore.getPlayer()).append(",").append(highscore.getScore()).append(";");
        }
        prefs.edit().putString(KEY_HIGHSCORES, highscoreString.toString()).apply();
    }
}
