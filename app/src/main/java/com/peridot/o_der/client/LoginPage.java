package com.peridot.o_der.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPage extends AppCompatActivity {

    TextView idText;
    TextView pwText;
    public static Context context_login;
    static boolean login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        idText = findViewById(R.id.idText);
        pwText = findViewById(R.id.pwText);

        context_login = this;

        //로그인 버튼
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idText.getText().toString();
                String pw = pwText.getText().toString();

                //id, pw 확인
                LogInCheck(id, pw);
            }
        });

        //회원가입 버튼
        Button joinButton = findViewById(R.id.joinButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 이동
                Intent intent = new Intent(getApplicationContext(), JoinPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.login_page_slide_in_right,R.anim.login_page_slide_out_left);
            }
        });

        //취소 버튼(X)
        ImageButton cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.login_page_slide_in_left,R.anim.login_page_slide_out_right);
            }
        });

    }
    private void LogInCheck(String ID, String PW){
        String userID;
        String userPass;

        //id, pw 작성이 안되어 있으면 넘어가지 않도록 함
        if(ID.equals("") || PW.equals("")){
            Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 작성해주세요",Toast.LENGTH_SHORT).show();
        }

        Log.d("data1111","LogInCheck called");

        //db 연결 부분
        Response.Listener<String> responseListener=new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("data1111","LogInCheck onResponse In");
                try {

                    JSONObject jasonObject=new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    boolean success=jasonObject.getBoolean("success");

                    //로그인 성공
                    if (success) {
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                        login = true;
                        //로그인 고객이름 Main activity로 전달
                        String customerName = jasonObject.getString("NAME");
                        int customerId = jasonObject.getInt("CUSTOMER_ID");
                        String tel = jasonObject.getString("TEL");
                        Intent intent = new Intent(LoginPage.this, MainActivity.class);
                        intent.putExtra("customerName", customerName);
                        intent.putExtra("customerId",customerId);
                        intent.putExtra("tel", tel);
                        startActivity(intent);


                    }
                    //로그인 실패
                    else{
                        Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        return;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        //LogInRequest로 작성한 id, pw 전달
        LogInRequest loginRequest=new LogInRequest(ID,PW,responseListener);
        RequestQueue queue= Volley.newRequestQueue(LoginPage.this);
        queue.add(loginRequest);
    }

}