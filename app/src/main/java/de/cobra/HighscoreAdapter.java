package de.cobra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class HighscoreAdapter extends BaseAdapter {

    private Context context;
    private List<Highscore> highscores;

    public HighscoreAdapter(Context context, List<Highscore> highscores) {
        this.context = context;
        this.highscores = highscores;
    }

    @Override
    public int getCount() {
        return highscores.size();
    }

    @Override
    public Object getItem(int position) {
        return highscores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.highscore_item, parent, false);
        }

        Highscore highscore = highscores.get(position);

        TextView tvPlayerName = convertView.findViewById(R.id.tvPlayerName);
        TextView tvScore = convertView.findViewById(R.id.tvScore);

        tvPlayerName.setText(highscore.getPlayer());
        tvScore.setText(String.valueOf(highscore.getScore()));

        return convertView;
    }
}
