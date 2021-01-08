package com.peridot.o_der.client;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DessertRequest extends JsonObjectRequest {
    //php url
    final static  private String URL="http://teamperidot.dothome.co.kr/dessert.php";

    public DessertRequest(JSONObject request, Response.Listener<JSONObject> listener) {
        super(URL,request,listener,null);

    }

}
