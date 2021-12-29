package org.techtown.costinhand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CompletionService;

public class ItemDetailActivity extends AppCompatActivity {
    TextView item_name;
    TextView item_price, detail_item_gram;
    FloatingActionButton button_add;
    String buttonName, SEQ, NAME, COST_PRC, QTY;
    ListView listView;
    int position;
    public static RecipeData recipeData;
    public static ArrayList<RecipeData> recipeDataArrayList;
    public static MenuData menuData;
    public static ArrayList<MenuData> menuDataArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ItemMenuActivity 에서 item_name, item_price 를 받아서 세팅
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        Log.d("source_position", String.valueOf(position));
        buttonName = intent.getExtras().getString("button_name");
        if(buttonName.equals("source")){
            recipeData = ItemMenuActivity.sourceItemDataArrayList.get(position);
            SEQ = recipeData.getRecipe_seq();
            NAME = recipeData.getRecipe_name();
            COST_PRC = recipeData.getCost_Prc();
            QTY = recipeData.getQty();
        } else {
            menuData = ItemMenuActivity.menuItemDataArrayList.get(position);
            SEQ = menuData.getSeq();
            NAME = menuData.getName();
            COST_PRC = menuData.getCost_prc();
            QTY = menuData.getCount();
        }
        UISettings();
        new BackgroundTask().execute();


        button_add = (FloatingActionButton) findViewById(R.id.fab_menu1);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetailActivity.this, ItemAddActivity.class);
                intent.putExtra("NAME",  NAME);
                intent.putExtra("SEQ", SEQ);;
                intent.putExtra("buttonName", buttonName);
                intent.putExtra("Mode", "update");
                startActivity(intent);
                overridePendingTransition(0,0);
                startActivity(intent);
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
        listView = (ListView) findViewById(R.id.detailMenu_listview);
        final RecipeDetailAdapter recipeDetailAdapter = new RecipeDetailAdapter(this, recipeDataArrayList);
        listView.setAdapter(recipeDetailAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ListClick!!", recipeDataArrayList.get(position).getFood_Name());

            }
        });
    }

    void UISettings(){
        detail_item_gram = (TextView) findViewById(R.id.detail_item_gram);
        item_name = (TextView) findViewById(R.id.item_name);
        item_price = (TextView) findViewById(R.id.item_price);

        item_name.setText(NAME);
        item_price.setText(COST_PRC);
        detail_item_gram.setText(QTY);

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
                recipeDataArrayList = new ArrayList<RecipeData>();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                Log.d("jsonArray", String.valueOf(jsonArray));
                int count = 0;
                String Food_Name, Food_seq, Qty, Cost_Prc, RECIPE_SEQ, Set_no, Menu_seq;
                while (count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    Food_seq = object.getString("FOOD_SEQ");
                    Food_Name = object.getString("FOOD_NAME");
                    Qty = object.getString("QTY");
                    Cost_Prc = object.getString("COST_PRC");
                    Set_no = object.getString("SET_NO");
                    Menu_seq = object.getString("MENU_SEQ");
                    RECIPE_SEQ = object.getString("RECIPE_SEQ");
                    recipeData = new RecipeData(NAME, RECIPE_SEQ, Food_seq, Food_Name, Qty, Cost_Prc,Set_no, Menu_seq);
                    recipeDataArrayList.add(recipeData);
                    Log.d("object", String.valueOf(object));
                    count++;
                }
                ListEvent();
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
    }
}