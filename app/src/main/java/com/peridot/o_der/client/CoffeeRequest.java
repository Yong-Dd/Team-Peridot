package com.peridot.o_der.client;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CoffeeRequest extends JsonObjectRequest {
    //php url
    final static  private String URL="http://teamperidot.dothome.co.kr/coffee1.php";

    public CoffeeRequest(JSONObject request, Response.Listener<JSONObject> listener) {
        super(URL,request,listener,null);
    }


}
