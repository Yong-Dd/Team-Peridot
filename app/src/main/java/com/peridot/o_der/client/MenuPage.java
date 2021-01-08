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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuPage extends AppCompatActivity {

    public static Context context_menu; //다른 액티비티에게 변수를 줄 수 있음
    Animation translateUpAnim; //orderbutton 올라오는 애니메이션, Fragment
    Animation translateDownAnim; //orderbutton 내려가는 애니메이션, Fragment
    MenuFragment menuFragment;
    static int count ;
    Button orderbutton;
    Button menuPlusButton;

    //각 리싸이클러뷰 포지션
    static int coffee_position;
    static int dessert_position;
    static int tea_position;
    //Payment Page에 넘길 주문 리스트
    static ArrayList<Payment> paymentList;

    static CoffeeAdapter coffeeAdapter;
    static DisertAdapter disertAdapter;
    static TeaAdapter teaAdapter;

    //MenuFragment 전달 위함
    static ArrayList<Integer> db_coffeePrice;
    static ArrayList<Integer> db_dessertPrice;
    static ArrayList<Integer> db_teaPrice;

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

        db_coffeePrice = new ArrayList<>();
        db_dessertPrice = new ArrayList<>();
        db_teaPrice = new ArrayList<>();
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
        coffeeAdapter = new CoffeeAdapter();//커피어뎁터 생성

        //커피 db연결, 리사이클러뷰 add
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("response");
                    JSONObject jsonInnerObject;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonInnerObject = new JSONObject(jsonArray.get(i).toString());
                        String coffeeId = jsonInnerObject.getString("COFFEE_ID");
                        String coffeeName = jsonInnerObject.getString("COFFEE_NAME");
                        int inDB_coffeePrice = jsonInnerObject.getInt("COFFEE_PRICE");
                        String coffeePrice = inDB_coffeePrice + "원";
                        Log.d("coffeeName", coffeeName + "," + coffeeId);
                        coffeeAdapter.addItem(new Coffee(coffeeName, coffeePrice));

                        //MenuFragment 전달 위함(리싸이클러뷰의 position과 i가 일치하게 됨)
                        db_coffeePrice.add(i,inDB_coffeePrice);

                    }
                    coffeerecyclerView.setAdapter(coffeeAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //coffee table 요청
        CoffeeRequest coffeeRequest = new CoffeeRequest(null,responseListener);
        RequestQueue coffee_queue = Volley.newRequestQueue(MenuPage.this);
        coffee_queue.add(coffeeRequest);


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
        disertAdapter = new DisertAdapter();

        //디저트 db연결, 리사이클러뷰 add
        Response.Listener<JSONObject> responseListener2 = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("response");
                    JSONObject jsonInnerObject;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonInnerObject = new JSONObject(jsonArray.get(i).toString());
                        int dessertId = jsonInnerObject.getInt("DESSERT_ID");
                        String dessertName = jsonInnerObject.getString("DESSERT_NAME");
                        int inDB_dessertPrice = jsonInnerObject.getInt("DESSERT_PRICE");
                        String dessertPrice = inDB_dessertPrice + "원";
                        disertAdapter.addItem(new Disert(dessertName, dessertPrice));

                        //MenuFragment 전달 위함(리싸이클러뷰의 position과 i가 일치하게 됨)
                        db_dessertPrice.add(i,inDB_dessertPrice);

                    }
                    disertrecyclerView.setAdapter(disertAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DessertRequest dessertRequest = new DessertRequest(null,responseListener2);
        RequestQueue dessert_queue = Volley.newRequestQueue(MenuPage.this);
        dessert_queue.add(dessertRequest);

        //디저트 터치 리스너
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
        teaAdapter = new TeaAdapter();

        //티 db연결, 리사이클러뷰 add
        Response.Listener<JSONObject> responseListener3 = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("response");
                    JSONObject jsonInnerObject;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonInnerObject = new JSONObject(jsonArray.get(i).toString());
                        int teaId = jsonInnerObject.getInt("TEA_ID");
                        String teaName = jsonInnerObject.getString("TEA_NAME");
                        int inDB_teaPrice = jsonInnerObject.getInt("TEA_PRICE");
                        String teaPrice = inDB_teaPrice + "원";
                        teaAdapter.addItem(new Tea(teaName, teaPrice));

                        //MenuFragment 전달 위함(리싸이클러뷰의 position과 i가 일치하게 됨)
                        db_teaPrice.add(i,inDB_teaPrice);

                    }
                    tearecyclerView.setAdapter(teaAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        TeaRequest teaRequest = new TeaRequest(null,responseListener3);
        RequestQueue tea_queue = Volley.newRequestQueue(MenuPage.this);
        tea_queue.add(teaRequest);

        //tea touch listener
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

        //PaymentPage에 전달한 메뉴 이름
        String itemName = "";

        //여러 메뉴 선택시, 메뉴 중복을 피하기 위해 각 position을 -1로 변경
        Log.d("positionsss","size: "+coffeeAdapter.getItemCount());
        if(coffee_position>-1){
            itemName = coffeeAdapter.getItem(coffee_position).getName();
            coffee_position = -1;
        }else if(dessert_position>-1){
            itemName = disertAdapter.getItem(dessert_position).getName();
            dessert_position = -1;
        }else if(tea_position>-1){
            itemName = teaAdapter.getItem(tea_position).getName();
            tea_position = -1;
        }

        if(!itemName.equals("")){
            Log.d("paymentList","checkItem add, itemName: "+itemName);
            paymentList.add(new Payment(itemName, price));
        }
    }

}