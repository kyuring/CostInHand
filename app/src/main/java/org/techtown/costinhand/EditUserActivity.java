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
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EditUserActivity extends AppCompatActivity {

    private String menu_name;
    Button button_editUser_cancel, button_editUser_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        // 툴바 세팅
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        Intent intent = getIntent();
        menu_name = intent.getExtras().getString("buttonName");
        Log.d("intent", menu_name);
        // 디자인 수정
        EditDesign(menu_name);
        
        //button 이벤트 추가
        EventButton();

    }
    void EditDesign(String name) {
        LinearLayout Linear_password, Linear_newPassword,Linear_storeName ,Linear_id, Linear_email;
        Linear_password = (LinearLayout) findViewById(R.id.Linear_password);
        Linear_newPassword = (LinearLayout) findViewById(R.id.Linear_newPassword);
        Linear_storeName = (LinearLayout) findViewById(R.id.Linear_storeName);
        Linear_id = (LinearLayout) findViewById(R.id.Linear_id);
        Linear_email = (LinearLayout) findViewById(R.id.Linear_email);
        TextView edit_user_title = (TextView) findViewById(R.id.edit_user_title);
        TextView edit_remove_text = (TextView) findViewById(R.id.edit_remove_text);

        // 디자인 수정
        if(name.equals("editInfo")) {
            Linear_password.setVisibility(View.GONE);
            Linear_newPassword.setVisibility(View.GONE);
            edit_remove_text.setVisibility(View.GONE);
        } else if(name.equals("editPassword")) {
            edit_user_title.setText("비밀번호 수정중...");
            Linear_email.setVisibility(View.GONE);
            Linear_id.setVisibility(View.GONE);
            Linear_storeName.setVisibility(View.GONE);
            edit_remove_text.setVisibility(View.GONE);
        } else if(name.equals("removeUser")) {
            edit_user_title.setText("회원탈퇴");
            edit_remove_text.setText("회원 탈퇴를 위해 비밀번호를 입력해주세요");
            Linear_newPassword.setVisibility(View.GONE);
            Linear_email.setVisibility(View.GONE);
            Linear_id.setVisibility(View.GONE);
            Linear_storeName.setVisibility(View.GONE);
        }
    }
    void EventButton() {
        button_editUser_cancel = (Button) findViewById(R.id.button_editUser_cancel);
        button_editUser_ok = (Button) findViewById(R.id.button_editUser_ok);

        button_editUser_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUserActivity.super.onBackPressed();
            }
        });
        button_editUser_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUserActivity.super.onBackPressed();
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
}