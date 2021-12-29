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

public class CompanyAdapter extends BaseAdapter {

    LayoutInflater layoutInflater = null;
    Context context = null;
    ArrayList<CompanyData> companyData;

    public CompanyAdapter(Context context, ArrayList<CompanyData> companyDataArrayList) {
        context = context;
        companyData = companyDataArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return companyData.size();
    }

    @Override
    public Object getItem(int position) {
        return companyData.get(position);
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

        remove_item_name.setText(companyData.get(position).getName());
        if(companyData.get(position).getTel() == null) {
            remove_item_info.setText(companyData.get(position).getPhone());
        } else {
            remove_item_info.setText(companyData.get(position).getTel());
        }
        remove_item_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage(companyData.get(position).getName() + "을 삭제하시겠습니까?");
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
                        deleteButtonEvt(companyData.get(position).getSeq(), v.getContext(), position, dialog);
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
                        companyData.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };
        CompanyRequest companyRequest = new CompanyRequest("delete", seq, responseListener);
        companyRequest.setShouldCache(false);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(companyRequest);
    }
}
