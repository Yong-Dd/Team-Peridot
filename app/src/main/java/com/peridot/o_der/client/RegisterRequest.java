package com.peridot.o_der.client;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;



//회원가입 요청

public class RegisterRequest extends StringRequest {

    final static  private String URL = "http://teamperidot.dothome.co.kr/register.php"; //아직 DB파일이 없으므로 샘플로 사이트 첨부
    private Map<String, String> parameters;

    public RegisterRequest(String ID, String PW, String NAME, String TEL, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("ID",ID);
        parameters.put("PW",PW);
        parameters.put("NAME",NAME);
        parameters.put("TEL",TEL);

    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}
