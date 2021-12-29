package org.techtown.costinhand;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FoodRequest extends StringRequest {

    final static private String URL = "http://kyuna58.cafe24.com/CostInHand/Food.php";
    private Map<String, String> parameters;

    public FoodRequest(String userID, String food_name, String price, String qty, String unit, String mode, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        Log.d("userID", userID);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("unit", unit);
        parameters.put("pch_prc", price);
        parameters.put("qty", qty);
        parameters.put("name", food_name);
        parameters.put("Mode", mode);

        Log.d("Register parameter", String.valueOf(parameters));
    }

    public FoodRequest(String userID, String food_name, String price, String qty, String unit, String mode, String food_seq, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        Log.d("userID", userID);
        parameters = new HashMap<>();
        parameters.put("FOOD_SEQ", food_seq);
        parameters.put("userID", userID);
        parameters.put("unit", unit);
        parameters.put("pch_prc", price);
        parameters.put("qty", qty);
        parameters.put("name", food_name);
        parameters.put("Mode", mode);
        Log.d("Register parameter", String.valueOf(parameters));
    }

    public FoodRequest(String mode, String food_seq, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("FOOD_SEQ", food_seq);
        parameters.put("Mode", mode);
        Log.d("Register parameter", String.valueOf(parameters));
    }

    public FoodRequest(String mode, String Food_name, String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("name", Food_name);
        parameters.put("Mode", mode);
        parameters.put("userID", userID);
        Log.d("Register parameter", String.valueOf(parameters));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
