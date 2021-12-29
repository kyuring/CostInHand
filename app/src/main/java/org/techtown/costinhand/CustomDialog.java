package org.techtown.costinhand;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class CustomDialog extends Dialog {
    private EditText EditText_menu_name;
    private Context mContext;
    private String buttonName;
    Button saveButton,cancelButton;
    TextView changeName;
    String recipe_name, menu_name;
    public static FoodData foodData;
    public static MenuData menuData;
    public CustomDialog(@NonNull Context context, String buttonName) {
        super(context);
        this.mContext = context;
        this.buttonName = buttonName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);
        // 다이얼로그의 배경을 투명으로 만든다.
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        UISetting();
        String mode = "insertRecipe";
        // 확인버튼
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonName.equals("source")) {
                    recipe_name = EditText_menu_name.getText().toString();
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                Log.d("response", String.valueOf(jsonResponse));
                                boolean success = jsonResponse.getBoolean("success");
                                if(success) {
                                    // 초기 생성이라 아직 원가값을 0 으로 설정
                                    foodData = new FoodData(jsonResponse.getString("RECIPE_SEQ"), jsonResponse.getString("FOOD_SEQ"), recipe_name, "0");
                                    Intent intent = new Intent(getContext(), ItemAddActivity.class);
                                    intent.putExtra("NAME",  recipe_name);
                                    intent.putExtra("SEQ", jsonResponse.getString("RECIPE_SEQ"));
                                    intent.putExtra("buttonName", buttonName);
                                    intent.putExtra("Mode", "insert");
                                    ItemMenuActivity.foodItemDataArrayList.add(foodData);
                                    ItemMenuActivity.sourceAdapter.notifyDataSetChanged();
                                    mContext.startActivity(intent);
                                    dismiss();
                                } else {
                                    Log.d("shekq","노답!!!");
                                }
                                Log.d("success", String.valueOf(success));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    if(recipe_name.length() == 0 ){
                        MainActivity.alert(getContext(), "소스명을 입력하세요!", "경고", 1);
                    } else {
                        FoodRequest foodRequest = new FoodRequest(mode, recipe_name, MainActivity.userid, responseListener);
                        foodRequest.setShouldCache(false);
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        queue.add(foodRequest);
                    }
                } else {
                    // menu 추가
                    menu_name = EditText_menu_name.getText().toString();
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                Log.d("response", String.valueOf(jsonResponse));
                                boolean success = jsonResponse.getBoolean("success");
                                if(success) {
                                    // 초기 생성이라 아직 원가값을 0 으로 설정
                                    menuData = new MenuData(menu_name, "0", jsonResponse.getString("MENU_SEQ"), "0");
                                    Intent intent = new Intent(getContext(), ItemAddActivity.class);
                                    intent.putExtra("NAME",  menu_name);
                                    intent.putExtra("SEQ", jsonResponse.getString("MENU_SEQ"));
                                    intent.putExtra("buttonName", buttonName);
                                    intent.putExtra("Mode", "insert");
                                    ItemMenuActivity.menuItemDataArrayList.add(menuData);
                                    ItemMenuActivity.menuItemAdapter.notifyDataSetChanged();
                                    mContext.startActivity(intent);
                                    dismiss();
                                } else {
                                    Log.d("shekq","노답!!!");
                                }
                                Log.d("success", String.valueOf(success));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    if(menu_name.length() == 0 ){
                        MainActivity.alert(getContext(), "메뉴명을 입력하세요!", "경고", 1);
                    } else {
                        MenuRequest menuRequest = new MenuRequest(mode, menu_name, MainActivity.userid, responseListener);
                        menuRequest.setShouldCache(false);
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        queue.add(menuRequest);
                    }

                }
            }
        });
        
        // 취소버튼
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
    void UISetting(){
        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        EditText_menu_name = findViewById(R.id.EditText_menu_name);
        saveButton = findViewById(R.id.btnSave);
        cancelButton = findViewById(R.id.btnCancle);
        changeName = findViewById(R.id.changeName);
        if(buttonName.equals("source")) {
            changeName.setText("소스명");
        } else {
            changeName.setText("메뉴명");
        }
    }
}