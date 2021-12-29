package org.techtown.costinhand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.transform.Source;

public class ItemMenuActivity extends AppCompatActivity {
    private LinearLayout menu_item1;
    FloatingActionButton fab_menu1;
    // 회사
    public static ArrayList<CompanyData> companyDataArrayList;
    public static CompanyAdapter companyAdapter;
    // 메뉴
    public static ArrayList<MenuData> menuItemDataArrayList;
    public static MenuItemAdapter menuItemAdapter;
    // 재료
    public static ArrayList<FoodData> foodItemDataArrayList;
    private String buttonName;
    public static FoodAdapter foodAdapter;
    // 소스
    public static ArrayList<RecipeData> sourceItemDataArrayList;
    public static SourceAdapter sourceAdapter;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_menu);

         Intent intent = getIntent();
         buttonName = intent.getExtras().getString("buttonName");
         new BackgroundTask().execute();

        // 상단 툴바에 뒤로가기 이벤트 설정
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         // 플로팅 버튼
         fab_menu1 = findViewById(R.id.fab_menu1);
         fab_menu1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 // 버튼 클릭시 Custom Dialog 호출
                 if(buttonName.equals("menu") || buttonName.equals("source")){
                     CustomDialog dlg = new CustomDialog(view.getContext(), buttonName);
                     dlg.show();
                 } else if(buttonName.equals("ingredient")){
                     CustomIngredientDialog dlg = new CustomIngredientDialog(view.getContext(), "insert");
                     dlg.setCancelable(false);
                     dlg.show();
                 } else if(buttonName.equals("company")) {
                     CustomCompanyDialog dlg = new CustomCompanyDialog(view.getContext(), "insert");
                     dlg.setCancelable(false);
                     dlg.show();
                 }
             }
         });
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
    public void ListEvent(){
        ListView listView = (ListView) findViewById(R.id.main_listview);
        if(buttonName.equals("menu")){
            menuItemAdapter = new MenuItemAdapter(this, menuItemDataArrayList);
            listView.setAdapter(menuItemAdapter);
        } else if(buttonName.equals("source")) {
            sourceAdapter = new SourceAdapter(this, sourceItemDataArrayList);
            listView.setAdapter(sourceAdapter);
        } else if(buttonName.equals("ingredient")){
            foodAdapter = new FoodAdapter(this, foodItemDataArrayList);
            listView.setAdapter(foodAdapter);
        } else if(buttonName.equals("company")) {
            companyAdapter = new CompanyAdapter(this, companyDataArrayList);
            listView.setAdapter(companyAdapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 메뉴 버튼일때 이벤트 처리
                if(buttonName.equals("menu")) {
                    Intent ListIntent = new Intent(ItemMenuActivity.this, ItemDetailActivity.class);
                    ListIntent.putExtra("button_name",buttonName);
                    ListIntent.putExtra("position", position);
                    Log.d("source_position", String.valueOf(position));
                    startActivity(ListIntent);
                    overridePendingTransition(0,0);
                } else if (buttonName.equals("source")) {
                    Intent ListIntent = new Intent(ItemMenuActivity.this, ItemDetailActivity.class);
                    ListIntent.putExtra("button_name",buttonName);
                    ListIntent.putExtra("position", position);
                    Log.d("source_position", String.valueOf(position));
                    startActivity(ListIntent);
                    overridePendingTransition(0,0);
                } else if(buttonName.equals("ingredient")){
                    CustomIngredientDialog dlg = new CustomIngredientDialog(view.getContext(), foodItemDataArrayList.get(position).getName(), "update", foodItemDataArrayList.get(position));
                    dlg.setCancelable(false);
                    dlg.show();
                } else if(buttonName.equals("company")) {
                    CustomCompanyDialog dlg = new CustomCompanyDialog(view.getContext(), companyDataArrayList.get(position), "update");
                    dlg.setCancelable(false);
                    dlg.show();
                }
            }
        });
    }


    // 메뉴 리스트
    class BackgroundTask extends AsyncTask<Void, Void, String >{
        String target;
        @Override
        protected void onPreExecute(){
            target = "https://kyuna58.cafe24.com/CostInHand/GetInfo.php?userID="+MainActivity.userid+"&buttonName="+buttonName;
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
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                menuItemDataArrayList = new ArrayList<MenuData>();
                foodItemDataArrayList = new ArrayList<FoodData>();
                companyDataArrayList = new ArrayList<CompanyData>();
                sourceItemDataArrayList = new ArrayList<RecipeData>();
                Log.d("jsonArray", String.valueOf(jsonArray));
                int count = 0;
                String FOOD_SEQ, FOOD_NAME, FOOD_KIND, RECIPE_SEQ, UNIT, PCH_PRC, QTY;
                String MENU_SEQ, MENU_NAME, PACK_QTY, COST_PRC;
                String CO_SEQ, CO_NAME, CEO_NAME, TEL, PHONE, ETC, RECIPE_NAME;
                while (count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    if(buttonName.equals("ingredient")) {
                        FOOD_SEQ = object.getString("FOOD_SEQ");
                        FOOD_NAME = object.getString("FOOD_NAME");
                        FOOD_KIND = object.getString("FOOD_KIND");
                        UNIT = object.getString("UNIT");
                        PCH_PRC = object.getString("PCH_PRC");
                        COST_PRC = object.getString("COST_PRC");
                        QTY = object.getString("QTY");
                        RECIPE_SEQ = object.getString("RECIPE_SEQ");
                        FoodData foodData = new FoodData(FOOD_SEQ, FOOD_NAME, FOOD_KIND, UNIT, PCH_PRC, COST_PRC, QTY);
                        foodItemDataArrayList.add(foodData);
                    }else if(buttonName.equals("source")){
                        RECIPE_SEQ = object.getString("RECIPE_SEQ");
                        RECIPE_NAME = object.getString("RECIPE_NAME");
                        COST_PRC = object.getString("COST_PRC");
                        QTY = object.getString("QTY");
                        RecipeData recipeData = new RecipeData(RECIPE_SEQ, RECIPE_NAME, COST_PRC, QTY);
                        sourceItemDataArrayList.add(recipeData);
                    }else if(buttonName.equals("menu")){
                        MENU_SEQ = object.getString("MENU_SEQ");
                        MENU_NAME = object.getString("MENU_NAME");
                        PACK_QTY = object.getString("PACK_QTY");
                        COST_PRC = object.getString("COST_PRC");

                        MenuData menuData = new MenuData(MENU_NAME, COST_PRC, MENU_SEQ, PACK_QTY );
                        menuItemDataArrayList.add(menuData);
                    } else if(buttonName.equals("company")) {
                        CO_SEQ = object.getString("CO_SEQ");
                        CO_NAME = object.getString("CO_NAME");
                        CEO_NAME = object.getString("CEO_NAME");
                        TEL = object.getString("TEL");
                        PHONE = object.getString("PHONE");
                        ETC = object.getString("ETC");

                        CompanyData companyData = new CompanyData(CO_NAME, CEO_NAME, CO_SEQ, TEL, PHONE, ETC);
                        companyDataArrayList.add(companyData);
                    }
                    count++;
                }
                ListEvent();
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
    }
}