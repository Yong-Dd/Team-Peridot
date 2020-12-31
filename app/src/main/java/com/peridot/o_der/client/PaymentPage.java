package com.peridot.o_der.client;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);

        //리싸이클러뷰 LinearLayoutManager 설정
        RecyclerView recyclerView = findViewById(R.id.payment_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        PaymentAdapter adapter = new PaymentAdapter();

        //임의 자료 추가
        adapter.addItem(new Payment("아메리카노","3000원"));
        adapter.addItem(new Payment("카페라떼","4500원"));
        adapter.addItem(new Payment("당근 케익","5500원"));

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
    }
}