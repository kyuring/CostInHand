package org.techtown.costinhand;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodAdapter extends BaseAdapter {

    LayoutInflater layoutInflater = null;
    Context context = null;
    ArrayList<FoodData> foodData;

    public FoodAdapter(Context context, ArrayList<FoodData> foodItemDataArrayList) {
        context = context;
        foodData = foodItemDataArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return foodData.size();
    }

    @Override
    public Object getItem(int position) {
        return foodData.get(position);
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

        remove_item_name.setText(foodData.get(position).getName());
        remove_item_info.setText(foodData.get(position).getCost_price());


        remove_item_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage(foodData.get(position).getName() + "을 삭제하시겠습니까?");
                builder.setCancelable(true);
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteButtonEvt(foodData.get(position).getSeq(), v.getContext(), position, dialog);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return view;
    }
    public void deleteButtonEvt(String seq, Context context, int position, DialogInterface dialog){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Log.d("response", response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(!success) {
                        MainActivity.alert(context, "재료 삭제 중 오류가 발생하였습니다.", "알림", 1);
                    } else {
                        foodData.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };
        FoodRequest foodRequest = new FoodRequest("delete", seq, responseListener);
        foodRequest.setShouldCache(false);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(foodRequest);
    }
}
