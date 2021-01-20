package com.peridot.o_der.client;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class OrderListUploadRequest extends StringRequest {
    final static  private String URL = "http://teamperidot.dothome.co.kr/orderList_upload.php"; //아직 DB파일이 없으므로 샘플로 사이트 첨부
    private Map<String, String> parameters;


    public OrderListUploadRequest(String CUSTOMER_NAME, String ORDER_DATE, String ORDER_MENU, int ORDER_PRICE, String PICKUP_TIME, String ORDER_MEMO, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("CUSTOMER_NAME",CUSTOMER_NAME);
        parameters.put("ORDER_DATE",ORDER_DATE);
        parameters.put("ORDER_MENU",ORDER_MENU);
        parameters.put("ORDER_PRICE", Integer.toString(ORDER_PRICE));
        parameters.put("PICKUP_TIME",PICKUP_TIME);
        parameters.put("ORDER_MEMO",ORDER_MEMO);

    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
