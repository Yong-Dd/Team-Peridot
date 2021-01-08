package com.peridot.o_der.client;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MenuRequest extends StringRequest {
    //php url
    final static  private String URL="http://teamperidot.dothome.co.kr/coffee.php";
    private Map<String,String> map;

    public MenuRequest(String COFFEE_ID, Response.Listener<String> listener) {
        super(Method.POST,URL, listener,null);

        map = new HashMap<>();
    //    map.put("tableName",tableName);
        map.put("COFFEE_ID",COFFEE_ID);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
