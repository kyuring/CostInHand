package org.techtown.costinhand;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UserStatusCountRequest extends StringRequest {
    final static private String URL = "http://kyuna58.cafe24.com/CostInHand/UserStatus.php";
    private Map<String, String> parameters;

    public UserStatusCountRequest(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        Log.d("Status parameter", String.valueOf(parameters));
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return parameters;
    }
}
