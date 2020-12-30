package com.peridot.o_der.client;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MenuPage extends AppCompatActivity {

    Animation translateUpAnim; //orderbutton 올라오는 애니메이션, Fragment
    Animation translateDownAnim; //orderbutton 내려가는 애니메이션, Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page);

        RecyclerView coffeerecyclerView = findViewById(R.id.coffeerecyclerView);//커피리사이클러뷰
        RecyclerView disertrecyclerView = findViewById(R.id.disertrecyclerView);//디저트리사이클러뷰
        RecyclerView tearecyclerView = findViewById(R.id.tearecyclerView);//티리사이클러뷰

        translateUpAnim = AnimationUtils.loadAnimation(this, R.anim.translate_up); //애니메이션 불러오기
        translateDownAnim = AnimationUtils.loadAnimation(this, R.anim.translate_down); //애니메이션 불러오기

        SlidingButtonAnimationListener animationListener = new SlidingButtonAnimationListener(); //애니메이션 리스너 생성자
        translateDownAnim.setAnimationListener(animationListener);// 애니메이션에 리스너 연결
        translateUpAnim.setAnimationListener(animationListener);// 애니메이션에 리스너 연결

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);// 리니어레이아웃매니저 생성
        coffeerecyclerView.setLayoutManager(layoutManager);//커피리사이클러뷰에 리니어레이아웃매니저 연결
        CoffeeAdapter coffeeAdapter = new CoffeeAdapter();//커피어뎁터 생성

        coffeeAdapter.addItem(new Coffee("아메리카노", "3000원"));//커피어뎁터에 내용 추가
        coffeeAdapter.addItem(new Coffee("카푸치노", "5000원"));//커피어뎁터에 내용 추가
        coffeeAdapter.addItem(new Coffee("카라멜 마끼야또", "4000원"));//커피어뎁터에 내용 추가

        coffeerecyclerView.setAdapter(coffeeAdapter); //커피리사이클러뷰에 커피어뎁터 내용 넣기

        final GestureDetector gestureDetector = new GestureDetector(MenuPage.this,new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }
        }); //onInterceptTouchEvent는 기본적으로 누르고 때고를 인식하여 2번 눌리는 효과가 생김, 그래서 onSingleTapUp을 넣어 1번 인식하는걸로 바꿈


        coffeerecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = coffeerecyclerView.findChildViewUnder(e.getX(), e.getY());//클릭시 X,Y좌표 구하기
                int position = coffeerecyclerView.getChildAdapterPosition(child);//해당 좌표가 몇번째 리사이클러뷰인지

                Button orderbuttom = findViewById(R.id.orderbutton);
                if(child!=null&&gestureDetector.onTouchEvent(e)){
                orderbuttom.setVisibility(View.VISIBLE);//버튼 보이기, defualt : gone
                Log.d("position", "{" + position + "]");
                }

                orderbuttom.startAnimation(translateUpAnim);//버튼에 up 애니메이션 적용
                return false;
            } //클릭시 발생하는 곳

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        }); //커피리사이클러뷰 클릭시 발생하는 이벤트 처리

        LinearLayoutManager layoutManager1 =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        disertrecyclerView.setLayoutManager(layoutManager1);
        DisertAdapter disertAdapter = new DisertAdapter();

        disertAdapter.addItem(new Disert("치즈케이크", "5000원"));
        disertAdapter.addItem(new Disert("초코케이크", "5000원"));
        disertAdapter.addItem(new Disert("슈크림", "6000원"));

        disertrecyclerView.setAdapter(disertAdapter);
        disertrecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = coffeerecyclerView.findChildViewUnder(e.getX(), e.getY());
                int position = coffeerecyclerView.getChildAdapterPosition(child);

                Button orderbuttom = findViewById(R.id.orderbutton);

                if(child!=null&&gestureDetector.onTouchEvent(e)){
                orderbuttom.setVisibility(View.VISIBLE);
                Log.d("position", "{" + position + "]");}

                orderbuttom.startAnimation(translateUpAnim);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        LinearLayoutManager layoutManager2 =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        tearecyclerView.setLayoutManager(layoutManager2);
        TeaAdapter teaAdapter = new TeaAdapter();

        teaAdapter.addItem(new Tea("얼그레이티", "5000원"));
        teaAdapter.addItem(new Tea("아이스티", "2000원"));
        teaAdapter.addItem(new Tea("레몬녹차", "3000원"));


        tearecyclerView.setAdapter(teaAdapter);
        tearecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = coffeerecyclerView.findChildViewUnder(e.getX(), e.getY());
                int position = coffeerecyclerView.getChildAdapterPosition(child);

                Button orderbuttom = findViewById(R.id.orderbutton);

                if(child!=null&&gestureDetector.onTouchEvent(e)){
                orderbuttom.setVisibility(View.VISIBLE);
                Log.d("position", "{" + position + "]");}

                orderbuttom.startAnimation(translateUpAnim);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        Button orderbutton = findViewById(R.id.orderbutton);
        orderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderbutton.startAnimation(translateDownAnim);//버튼에 down 애니메이션 적용
                orderbutton.setVisibility(v.GONE);//버튼 안보임
            }
        });//누르면 결재페이지로 넘어가기
    }
    private class SlidingButtonAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    } //애니메이션리스너 implements Animationi.AnimationListener가 핵심
}