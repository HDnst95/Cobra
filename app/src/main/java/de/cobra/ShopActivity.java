package de.cobra;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShopActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        // Beispiel für das Abrufen der Punkte des Spielers (z.B. aus der Datenbank oder dem Speicher)
        final int[] playerPoints = {getPlayerPoints()}; // Verwende ein Array, um die Variable effektiv final zu machen

        TextView pointsTextView = findViewById(R.id.points_text_view);
        pointsTextView.setText("Punkte: " + playerPoints[0]);

        Button buyButton = findViewById(R.id.buy_button);
        buyButton.setOnClickListener(v -> {
            // Beispiel für den Kauf eines Gegenstands
            int itemCost = 100; // Beispielwert für die Kosten eines Gegenstands
            if (playerPoints[0] >= itemCost) {
                playerPoints[0] -= itemCost;
                updatePlayerPoints(playerPoints[0]);
                pointsTextView.setText("Punkte: " + playerPoints[0]);
                // Weitere Logik für den Erhalt des Gegenstands
            }
        });

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kehrt zum vorherigen Bildschirm zurück
            }
        });
    }

    private int getPlayerPoints() {
        // Beispiel für das Abrufen der Punkte des Spielers
        return 1500; // Beispielwert
    }

    private void updatePlayerPoints(int points) {
        // Beispiel für das Aktualisieren der Punkte des Spielers
    }
}
