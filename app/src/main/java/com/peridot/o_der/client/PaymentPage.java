package com.peridot.o_der.client;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PaymentPage extends AppCompatActivity {

    //coupon 종류
    String[] coupons = {"선택 안함", "첫 가입 축하 쿠폰 - 20%","생일 기념 쿠폰 - 10%", "오늘의 메뉴 할인 - 5%"};

    //결제할 총 금액
    static int choice_price;

    PaymentAdapter adapter;
    RecyclerView recyclerView;

    ArrayList<Payment> paymentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);


        //리싸이클러뷰 LinearLayoutManager 설정
        recyclerView = (RecyclerView) findViewById(R.id.payment_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PaymentAdapter();

        //주문리스트 가져올 리스트
        paymentList = new ArrayList<>();



        //MenuPage에서 주문리스트(paymentList) 가져와서 recyclerview 추가(intent로 보낸 내용)
        paymentList =  getIntent().getParcelableArrayListExtra("paymentList");
        if(paymentList!=null){
            for(int i =0; i<paymentList.size(); i++){
                String coffeeName = paymentList.get(i).coffeeName;
                int coffeePrice = paymentList.get(i).coffeePrice;
                adapter.addItem(new Payment(coffeeName,coffeePrice ));

                //결제할 금액 계산
                choice_price += coffeePrice;
            }
            adapter.notifyDataSetChanged();
        }


        //리싸이클러뷰 adapter 설정
        recyclerView.setAdapter(adapter);

        // X버튼 클릭 - 전 페이지로 이동
        Button close_button = findViewById(R.id.close_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.login_page_slide_in_left,R.anim.login_page_slide_out_right);
            }
        });

        //결제하기 버튼 - Toast 띄우기
        Button payment_button = findViewById(R.id.payment_button);
        payment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // <쿠폰 선택 - spinner 구현>

        //coupon spinner adapter setting
        Spinner payment_coupon_spinner = findViewById(R.id.payment_coupon_spinner);
        ArrayAdapter<String> coupon_adapter = new ArrayAdapter<String>(this, R.layout.payment_spinner_list,coupons);
        coupon_adapter.setDropDownViewResource(R.layout.payment_spinner_list);
        payment_coupon_spinner.setAdapter(coupon_adapter);

        //coupon spinner listener
        payment_coupon_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //쿠폰 할인 계산 위한 메서드
                double choice_coupon = couponChoice(position);

                //쿠폰 할인 가격, 총 가격 계산
                int discount_price = (int) Math.floor(choice_price * choice_coupon);
                int total_price = choice_price - discount_price;

                //결제하기 버튼에 가격 추가
                String payment_price = total_price+"원 결제하기";
                if(choice_price!=0){
                    payment_button.setText(payment_price);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private double couponChoice(int position){
        double choice = 0.0;

        //쿠폰 할인 선택 (String[] coupons)
        if(position == 1){
            choice = 0.2;
        }else if(position == 2){
            choice = 0.1;
        }else if(position ==3){
            choice = 0.05;
        }
        return choice;
    }

}