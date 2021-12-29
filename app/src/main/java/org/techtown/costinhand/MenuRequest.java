package org.techtown.costinhand;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MenuRequest extends StringRequest {

    final static private String URL = "http://kyuna58.cafe24.com/CostInHand/Menu.php";
    private Map<String, String> parameters;

    public MenuRequest(String mode, String Menu_name, String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("MENU_NAME", Menu_name);
        parameters.put("MODE", mode);
        parameters.put("USERID", userID);
        Log.d("Register parameter", String.valueOf(parameters));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
