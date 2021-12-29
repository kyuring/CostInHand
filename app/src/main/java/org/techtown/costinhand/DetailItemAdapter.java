package org.techtown.costinhand;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailItemAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<DetailItemData> detailItemData;

    public DetailItemAdapter(Context context, ArrayList<DetailItemData> detailItemDataArrayList) {
        context = context;
        detailItemData = detailItemDataArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return detailItemData.size();
    }

    @Override
    public Object getItem(int position) {
        return detailItemData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_detail, null);

        TextView detail_item_name = (TextView) view.findViewById(R.id.detail_item_name);
        TextView detail_item_price = (TextView) view.findViewById(R.id.detail_item_price);
        TextView detail_item_gram = (TextView) view.findViewById(R.id.detail_item_gram);

        detail_item_name.setText(detailItemData.get(position).getDetail_item_name());
        detail_item_price.setText(detailItemData.get(position).getDetail_item_price());
        detail_item_gram.setText(detailItemData.get(position).getDetail_item_gram());

        return view;
    }
}
