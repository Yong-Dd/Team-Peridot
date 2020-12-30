package com.peridot.o_der.client;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageView = findViewById(R.id.imageView2);
        Drawable alpha1 = imageView.getBackground();
        alpha1.setAlpha(100);

        //로그인 버튼 - 로그인 화면으로 이동
        Button login_button = findViewById(R.id.close_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //애니메이션 효과
                overridePendingTransition(R.anim.login_page_slide_in_right,R.anim.login_page_slide_out_left);
            }
        });

        //결제 버튼 - 결제 화면으로 이동
        Button paymentButton = findViewById(R.id.payment);
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //애니메이션 효과
                overridePendingTransition(R.anim.login_page_slide_in_right,R.anim.login_page_slide_out_left);
            }
        });

        //주문 버튼 - 주문 화면으로 이동
        Button orderButton = findViewById(R.id.order);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //애니메이션 효과
                overridePendingTransition(R.anim.login_page_slide_in_right,R.anim.login_page_slide_out_left);
            }
        });
    }
}