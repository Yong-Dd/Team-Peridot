package com.peridot.o_der.client;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;



//회원가입 요청

public class RegisterRequest extends StringRequest {

    final static  private String URL = "http://simssbook9.cafe24.com/Register.php"; //아직 DB파일이 없으므로 샘플로 사이트 첨부
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userName, String userTel, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
        parameters.put("userName",userName);
        parameters.put("userTel",userTel);

    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
