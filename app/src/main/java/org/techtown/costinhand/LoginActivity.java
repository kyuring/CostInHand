package org.techtown.costinhand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private String LoginId = "";
    private String USERNAME ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editID = (EditText) findViewById(R.id.editID);
        EditText editPassword = (EditText) findViewById(R.id.editPassword);

        // Register Button Event
        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity("Register");

            }
        });

        // Login Button Event
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ID = editID.getText().toString();
                String PW = editPassword.getText().toString();
                if(ID.equals("") || PW.equals("")){
                    alert();
                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                Log.d("response", String.valueOf(jsonResponse));
                                boolean success = jsonResponse.getBoolean("success");
                                if(success) {
                                    LoginId = ID;
                                    USERNAME = jsonResponse.getString("NAME");
                                    moveActivity("Login");
                                } else {
                                    alert();
                                }
                                Log.d("success", String.valueOf(success));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    LoginRequest loginRequest = new LoginRequest(ID, PW, responseListener);
                    loginRequest.setShouldCache(false);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(loginRequest);
                }
            }
        });
    }
    public void moveActivity(String buttonName){
        Intent intent = null;
        if(buttonName.equals("Login")){
            intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("userID", LoginId);
            intent.putExtra("userName",USERNAME);
        } else if(buttonName.equals("Register")) {
            intent = new Intent(LoginActivity.this, RegisterActivity.class);
        }
        LoginActivity.this.startActivity(intent);
        overridePendingTransition(0,0);
    }

    public void alert(){
        AlertDialogActivity dlg = new AlertDialogActivity(LoginActivity.this, "아이디와 비밀번호를 확인해주세요", "경고", 2);
        dlg.setCancelable(false);
        dlg.show();
    }

}