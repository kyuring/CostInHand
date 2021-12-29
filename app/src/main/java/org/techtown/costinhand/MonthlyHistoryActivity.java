package org.techtown.costinhand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MonthlyHistoryActivity extends AppCompatActivity {
    ArrayList<SelectItemData> selectItemDataArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Spinner monthly_year_spinner = (Spinner) findViewById(R.id.monthly_year_spinner);
        ArrayAdapter<CharSequence> year_adapter = ArrayAdapter.createFromResource(this, R.array.year_array, android.R.layout.simple_spinner_item);
        year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthly_year_spinner.setAdapter(year_adapter);

        Spinner monthly_month_spinner = (Spinner) findViewById(R.id.monthly_month_spinner);
        ArrayAdapter<CharSequence> month_adapter = ArrayAdapter.createFromResource(this, R.array.month_array, android.R.layout.simple_spinner_item);
        month_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthly_month_spinner.setAdapter(month_adapter);

        this.InitMonthlyData();
        ListView listView = (ListView) findViewById(R.id.monthly_company_listView);
        final ReceiptAdapter receiptAdapter = new ReceiptAdapter(this, selectItemDataArrayList);
        listView.setAdapter(receiptAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

    public void InitMonthlyData(){
        selectItemDataArrayList = new ArrayList<SelectItemData>();
        selectItemDataArrayList.add(new SelectItemData("호박","10"));
        selectItemDataArrayList.add(new SelectItemData("양파","5"));
        selectItemDataArrayList.add(new SelectItemData("고추","7"));
    }
}