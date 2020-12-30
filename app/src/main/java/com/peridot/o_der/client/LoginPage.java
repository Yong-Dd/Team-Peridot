package com.peridot.o_der.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    TextView idText;
    TextView pwText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        idText = findViewById(R.id.idText);
        pwText = findViewById(R.id.pwText);

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
    private void LogInCheck(String id, String pw){
        //db 연결시 작성

        //id, pw 작성이 안되어 있으면 넘어가지 않도록 함
        if(id.equals("") || pw.equals("")){
            Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 작성해주세요",Toast.LENGTH_SHORT).show();
        }
    }
}