package org.techtown.costinhand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SelectAdapter extends BaseAdapter {

    LayoutInflater layoutInflater = null;
    CustomListDialog customListDialog = null;
    CustomFindDialog customFindDialog = null;
    ArrayList<SelectItemData> selectItemData;
    ArrayList<FoodData> selectFoodItemData;
    String mode;
    public SelectAdapter(CustomListDialog customListDialog, ArrayList<FoodData> selectFoodItemDataArrayList, String Mode) {
        customListDialog = customListDialog;
        selectFoodItemData = selectFoodItemDataArrayList;
        mode = Mode;
        layoutInflater = LayoutInflater.from(customListDialog.getContext());
    }

    public SelectAdapter(CustomFindDialog customFindDialog, ArrayList<SelectItemData> selectItemDataArrayList) {
        customFindDialog = customFindDialog;
        selectItemData = selectItemDataArrayList;
        layoutInflater = LayoutInflater.from(customFindDialog.getContext());
    }

    @Override
    public int getCount() {
        if(mode != null){
            return selectFoodItemData.size();
        } else {
            return selectItemData.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if(mode != null){
            return selectFoodItemData.get(position);
        } else {
            return selectItemData.get(position);
        }
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
        TextView select_item_gram = (TextView) view.findViewById(R.id.select_item_gram);

        if(mode != null) {
            select_item_name.setText(selectFoodItemData.get(position).getName());
            select_item_price.setText(selectFoodItemData.get(position).getCost_price());
        } else {
            select_item_name.setText(selectItemData.get(position).getSelect_item_name());
            select_item_price.setText(selectItemData.get(position).getSelect_item_price());
            if(selectItemData.get(position).getSelect_item_gram() != null) {
                select_item_gram.setText(selectItemData.get(position).getSelect_item_gram() + "g/l/개");
            }
        }
        return view;
    }
}
