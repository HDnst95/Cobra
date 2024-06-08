package de.cobra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ShopAdapter extends BaseAdapter {

    private Context context;
    private List<ShopItem> items;

    public ShopAdapter(Context context, List<ShopItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.shop_item, parent, false);
        }

        ShopItem item = items.get(position);

        TextView tvName = convertView.findViewById(R.id.tvItemName);
        TextView tvPrice = convertView.findViewById(R.id.tvItemPrice);

        tvName.setText(item.getName());
        tvPrice.setText(String.valueOf(item.getPrice()));

        return convertView;
    }
}
