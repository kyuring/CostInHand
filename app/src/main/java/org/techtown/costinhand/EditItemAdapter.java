package org.techtown.costinhand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class EditItemAdapter extends BaseAdapter {

    LayoutInflater layoutInflater = null;
    Context context;
    ArrayList<RecipeData> editItemData;
    String mode;
    public static EditText edit_item_input;

    public EditItemAdapter(Context context, ArrayList<RecipeData> editItemDataArrayList, String mode) {
        context = context;
        editItemData = editItemDataArrayList;
        layoutInflater = LayoutInflater.from(context);
        this.mode = mode;
    }

    @Override
    public int getCount() {
        return editItemData.size();
    }

    @Override
    public Object getItem(int position) {
        return editItemData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_edittext, null);

        TextView edit_item_name = (TextView) view.findViewById(R.id.edit_item_name);
        TextView edit_item_remove = (TextView) view.findViewById(R.id.edit_item_remove);
        edit_item_input = (EditText) view.findViewById(R.id.edit_item_input);

        edit_item_name.setText(editItemData.get(position).getFood_Name());
        System.out.println(editItemData.get(position).getFood_Name());
        if (editItemData.get(position).getSET_NO() != null) {
            edit_item_input.setText(editItemData.get(position).getQty());
        }

        edit_item_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 삭제 클릭시 RECIPE_MODE 값을 "delete" 로변경
                editItemData.get(position).setRECIPE_MODE("delete");
                ItemAddActivity.recipeDeleteDataArrayList.add(new RecipeDeleteData(editItemData.get(position).getRecipe_seq(), editItemData.get(position).getFood_seq(),
                        editItemData.get(position).getMENU_SEQ(),ItemAddActivity.buttonName));
                editItemData.remove(position);
                // DB delete 필요
                // adapter 리플래쉬
                notifyDataSetChanged();
            }
        });
        return view;
    }
    public void setQTY(int position) {

        editItemData.get(position).setQty(edit_item_input.getText().toString());
    }
}
