package org.techtown.costinhand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ReceiptAddActivity extends AppCompatActivity {
    ArrayList<ReceiptAddData> receiptAddDataArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("activity_receipt_add!!", "test!!!!!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_add);

        // 툴바 세팅
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        EditText EditText_select_day = (EditText) findViewById(R.id.EditText_select_day);
        EditText_select_day.setText(intent.getExtras().getString("select_day"));

        // 거래처 찾기
        TextView button_receipt_company = (TextView) findViewById(R.id.button_receipt_company);
        button_receipt_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomFindDialog customFindDialog = new CustomFindDialog(v.getContext());
                customFindDialog.show();
            }
        });

        // 리스트 뷰 세팅
        receiptAddDataArrayList = new ArrayList<ReceiptAddData>();
        ListView listView = (ListView) findViewById(R.id.receipt_add_list);
        final ReceiptAddAdapter receiptAddAdapter = new ReceiptAddAdapter(this, receiptAddDataArrayList);
        listView.setAdapter(receiptAddAdapter);

        TextView receipt_add = (TextView) findViewById(R.id.receipt_add);
        receipt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiptAddDataArrayList.add(new ReceiptAddData());
                receiptAddAdapter.notifyDataSetChanged();
            }
        });

        //취소 버튼
        Button button_receipt_cancel = (Button) findViewById(R.id.button_receipt_cancel);
        button_receipt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiptAddActivity.super.onBackPressed();
            }
        });

        //추가 버튼
        Button button_receipt_add = (Button) findViewById(R.id.button_receipt_add);
        button_receipt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiptAddActivity.super.onBackPressed();
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
}