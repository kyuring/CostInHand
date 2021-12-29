package org.techtown.costinhand;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RecipeRequest extends StringRequest {

    final static private String URL = "http://kyuna58.cafe24.com/CostInHand/Recipe.php";
    private Map<String, String> parameters;

    public RecipeRequest(String RECIPE_SEQ, String SET_NO, String RECIPE_NAME, String FOOD_SEQ, String COST_PRC, String QTY, String USERID, String buttonName, String Mode, String MENU_SEQ,Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("RECIPE_NAME", RECIPE_NAME);
        if(Mode.equals("update")) {
            parameters.put("SET_NO", SET_NO);
        }
        parameters.put("FOOD_SEQ", FOOD_SEQ);
        parameters.put("COST_PRC", COST_PRC);
        parameters.put("qty", QTY);
        parameters.put("userID", USERID);
        if(buttonName.equals("menu")) {
            parameters.put("MENU_SEQ", MENU_SEQ);
        }
        parameters.put("RECIPE_SEQ", RECIPE_SEQ);
        parameters.put("buttonName", buttonName);
        parameters.put("Mode", Mode);
        Log.d("Register parameter", String.valueOf(parameters));
    }

    public RecipeRequest(String RECIPE_SEQ, String FOOD_SEQ, String MENU_SEQ, String buttonName, String Mode, String USERID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("RECIPE_SEQ", RECIPE_SEQ);
        parameters.put("MENU_SEQ", MENU_SEQ);
        parameters.put("FOOD_SEQ", FOOD_SEQ);
        parameters.put("buttonName", buttonName);
        parameters.put("userID", USERID);
        parameters.put("Mode", Mode);

        Log.d("Register parameter", String.valueOf(parameters));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
