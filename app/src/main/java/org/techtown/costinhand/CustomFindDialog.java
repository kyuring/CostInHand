package org.techtown.costinhand;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomFindDialog extends Dialog {
    private Context mContext;
    ArrayList<SelectItemData> selectItemDataArrayList;
    private String callName;
    private String company_name;

    public CustomFindDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom_find);

        // 초기값 세팅
        this.InitSelectData();

        Button button_find = (Button) findViewById(R.id.button_find);
        button_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ListView listView = (ListView) findViewById(R.id.select_dialog_listview);

        final SelectAdapter selectAdapter = new SelectAdapter(this, selectItemDataArrayList);
        listView.setAdapter(selectAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("선택","선택");
                Log.d("선택", String.valueOf(selectItemDataArrayList.size()));
            }
        });

        Button saveButton = findViewById(R.id.btnSave);
        Button cancelButton = findViewById(R.id.btnCancle);
        // 확인버튼
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo - 다음 액티비티로 이동시키기
                dismiss();
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

    public void InitSelectData(){
        selectItemDataArrayList = new ArrayList<SelectItemData>();
        selectItemDataArrayList.add(new SelectItemData("호박","10"));
        selectItemDataArrayList.add(new SelectItemData("양파","5"));
    }
}
