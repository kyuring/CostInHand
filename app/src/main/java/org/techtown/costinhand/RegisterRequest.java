package org.techtown.costinhand;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://kyuna58.cafe24.com/CostInHand/UserRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userEmail, String userPassword, String userStoreName, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        Log.d("userID", userID);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userEmail", userEmail);
        parameters.put("userPassword", userPassword);
        parameters.put("userStoreName", userStoreName);
        Log.d("Register parameter", String.valueOf(parameters));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
