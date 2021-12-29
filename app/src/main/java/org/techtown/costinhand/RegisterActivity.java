package org.techtown.costinhand;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    String userID;
    String userEmail;
    String userPassword;
    String userPasswordCheck;
    String userStoreName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button button_register = (Button) findViewById(R.id.button_register);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IDSetting();
                boolean check = EditCheck();
                if(check) {
                    // 비밀번호랑 비밀번호확인이랑랑 같은지 확인
                    if(!(userPassword.equals(userPasswordCheck))){
                        alert("비밀번호와 비밀번호 확인이 다릅니다. 확인해주세요.");
                    }
                }

                // 리스너 생성
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Log.d("Register response", response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog alert = new AlertDialog.Builder( RegisterActivity.this )
                                        .setTitle( "확인" )
                                        .setMessage( "회원가입이 완료되었습니다.")
                                        .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                                            @Override public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                overridePendingTransition(0,0);
                                            }
                                        }).show();
                            } else {
                                alert("회원가입을 다시 시도해주세요.");
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                Log.d("here id", userID);
                // Volley 사용하여 보내기
                RegisterRequest registerRequest = new RegisterRequest(userID, userEmail, userPassword, userStoreName, responseListener);
                registerRequest.setShouldCache(false);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(registerRequest);
            }
        });
    }
    // ID 세팅
    void IDSetting(){
        EditText editID = (EditText) findViewById(R.id.editID);
        EditText editEmail = (EditText) findViewById(R.id.editEmail);
        EditText editPassword = (EditText) findViewById(R.id.editPassword);
        EditText editPasswordCheck = (EditText) findViewById(R.id.editPasswordCheck);
        EditText editStoreName = (EditText) findViewById(R.id.editStoreName);

        userID = editID.getText().toString();
        userEmail = editEmail.getText().toString();
        userPassword = editPassword.getText().toString();
        userPasswordCheck = editPasswordCheck.getText().toString();
        userStoreName = editStoreName.getText().toString();
    }
    // EditText 확인
    boolean EditCheck(){
        if(userID.equals("")) {
            Log.d("userID", userID);
            alert("아이디를 입력해주세요.");
            return false;
        } else if(userEmail.equals("")) {
            alert("이메일을 입력해주세요.");
            return false;
        } else if(userPassword.equals("")) {
            alert("비밀번호를 입력해주세요.");
            return false;
        } else if(userPasswordCheck.equals("")) {
            alert("비밀번호 확인을 입력해주세요.");
            return false;
        } else if(userStoreName.equals("")) {
            alert("가게이름을 입력해주세요.");
            return false;
        }
        return true;
    }
    public void alert(String detail){
        AlertDialogActivity dlg = new AlertDialogActivity(RegisterActivity.this, detail, "경고", 1);
        dlg.setCancelable(false);
        dlg.show();
    }
}
