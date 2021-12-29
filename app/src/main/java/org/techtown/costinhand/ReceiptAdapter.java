package org.techtown.costinhand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReceiptAdapter extends BaseAdapter {

    LayoutInflater layoutInflater = null;
    ArrayList<SelectItemData> selectItemData;
    Context context;

    public ReceiptAdapter(Context context, ArrayList<SelectItemData> selectItemDataArrayList) {
        this.context = context;
        selectItemData = selectItemDataArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return selectItemData.size();
    }

    @Override
    public Object getItem(int position) {
        return selectItemData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_dialog_select, null);

        TextView select_item_name = (TextView) view.findViewById(R.id.select_item_name);
        TextView select_item_price = (TextView) view.findViewById(R.id.select_item_price);

        select_item_name.setText(selectItemData.get(position).getSelect_item_name());
        select_item_price.setText(selectItemData.get(position).getSelect_item_price());

        return view;
    }
}
