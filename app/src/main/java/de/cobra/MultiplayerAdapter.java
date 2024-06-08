package de.cobra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class MultiplayerAdapter extends BaseAdapter {

    private Context context;
    private List<String> players;

    public MultiplayerAdapter(Context context, List<String> players) {
        this.context = context;
        this.players = players;
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Object getItem(int position) {
        return players.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.multiplayer_item, parent, false);
        }

        String player = players.get(position);

        TextView tvPlayerName = convertView.findViewById(R.id.tvPlayerName);
        tvPlayerName.setText(player);

        return convertView;
    }
}
