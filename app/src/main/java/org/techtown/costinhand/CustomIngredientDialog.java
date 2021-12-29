package org.techtown.costinhand;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomIngredientDialog extends Dialog {
    private Context mContext;
    private String ingredient_name = null;
    private String mode;
    private FoodData foodData;
    private EditText edit_cost_price;
    EditText dlg_ingredient_name;
    EditText edit_price;
    EditText edit_qty;
    Spinner unit_spinner;
    Button button_cancel;
    Button button_add;
    // update 할경우
    public CustomIngredientDialog(@NonNull Context context, String ingredient_name, String mode, FoodData foodData) {
        super(context);
        this.mContext = context;
        this.ingredient_name = ingredient_name;
        this.mode = mode;
        this.foodData = foodData;
    }
    // insert 할 경우
    public CustomIngredientDialog(@NonNull Context context, String mode) {
        super(context);
        this.mContext = context;
        this.mode = mode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ingredient);

        UISettings();

        // 취소, 추가 버튼 - 일단 그냥 닫히게

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // foodData == null 인경우는 insert 하는거
                if(mode.equals("insert")) {
                    insertButtonEvt(dlg_ingredient_name.getText().toString(), edit_price.getText().toString(), edit_qty.getText().toString(), unit_spinner.getSelectedItem().toString(), mode, getContext());
                } else if(mode.equals("update")) {
                    updateButtonEvt(dlg_ingredient_name.getText().toString(), edit_price.getText().toString(), edit_qty.getText().toString(), unit_spinner.getSelectedItem().toString(), mode, foodData.getSeq(), getContext());
                }
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    void UISettings(){
        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_add = (Button) findViewById(R.id.button_add);
        edit_cost_price = (EditText) findViewById(R.id.edit_cost_price);
        dlg_ingredient_name = findViewById(R.id.dlg_ingredient_name);
        edit_price = (EditText) findViewById(R.id.edit_price);
        edit_qty = (EditText) findViewById(R.id.edit_qty);
        
        // 재료 추가할때는 "원가" editText제거
        if(mode.equals("insert")) {
            LinearLayout layout_cost_price = (LinearLayout) findViewById(R.id.layout_cost_price);
            layout_cost_price.setVisibility(View.GONE);
        }

        unit_spinner = (Spinner) findViewById(R.id.unit);
        ArrayAdapter<CharSequence> unit_adapter = ArrayAdapter.createFromResource(getContext(), R.array.unit_array, android.R.layout.simple_spinner_item);
        unit_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit_spinner.setAdapter(unit_adapter);

        if(foodData != null) {
            dlg_ingredient_name.setText(foodData.getName());
            edit_price.setText(foodData.getPrice());
            edit_cost_price.setText(foodData.getCost_price());
            edit_qty.setText(foodData.getQty());
            unit_spinner.setSelection(foodData.getUnit());
        }
        if(mode.equals("update")) {
            button_add.setText("수정");
        }
    }
    public void insertButtonEvt(String foodName, String price, String qty, String unit, String mode, Context context){
        if(foodName.length() == 0 || price.length() == 0 || qty.length() == 0) {
            MainActivity.alert(context, "입력해 주세요!!", "경고", 1);
        } else {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        Log.d("response", response);
                        JSONObject jsonResponse = new JSONObject(response);
                        String FOOD_SEQ = jsonResponse.getString("FOOD_SEQ");
                        String COST_PRC = jsonResponse.getString("COST_PRC");
                        String FOOD_KIND = jsonResponse.getString("FOOD_KIND");
                        String UNIT_SEQ = jsonResponse.getString("UNIT");
                        boolean success = jsonResponse.getBoolean("success");
                        if(!success) {
                            MainActivity.alert(context, "재료 입력 중 오류가 발생하였습니다.", "알림", 1);
                        } else {
                            // 성공시 할 일
                            FoodData foodData = new FoodData(FOOD_SEQ, foodName, FOOD_KIND, UNIT_SEQ, price, COST_PRC, qty);
                            ItemMenuActivity.foodItemDataArrayList.add(foodData);
                            ItemMenuActivity.foodAdapter.notifyDataSetChanged();
                            // 다이얼로그 닫기
                            CustomIngredientDialog.this.dismiss();
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            };
            FoodRequest foodRequest = new FoodRequest(MainActivity.userid, foodName, price, qty, unit, mode, responseListener);
            foodRequest.setShouldCache(false);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(foodRequest);
        }
    }
    public void updateButtonEvt(String foodName, String price, String qty, String unit, String mode, String seq, Context context){
        if(foodName.length() == 0 || price.length() == 0 || qty.length() == 0) {
            MainActivity.alert(context, "입력해 주세요!!", "경고", 1);
        } else {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        Log.d("response", response);
                        JSONObject jsonResponse = new JSONObject(response);
                        String COST_PRC = jsonResponse.getString("COST_PRC");
                        String UNIT_SEQ = jsonResponse.getString("UNIT");
                        boolean success = jsonResponse.getBoolean("success");
                        if(!success) {
                            MainActivity.alert(context, "재료 수정 중 오류가 발생하였습니다.", "알림", 1);
                        } else {
                            // 성공시 할 일
                            foodData.setName(foodName);
                            foodData.setPrice(price);
                            foodData.setQty(qty);
                            foodData.setUnit(UNIT_SEQ);
                            foodData.setCost_price(COST_PRC);

                            ItemMenuActivity.foodAdapter.notifyDataSetChanged();
                            // 다이얼로그 닫기
                            CustomIngredientDialog.this.dismiss();
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            };
            FoodRequest foodRequest = new FoodRequest(MainActivity.userid, foodName, price, qty, unit, mode, seq, responseListener);
            foodRequest.setShouldCache(false);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(foodRequest);
        }
    }

}
