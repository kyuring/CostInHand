package org.techtown.costinhand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainItemAdapter extends BaseAdapter {

    LayoutInflater layoutInflater = null;
    Context context = null;
    ArrayList<MainItemData> mainItemData;

    public MainItemAdapter(Context context, ArrayList<MainItemData> mainItemDataArrayList) {
        context = context;
        mainItemData = mainItemDataArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mainItemData.size();
    }

    @Override
    public Object getItem(int position) {
        return mainItemData.get(position);
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

        main_item_name.setText(mainItemData.get(position).getMain_item_name());
        main_item_price.setText(mainItemData.get(position).getMain_item_price());

        return view;
    }
}
