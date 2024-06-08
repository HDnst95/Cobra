package de.cobra;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private ListView lvItems;
    private TextView tvPoints;
    private List<ShopItem> items;
    private int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        lvItems = findViewById(R.id.lvItems);
        tvPoints = findViewById(R.id.tvPoints);

        points = 100; // Beispielwert, sollte dynamisch geladen werden
        items = new ArrayList<>();
        items.add(new ShopItem("Speed Boost", 50));
        items.add(new ShopItem("Extra Life", 75));
        items.add(new ShopItem("Shield", 100));

        ShopAdapter adapter = new ShopAdapter(this, items);
        lvItems.setAdapter(adapter);

        tvPoints.setText("Punkte: " + points);

        lvItems.setOnItemClickListener((parent, view, position, id) -> {
            ShopItem item = items.get(position);
            if (points >= item.getPrice()) {
                points -= item.getPrice();
                tvPoints.setText("Punkte: " + points);
                // Weitere Logik für den Kauf des Gegenstands hinzufügen
            }
        });
    }
}
