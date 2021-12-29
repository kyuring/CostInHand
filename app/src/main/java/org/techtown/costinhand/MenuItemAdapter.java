package org.techtown.costinhand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuItemAdapter extends BaseAdapter {

    LayoutInflater layoutInflater = null;
    Context context = null;
    ArrayList<MenuData> menuData;

    public MenuItemAdapter(Context context, ArrayList<MenuData> menuItemDataArrayList) {
        context = context;
        menuData = menuItemDataArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return menuData.size();
    }

    @Override
    public Object getItem(int position) {
        return menuData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_main, null);

        TextView main_item_name = (TextView) view.findViewById(R.id.main_item_name);
        TextView main_item_price = (TextView) view.findViewById(R.id.main_item_price);

        main_item_name.setText(menuData.get(position).getName());
        main_item_price.setText(menuData.get(position).getCost_prc());

        return view;
    }
}
