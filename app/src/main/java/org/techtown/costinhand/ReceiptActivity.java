package org.techtown.costinhand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReceiptActivity extends AppCompatActivity {
    private String select_year = null;
    private String select_month = null;
    private String select_dayOfMonth = null;
    private String select_day = null;
    FloatingActionButton fab_menu1;
    ArrayList<SelectItemData> selectItemDataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        // 툴바 세팅
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 초기 날짜 - 오늘 날짜로 설정
        DateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
        select_day = dfDate.format(Calendar.getInstance().getTime());

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                select_year = String.valueOf(year);
                select_month  = String.valueOf(month + 1);
                select_dayOfMonth = String.valueOf(dayOfMonth);
                Log.d("날짜", select_year + "년" + select_month + "월" + select_dayOfMonth + "일");
                select_day = select_year + "-" + select_month + "-" + select_dayOfMonth;
            }
        });

        this.InitSelectData();

        ListView listView = (ListView) findViewById(R.id.receipt_listview);
        final ReceiptAdapter receiptAdapter = new ReceiptAdapter(this, selectItemDataArrayList);
        listView.setAdapter(receiptAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomListDialog dlg = new CustomListDialog(view.getContext(), "ReceiptActivity", selectItemDataArrayList.get(position).getSelect_item_name());
                dlg.show();
            }
        });

        // 플로팅 버튼
        // 플로팅 버튼
        fab_menu1 = findViewById(R.id.fab_menu1);
        fab_menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // intent 이동
                Intent intent = new Intent(ReceiptActivity.this, ReceiptAddActivity.class);
                intent.putExtra("select_day",select_day);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
    }
    // 툴바세팅
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
    public void InitSelectData(){
        selectItemDataArrayList = new ArrayList<SelectItemData>();
        selectItemDataArrayList.add(new SelectItemData("호박","10"));
        selectItemDataArrayList.add(new SelectItemData("양파","5"));
        selectItemDataArrayList.add(new SelectItemData("고추","7"));
    }
}