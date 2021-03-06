package com.peridot.o_der.client;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button login_button;
    TextView customer_textView;

    public static Context context_main;
    static boolean login;

    static int CUSTOMER_ID;
    static String CUSTOMER_NAME;
    static String TEL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context_main = this;

        imageView = findViewById(R.id.menuImage);
        Drawable alpha1 = imageView.getBackground();
        alpha1.setAlpha(100);

        //로그인시 이름 textView
        customer_textView = findViewById(R.id.mainpagetext);

        //로그인 버튼 - 로그인 화면으로 이동
        login_button = findViewById(R.id.main_logInButton);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //애니메이션 효과
                overridePendingTransition(R.anim.login_page_slide_in_right, R.anim.login_page_slide_out_left);

                login = ((LoginPage)LoginPage.context_login).login;
                if (login){
                    customer_textView.setVisibility(View.GONE);
                    login_button.setText("로그인");
                    login = false;
                }
            }
        });

        //결제 버튼 - 결제 화면으로 이동
        RelativeLayout paymentLayout = findViewById(R.id.paymentLayout);
        paymentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //애니메이션 효과
                overridePendingTransition(R.anim.login_page_slide_in_right, R.anim.login_page_slide_out_left);
            }
        });

        //주문 버튼 - 주문 화면으로 이동
        RelativeLayout order = findViewById(R.id.orderLayout);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //애니메이션 효과
                overridePendingTransition(R.anim.login_page_slide_in_right, R.anim.login_page_slide_out_left);
            }
        });

        RelativeLayout insta = findViewById(R.id.instaLayout);
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InstagramPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //애니메이션 효과
                overridePendingTransition(R.anim.login_page_slide_in_right,R.anim.login_page_slide_out_left);
            }
        });

        //LogIn Page에서 성공시 넘어옴
        Intent intent = getIntent();
        customerName(intent);
    }

    //로그인시 고객 이름 설정
    private void customerName(Intent intent) {
        if (intent != null) {
            //이름 받아옴
            Bundle bundle = intent.getExtras();
            if(bundle !=null){
                String customerName = bundle.getString("customerName");
                int customerId = bundle.getInt("customerId");
                String tel = bundle.getString("tel");
                TEL = tel;
                CUSTOMER_ID = customerId;
                CUSTOMER_NAME = customerName;
                customer_textView.setVisibility(View.VISIBLE);
                customer_textView.setText("환영합니다 \n" +customerName + "님");
                login_button.setText("로그아웃");
            }
        }
    }
}
