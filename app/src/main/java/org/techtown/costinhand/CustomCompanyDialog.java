package org.techtown.costinhand;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomCompanyDialog extends Dialog {
    private Context mContext;
    Button button_cancel;
    Button button_add;
    EditText dlg_company_name;
    EditText dlg_company_ceo_name;
    EditText dlg_company_tell;
    EditText dlg_company_phone;
    EditText dlg_company_etc;
    String mode;
    CompanyData companyData = null;
    // 회사명 없는 경우 생성자
    public CustomCompanyDialog(@NonNull Context context, String mode) {
        super(context);
        this.mContext = context;
        this.mode = mode;
    }
    // 회사명을 알고있는 경우의 생성자
    public CustomCompanyDialog(@NonNull Context context, CompanyData companyData, String mode) {
        super(context);
        this.mContext = context;
        this.mode = mode;
        this.companyData = companyData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_company);

        UISettings();

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("insert")) {
                    insertButtonEvt(dlg_company_name.getText().toString(), dlg_company_ceo_name.getText().toString(), dlg_company_tell.getText().toString(), dlg_company_phone.getText().toString(), dlg_company_etc.getText().toString(), getContext());
                } else if(mode.equals("update")) {
                    updateButtonEvt(companyData.getSeq(), dlg_company_name.getText().toString(), dlg_company_ceo_name.getText().toString(), dlg_company_tell.getText().toString(), dlg_company_phone.getText().toString(), dlg_company_etc.getText().toString(), getContext());
                }
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    void UISettings(){
        // 회사명이 있는 경우 처리
        dlg_company_name = findViewById(R.id.dlg_company_name);
        dlg_company_ceo_name = findViewById(R.id.dlg_company_ceo_name);
        dlg_company_tell = findViewById(R.id.dlg_company_tell);
        dlg_company_phone = findViewById(R.id.dlg_company_phone);
        dlg_company_etc = findViewById(R.id.dlg_company_etc);
        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_add = (Button) findViewById(R.id.button_add);

        if (companyData != null) {
            dlg_company_name.setText(companyData.getName());
            dlg_company_ceo_name.setText(companyData.getCeo_name());
            dlg_company_tell.setText(companyData.getTel());
            dlg_company_phone.setText(companyData.getPhone());
            dlg_company_etc.setText(companyData.getEtc());
        }
        if(mode.equals("update")) {
            button_add.setText("수정");
        }
    }
    public void insertButtonEvt(String CO_NAME, String CEO_NAME, String TEL, String PHONE_TEL, String ETC, Context context){
        if(CO_NAME.length() == 0 || CEO_NAME.length() == 0 || (TEL.length() == 0 || PHONE_TEL.length() == 0)) {
            MainActivity.alert(context, "입력해 주세요!!", "경고", 1);
        } else {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        Log.d("response", response);
                        JSONObject jsonResponse = new JSONObject(response);
                        String CO_SEQ = jsonResponse.getString("CO_SEQ");
                        boolean success = jsonResponse.getBoolean("success");
                        if(!success) {
                            MainActivity.alert(context, "재료 입력 중 오류가 발생하였습니다.", "알림", 1);
                        } else {
                            // 성공시 할 일
                            CompanyData companyData = new CompanyData(CO_NAME, CEO_NAME, CO_SEQ, TEL, PHONE_TEL, ETC);
                            ItemMenuActivity.companyDataArrayList.add(companyData);
                            ItemMenuActivity.companyAdapter.notifyDataSetChanged();
                            // 다이얼로그 닫기
                            CustomCompanyDialog.this.dismiss();
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            };
            CompanyRequest companyRequest = new CompanyRequest(MainActivity.userid, CO_NAME, CEO_NAME, TEL, PHONE_TEL, ETC, mode, responseListener);
            companyRequest.setShouldCache(false);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(companyRequest);
        }
    }
    public void updateButtonEvt(String CO_SEQ, String CO_NAME, String CEO_NAME, String TEL, String PHONE_TEL, String ETC, Context context){
        if(CO_NAME.length() == 0 || CEO_NAME.length() == 0 || (TEL.length() == 0 || PHONE_TEL.length() == 0)) {
            MainActivity.alert(context, "입력해 주세요!!", "경고", 1);
        } else {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        Log.d("response", response);
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(!success) {
                            MainActivity.alert(context, "재료 입력 중 오류가 발생하였습니다.", "알림", 1);
                        } else {
                            // 성공시 할 일
                            companyData.setName(CO_NAME);
                            companyData.setCeo_name(CEO_NAME);
                            companyData.setTel(TEL);
                            companyData.setPhone(PHONE_TEL);
                            companyData.setEtc(ETC);

                            ItemMenuActivity.companyAdapter.notifyDataSetChanged();
                            // 다이얼로그 닫기
                            CustomCompanyDialog.this.dismiss();
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            };
            CompanyRequest companyRequest = new CompanyRequest(MainActivity.userid, CO_NAME, CEO_NAME, TEL, PHONE_TEL, ETC, CO_SEQ, mode, responseListener);
            companyRequest.setShouldCache(false);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(companyRequest);
        }
    }
}
