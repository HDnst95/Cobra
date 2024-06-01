package de.cobra;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShopActivity extends AppCompatActivity {
    private int playerPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        // Beispiel für das Abrufen der Punkte des Spielers (z.B. aus der Datenbank oder dem Speicher)
        playerPoints = getPlayerPoints();

        TextView pointsTextView = findViewById(R.id.points_text_view);
        pointsTextView.setText("Punkte: " + playerPoints);

        Button buyButton = findViewById(R.id.buy_button);
        buyButton.setOnClickListener(v -> {
            // Beispiel für den Kauf eines Gegenstands
            int itemCost = 100; // Beispielwert für die Kosten eines Gegenstands
            if (playerPoints >= itemCost) {
                playerPoints -= itemCost;
                updatePlayerPoints(playerPoints);
                pointsTextView.setText("Punkte: " + playerPoints);
                // Weitere Logik für den Erhalt des Gegenstands
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
