package org.techtown.costinhand;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CustomListDialog extends Dialog {
    private Context mContext;
    public static ArrayList<FoodData> selectItemDataArrayList;
    public static SelectAdapter selectAdapter;
    private String callName;
    private String company_name = null;
    private String find_text;
    private String mode;
    String buttonName, AddMode;

    // itemAddActicity.java 에서 호출
    public CustomListDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;

    }
    // ReceiptActivity.java 에서 호출
    public CustomListDialog(@NonNull Context context, String callName, String company_name) {
        super(context);
        this.callName = callName;
        this.company_name = company_name;
    }
    public CustomListDialog(@NonNull Context context, String find_text, String Mode, String buttonName) {
        super(context);
        this.mContext = context;
        this.find_text = find_text;
        this.buttonName = buttonName;
        this.AddMode = Mode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom_list);
        mode = "FIND_FOOD";
        new BackgroundTask().execute();


        if(company_name != null) {
            if(callName == "ReceiptActivity") {
                TextView customListDlg_company_name = (TextView) findViewById(R.id.customListDlg_company_name);
                customListDlg_company_name.setText(company_name);
                TextView customListDlg_sentence = (TextView) findViewById(R.id.customListDlg_sentence);
                customListDlg_sentence.setText("의 거래명세서");
            }
        }

        Button cancelButton = findViewById(R.id.btnCancle);

        // 취소버튼
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
    public void ListEvent(){
        ListView listView = (ListView) findViewById(R.id.select_dialog_listview);
        Log.d("selectItemDataArrayList", String.valueOf(selectItemDataArrayList.size()));
        selectAdapter = new SelectAdapter(this, selectItemDataArrayList, mode);
        listView.setAdapter(selectAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 여기 insert하기!!!
                if(company_name == null) {
                    Log.d("company null", "null");
                    FoodData foodDatas = selectItemDataArrayList.get(position);
                    Log.d("foosDatas", foodDatas.getName());
                    String RECIPE_MODE = "insert";
                    RecipeData recipeData = new RecipeData(foodDatas.getSeq(), foodDatas.getName(), foodDatas.getCost_price(), foodDatas.getQty(), RECIPE_MODE);
                    ItemAddActivity.editItemDataArrayList.add(recipeData);
                    ItemAddActivity.editItemAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    // 메뉴 리스트
    class BackgroundTask extends AsyncTask<Void, Void, String > {
        String target;
        @Override
        protected void onPreExecute(){
            target = "https://kyuna58.cafe24.com/CostInHand/Food.php?userID="+MainActivity.userid + "&Mode="+mode+"&FIND_FOOD_NAME="+find_text;
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
                selectItemDataArrayList = new ArrayList<FoodData>();
                Log.d("jsonArray", String.valueOf(jsonArray));
                int count = 0;
                String FOOD_SEQ, FOOD_NAME, FOOD_KIND, RECIPE_SEQ, UNIT, PCH_PRC, QTY, COST_PRC;
                while (count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    FOOD_SEQ = object.getString("FOOD_SEQ");
                    FOOD_NAME = object.getString("FOOD_NAME");
                    FOOD_KIND = object.getString("FOOD_KIND");
                    COST_PRC = object.getString("COST_PRC");
                    RECIPE_SEQ = object.getString("RECIPE_SEQ");
                    UNIT = object.getString("UNIT");
                    PCH_PRC = object.getString("PCH_PRC");
                    QTY = object.getString("QTY");
                    FoodData foodData = new FoodData(FOOD_SEQ, FOOD_NAME, FOOD_KIND, UNIT, PCH_PRC, COST_PRC, QTY, RECIPE_SEQ);
                    selectItemDataArrayList.add(foodData);
                    count++;
                }
                ListEvent();
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
    }
}
