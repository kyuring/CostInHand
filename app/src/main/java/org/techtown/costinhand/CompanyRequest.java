package org.techtown.costinhand;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CompanyRequest extends StringRequest {

    final static private String URL = "http://kyuna58.cafe24.com/CostInHand/Company.php";
    private Map<String, String> parameters;

    public CompanyRequest(String userID, String CO_NAME, String CEO_NAME, String TEL, String PHONE, String ETC, String mode, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        Log.d("userID", userID);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("CO_NAME", CO_NAME);
        parameters.put("CEO_NAME", CEO_NAME);
        parameters.put("TEL", TEL);
        parameters.put("PHONE", PHONE);
        parameters.put("ETC", ETC);
        parameters.put("mode", mode);
        Log.d("Register parameter", String.valueOf(parameters));
    }

    public CompanyRequest(String userID, String CO_NAME, String CEO_NAME, String TEL, String PHONE, String ETC, String CO_SEQ, String mode, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        Log.d("userID", userID);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("CO_NAME", CO_NAME);
        parameters.put("CEO_NAME", CEO_NAME);
        parameters.put("TEL", TEL);
        parameters.put("PHONE", PHONE);
        parameters.put("ETC", ETC);
        parameters.put("CO_SEQ", CO_SEQ);
        parameters.put("mode", mode);
        Log.d("Register parameter", String.valueOf(parameters));
    }

    public CompanyRequest(String mode, String CO_SEQ, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("CO_SEQ", CO_SEQ);
        parameters.put("mode", mode);
        Log.d("Register parameter", String.valueOf(parameters));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
