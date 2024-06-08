package de.cobra;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MultiplayerLobbyActivity extends AppCompatActivity {

    private ListView lvPlayers;
    private Button btnCreateGame;
    private Button btnJoinGame;
    private List<String> players;
    private MultiplayerAdapter adapter;
    private Client client;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_lobby);

        lvPlayers = findViewById(R.id.lvPlayers);
        btnCreateGame = findViewById(R.id.btnCreateGame);
        btnJoinGame = findViewById(R.id.btnJoinGame);

        players = new ArrayList<>();
        adapter = new MultiplayerAdapter(this, players);
        lvPlayers.setAdapter(adapter);

        btnCreateGame.setOnClickListener(v -> createGame());
        btnJoinGame.setOnClickListener(v -> joinGame());

        new Thread(() -> {
            try {
                client = new Client("SERVER_IP_ADDRESS", 12345);
                while (true) {
                    String message = client.receiveMessage();
                    handler.post(() -> {
                        players.add(message);
                        adapter.notifyDataSetChanged();
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
                handler.post(() -> Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void createGame() {
        client.sendMessage("create_game");
        Toast.makeText(this, "Game created", Toast.LENGTH_SHORT).show();
    }

    private void joinGame() {
        client.sendMessage("join_game");
        Toast.makeText(this, "Joined game", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (client != null) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
