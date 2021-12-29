package org.techtown.costinhand;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SourceAdapter extends BaseAdapter {

    LayoutInflater layoutInflater = null;
    Context context = null;
    ArrayList<RecipeData> RecipeData;

    public SourceAdapter(Context context, ArrayList<RecipeData> SourceItemDataArrayList) {
        context = context;
        RecipeData = SourceItemDataArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return RecipeData.size();
    }

    @Override
    public Object getItem(int position) {
        return RecipeData.get(position);
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
        main_item_name.setText(RecipeData.get(position).getRecipe_name());
        main_item_price.setText(RecipeData.get(position).getCost_Prc());

        return view;
    }
}
