package com.peridot.o_der.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MenuPage extends AppCompatActivity {

    public static Context context_menu; //다른 액티비티에게 변수를 줄 수 있음
    Animation translateUpAnim; //orderbutton 올라오는 애니메이션, Fragment
    Animation translateDownAnim; //orderbutton 내려가는 애니메이션, Fragment
    MenuFragment menuFragment;
    static int count ;
    Button orderbutton;
    Button menuPlusButton;

    //****변수 추가
    public static int coffee_position;
    static int dessert_position;
    static int tea_position;
    static ArrayList<Payment> paymentList;

    String coffeeId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page);

        /*추가*/
        paymentList= new ArrayList<>();
        coffee_position = -1;
        dessert_position = -1;
        tea_position = -1;
        //****여기까지

        context_menu = this; //다른 액티비티에서 MenuPage 변수를 사용할 수 있음

        RecyclerView coffeerecyclerView = findViewById(R.id.coffeerecyclerView);//커피리사이클러뷰
        RecyclerView disertrecyclerView = findViewById(R.id.disertrecyclerView);//디저트리사이클러뷰
        RecyclerView tearecyclerView = findViewById(R.id.tearecyclerView);//티리사이클러뷰

        menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menufragment);

        translateUpAnim = AnimationUtils.loadAnimation(this, R.anim.translate_up); //애니메이션 불러오기
        translateDownAnim = AnimationUtils.loadAnimation(this, R.anim.translate_down); //애니메이션 불러오기

        SlidingButtonAnimationListener animationListener = new SlidingButtonAnimationListener(); //애니메이션 리스너 생성자
        translateDownAnim.setAnimationListener(animationListener);// 애니메이션에 리스너 연결
        translateUpAnim.setAnimationListener(animationListener);// 애니메이션에 리스너 연결

        LinearLayout fragmentpage = findViewById(R.id.fragmentPage);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);// 리니어레이아웃매니저 생성
        coffeerecyclerView.setLayoutManager(layoutManager);//커피리사이클러뷰에 리니어레이아웃매니저 연결
        CoffeeAdapter coffeeAdapter = new CoffeeAdapter();//커피어뎁터 생성

        //db 연결 부분
        Response.Listener<String> responseListener=new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("data1111","LogInCheck onResponse In");
                try {
                    JSONObject jasonObject=new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                  //  boolean success=jasonObject.getBoolean("success");
                    Log.d("coffeeName",Integer.toString(jasonObject.length()));

                            coffeeId = jasonObject.getString("COFFEE_ID");
                            String coffeeName = jasonObject.getString("COFFEE_NAME");
                            String coffePrice = jasonObject.getInt("COFFEE_PRICE")+"원";
                            Log.d("coffeeName",coffeeName + ","+ coffeeId);




                        coffeeAdapter.addItem(new Coffee(coffeeName, coffePrice));
                        coffeerecyclerView.setAdapter(coffeeAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        //LogInRequest로 작성한 id, pw 전달
            for(int i =1; i<4; i++){
                Log.d("positions", Integer.toString(i));
                String coffeeIds = Integer.toString(i);
                MenuRequest menuRequest = new MenuRequest(coffeeIds, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MenuPage.this);
                queue.add(menuRequest);

            }







        //수정

        //커피리사이클러뷰에 커피어뎁터 내용 넣기

        final GestureDetector gestureDetector = new GestureDetector(MenuPage.this,new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }
        }); //onInterceptTouchEvent는 기본적으로 누르고 때고를 인식하여 2번 눌리는 효과가 생김, 그래서 onSingleTapUp을 넣어 1번 인식하는걸로 바꿈

        /******추가 버튼 클릭시 position 전달*/
        coffee_position = 0;
        coffeerecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = coffeerecyclerView.findChildViewUnder(e.getX(), e.getY());//클릭시 X,Y좌표 구하기
                int position = coffeerecyclerView.getChildAdapterPosition(child);//해당 좌표가 몇번째 리사이클러뷰인지

                if(child!=null&&gestureDetector.onTouchEvent(e)){
                 coffee_position = position;
                }

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

                if(child!=null&&gestureDetector.onTouchEvent(e)){
                    dessert_position = position;
                }

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


                if(child!=null&&gestureDetector.onTouchEvent(e)){
                    tea_position = position;
                }

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        orderbutton = findViewById(R.id.orderbutton);
        orderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(),PaymentPage.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
               Log.d("paymentList",Integer.toString(paymentList.size()));
               intent.putParcelableArrayListExtra("paymentList", (ArrayList<? extends Parcelable>) paymentList);
               startActivity(intent);


            }
        });//누르면 결재페이지로 넘어가기

        Button close_btn = findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.login_page_slide_in_left,R.anim.login_page_slide_out_right);
            }
        });

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

   /**** MenuFragment에서 받은 가격과 recycler의 addOnItemTouchListener에서 받은 위치를
    arraylist(paymentList)에 저장, MenuFragment에서 추가 하는 순서대로 paymentList에 추가됨
    MenuFragment에서 넘어온 거라서 그 당시의 position으로 선택을 알 수 있음*/
    public void checkItem(int price){
        Log.d("paymentList","MenuPage, checkItem called"+price);
        String itemName = "";

        //여러 메뉴 선택시, 메뉴 중복을 피하기 위해 각 position을 -1로 변경
         switch (coffee_position){
            case 0: itemName = "아메리카노"; coffee_position = -1; break;
            case 1: itemName = "카푸치노"; coffee_position = -1; break;
            case 2: itemName = "카라멜 마끼야또"; coffee_position = -1; break;
            default: break;
        }
        switch (dessert_position){
            case 0: itemName = "치즈케이크"; dessert_position = -1; break;
            case 1: itemName = "초코케이크"; dessert_position = -1; break;
            case 2: itemName = "슈크림"; dessert_position = -1; break;
            default: break;
        }
        switch (tea_position){
            case 0: itemName = "얼그레이티"; tea_position = -1; break;
            case 1: itemName = "아이스티"; tea_position = -1; break;
            case 2: itemName = "레몬 녹차";tea_position = -1; break;
        }

        if(!itemName.equals("")){
            Log.d("paymentList","checkItem add, itemName: "+itemName);
            paymentList.add(new Payment(itemName, price));
        }
    }

}