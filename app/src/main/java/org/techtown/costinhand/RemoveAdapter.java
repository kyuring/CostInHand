package org.techtown.costinhand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RemoveAdapter extends BaseAdapter {

    LayoutInflater layoutInflater = null;
    Context context = null;
    ArrayList<RemoveItemData> removeItemData;

    public RemoveAdapter(Context context, ArrayList<RemoveItemData> removeItemDataArrayList) {
        context = context;
        removeItemData = removeItemDataArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return removeItemData.size();
    }

    @Override
    public Object getItem(int position) {
        return removeItemData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_remove, null);

        TextView remove_item_name = (TextView) view.findViewById(R.id.remove_item_name);
        TextView remove_item_info = (TextView) view.findViewById(R.id.remove_item_info);
        TextView remove_item_remove = (TextView) view.findViewById(R.id.remove_item_remove);

        remove_item_name.setText(removeItemData.get(position).getRemove_item_name());
        remove_item_info.setText(removeItemData.get(position).getRemove_item_info());


        remove_item_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItemData.remove(position);
                // DB delete 필요
                // adapter 리플래쉬
                notifyDataSetChanged();

            }
        });

        return view;
    }
}
