package org.techtown.costinhand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeDetailAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<RecipeData> recipeData;

    public RecipeDetailAdapter(Context context, ArrayList<RecipeData> recipeDataArrayList) {
        context = context;
        recipeData = recipeDataArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return recipeData.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeData.get(position);
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

        detail_item_name.setText(recipeData.get(position).getFood_Name());
        detail_item_price.setText(recipeData.get(position).getCost_Prc());
        detail_item_gram.setText(recipeData.get(position).getQty());

        return view;
    }
}
