package org.techtown.costinhand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ItemAddActivity extends AppCompatActivity {
    public static ArrayList<RecipeData> editItemDataArrayList;
    TextView nameTextView;
    EditText find_text;
    String mode; // mode: update(자세히에서 온 값) or insert(생성용)
    public static EditItemAdapter editItemAdapter;
    public static RecipeData recipeData;
    public static String SEQ, NAME, buttonName, MENU_SEQ_RETURN, RECIPE_SEQ_RETURN;
    public static RecipeDeleteData recipeDeleteData;
    public static ArrayList<RecipeDeleteData> recipeDeleteDataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add);

        UISettings();
        // 기존에 있는 경우 리스트를 뿌려줌
        recipeDeleteDataArrayList = new ArrayList<RecipeDeleteData>();
        if(mode.equals("update")){
            new BackgroundTask().execute();
        } else {
            editItemDataArrayList = new ArrayList<RecipeData>();
            ListEvent();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    void UISettings(){
        //메뉴명 intent통해서 받은거로 채우기
        Intent intent = getIntent();
        nameTextView = (TextView) findViewById(R.id.menu_name);
        nameTextView.setText(intent.getExtras().getString("NAME"));
        NAME = nameTextView.getText().toString();
        find_text = (EditText) findViewById(R.id.find_text);
        buttonName = intent.getExtras().getString("buttonName");
        // SEQ와 mode 값 가져옴

        SEQ = intent.getExtras().getString("SEQ");
        mode = intent.getExtras().getString("Mode");
        
        // 재료 찾기 버튼
        ImageButton button_find = (ImageButton) findViewById(R.id.button_find);
        button_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomListDialog dlg = new CustomListDialog(view.getContext(), find_text.getText().toString(), mode, buttonName);
                dlg.show();
            }
        });
        // 취소 버튼
        Button button_item_cancel = (Button) findViewById(R.id.button_item_cancel);
        button_item_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemAddActivity.super.onBackPressed();
            }
        });

        // 확인 버튼
        Button button_item_add = (Button) findViewById(R.id.button_item_add);
        if(mode.equals("update")) {
            button_item_add.setText("수정");
        } else {
            button_item_add.setText("추가");
        }
        button_item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insert, update 인 경우 RECIPE_MODE 값만 다름
                // delete인경우 recipeDeleteDataArrayList 값에 들어있음
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Log.d("response", String.valueOf(jsonResponse));
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                // 초기 생성이라 아직 원가값을 0 으로 설정
                                finish();
                            } else {
                                Log.d("ItemAddActivity","ItemAddActivity_item_update_error");
                            }
                            Log.d("success", String.valueOf(success));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                if(editItemDataArrayList.size() > 0) {
                    for(int i = 0; i < editItemDataArrayList.size(); i++) {
                        Log.d("name", editItemDataArrayList.get(i).getFood_Name());
                        editItemAdapter.setQTY(i);
                        RecipeRequest recipeRequest;
                        recipeRequest = new RecipeRequest(RECIPE_SEQ_RETURN,
                                editItemDataArrayList.get(i).getSET_NO(), NAME,
                                editItemDataArrayList.get(i).getFood_seq(),
                                editItemDataArrayList.get(i).getCost_Prc(),
                                editItemDataArrayList.get(i).getQty(),
                                MainActivity.userid, buttonName,
                                editItemDataArrayList.get(i).getRECIPE_MODE(),
                                MENU_SEQ_RETURN,
                                responseListener);
                        recipeRequest.setShouldCache(false);
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(recipeRequest);
                    }
                }
                if(recipeDeleteDataArrayList.size() > 0) {
                    for(int i = 0; i < recipeDeleteDataArrayList.size(); i++) {
                        Log.d("getFood_seq", recipeDeleteDataArrayList.get(i).getFood_seq());
                        RecipeRequest recipeRequest = new RecipeRequest(recipeDeleteDataArrayList.get(i).getRecipe_seq()
                                , recipeDeleteDataArrayList.get(i).getFood_seq()
                                , recipeDeleteDataArrayList.get(i).getMENU_SEQ()
                                , recipeDeleteDataArrayList.get(i).getButtonName()
                                , "delete"
                                ,MainActivity.userid, responseListener);
                        recipeRequest.setShouldCache(false);
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(recipeRequest);
                    }
                }
            }
        });

        // 툴바 세팅
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // 리스트뷰 채우기
    void ListEvent(){
        ListView listView = (ListView) findViewById(R.id.add_listview);
        editItemAdapter = new EditItemAdapter(this, editItemDataArrayList, mode);
        listView.setAdapter(editItemAdapter);
    }

    // 메뉴 리스트
    class BackgroundTask extends AsyncTask<Void, Void, String > {
        String target;
        @Override
        protected void onPreExecute(){
            if(buttonName.equals("source")) {
                target = "https://kyuna58.cafe24.com/CostInHand/Recipe.php?RECIPE_SEQ="+SEQ+"&userID="+MainActivity.userid+"&buttonName="+buttonName;
            } else {
                target = "https://kyuna58.cafe24.com/CostInHand/Recipe.php?MENU_SEQ="+SEQ+"&userID="+MainActivity.userid+"&buttonName="+buttonName;
            }
        }
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();

                if(httpURLConnection != null) {
                    httpURLConnection.setConnectTimeout(1000);
                    while((temp = bufferedReader.readLine()) != null) {
                        stringBuilder.append(temp + "\n");
                    }
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try{
                editItemDataArrayList = new ArrayList<RecipeData>();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                Log.d("jsonArray", String.valueOf(jsonArray));
                int count = 0;
                String Food_Name, Food_seq, Qty, Cost_Prc, Menu_seq, Set_no, RECIPE_SEQ, RECIPE_MODE;
                while (count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    Food_seq = object.getString("FOOD_SEQ");
                    Food_Name = object.getString("FOOD_NAME");
                    Qty = object.getString("QTY");
                    Cost_Prc = object.getString("COST_PRC");
                    Set_no = object.getString("SET_NO");
                    Menu_seq = object.getString("MENU_SEQ");
                    RECIPE_SEQ = object.getString("RECIPE_SEQ");
                    RECIPE_MODE = "update";
                    MENU_SEQ_RETURN = Menu_seq;
                    RECIPE_SEQ_RETURN = RECIPE_SEQ;
                    recipeData = new RecipeData(NAME, RECIPE_SEQ, Food_seq, Food_Name,Qty, Cost_Prc,Set_no, Menu_seq, RECIPE_MODE);
                    editItemDataArrayList.add(recipeData);
                    Log.d("RECIPE_MODE", recipeData.getRECIPE_MODE());
                    count++;
                }
                ListEvent();
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
    }
}