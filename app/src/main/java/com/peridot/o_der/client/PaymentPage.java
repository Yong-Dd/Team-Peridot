package com.peridot.o_der.client;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class PaymentPage extends AppCompatActivity {

    static RequestQueue requestQueue;
    static String regId = "cAqF9C0vvwQ:APA91bFq-ZgD4-qbz7OpBXU7q3fE0n8r5XnUk247Z4dk8l7zY_pH6bOT2H3ufqXkaZhsjwodjP6mHQ3dy8oQy0eekYeueiS1HSwt9tMotNuhZcofqHauhNVG4Jrn5j2grURmnHq4PHCC";

    //coupon 종류
    String[] coupons = {"선택 안함", "첫 가입 축하 쿠폰 - 20%","생일 기념 쿠폰 - 10%", "오늘의 메뉴 할인 - 5%"};
    // 픽업 시간 Text
    TextView time_pick_Text;

    EditText memo_editText;

    //결제할 총 금액
    static int choice_price = 0;

    PaymentAdapter adapter;
    RecyclerView recyclerView;

    ArrayList<Payment> paymentList;

    static String pick_time;

    final DecimalFormat priceFormat = new DecimalFormat("###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);

        memo_editText = findViewById(R.id.memo_editText);

        //리싸이클러뷰 LinearLayoutManager 설정
        recyclerView = (RecyclerView) findViewById(R.id.payment_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PaymentAdapter();

        //주문리스트 가져올 리스트
        paymentList = new ArrayList<>();

        //time picker
        time_pick_Text = findViewById(R.id.time_picker);
        pick_time = "";

        //MenuPage에서 주문리스트(paymentList) 가져와서 recyclerview 추가(intent로 보낸 내용)
        paymentList =  getIntent().getParcelableArrayListExtra("paymentList");
        if(paymentList!=null){
            for(int i =0; i<paymentList.size(); i++){
                String coffeeName = paymentList.get(i).coffeeName;
                int coffeePrice = paymentList.get(i).coffeePrice;
                int count = paymentList.get(i).count;
                String hotIce = paymentList.get(i).hotIce;
                adapter.addItem(new Payment(coffeeName,coffeePrice,count,hotIce ));

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

        //결제하기 버튼 -
        Button payment_button = findViewById(R.id.payment_button);
        payment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그인시 customer id 받아오기 (미로그인시 id = 0)
                MainActivity mainActivity = new MainActivity();
                String CUSTOMER_NAME = mainActivity.CUSTOMER_NAME;
                String TEL = mainActivity.TEL;

                if(CUSTOMER_NAME == null){
                    CUSTOMER_NAME = "비회원";
                }

                if(TEL == null){
                    TEL = "";
                }

                //주문일자
                Date today = new Date();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd  HH시 mm분");
                String ORDER_DATE = format1.format(today);

                String ORDER_MENU="";
                int ORDER_PRICE = choice_price;

                //선택한 시간 가져오기
                String PICKUP_TIME = pick_time;

                //주문 메모 받아오기
                String ORDER_MEMO = memo_editText.getText().toString();

                Button time_picker = findViewById(R.id.time_picker);
                String time_pick_up_text = time_picker.getText().toString();

                String message = "order";

                //db에 넘길 자료 준비
                if(paymentList!=null){
                    for(int i =0; i<paymentList.size(); i++){
                        String coffeeName = paymentList.get(i).coffeeName;
                        int count = paymentList.get(i).count;
                        String hotIce = paymentList.get(i).hotIce;
                        String orderMenu = coffeeName+hotIce+count+"개";
                        ORDER_MENU += orderMenu+"\n";

                    }
                    }else{
                            Toast.makeText(getApplicationContext(),"메뉴를 선택해주세요",Toast.LENGTH_SHORT).show();
                    }

                if(time_pick_up_text.length() <= 5) {  
                    Toast.makeText(getApplicationContext(),"시간을 선택해주세요",Toast.LENGTH_SHORT).show();
                }

                if(paymentList!=null && time_pick_up_text.length() > 5){
                    Toast.makeText(getApplicationContext(),"결제가 완료되었습니다",Toast.LENGTH_SHORT).show();
                    //메인(홈)으로 돌아감
                    ((MenuPage)MenuPage.context_menu).close();
                    finish();

                    // ServerMain으로 메시지 송신
                    sendMessage(message);

                }

                //response listener
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                };

                OrderListUploadRequest orderListUploadRequest = new OrderListUploadRequest(CUSTOMER_NAME,TEL,
                        ORDER_DATE, ORDER_MENU, ORDER_PRICE, PICKUP_TIME, ORDER_MEMO, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PaymentPage.this);
                queue.add(orderListUploadRequest);
                }


        });

        final GestureDetector gestureDetector = new GestureDetector(PaymentPage.this,new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
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
                choice_price = choice_price - discount_price;

                //결제하기 버튼에 가격 추가
                String paymentFormat = priceFormat.format(choice_price);
                String payment_price = paymentFormat+"원 결제하기";
                if(choice_price!=0){
                    payment_button.setText(payment_price);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // 시간 선택 버튼 클릭 시
        Button time_picker = findViewById(R.id.time_picker);
        time_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //현재 시간 가져오기
                Date now = new Date();
                //한국 타임존 설정
                TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");

                //시간
                SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.KOREA);
                hourFormat.setTimeZone(tz);
                String hour = hourFormat.format(now);

                //분
                SimpleDateFormat minuteFormat = new SimpleDateFormat("mm",Locale.KOREA);
                String minute = minuteFormat.format(now);

                //타임피커 설정
                TimePickerDialog dialog = new TimePickerDialog(PaymentPage.this, android.R.style.Theme_Holo_Light_Dialog,listener, Integer.valueOf(hour), Integer.valueOf(minute), false);
                dialog.show();
            }
        });

        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        Button coffee_shop_search_Btn = findViewById(R.id.coffee_shop_search_Btn);
        coffee_shop_search_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CafeLocation.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        //가격 중복 방지
        choice_price=0;
    }

    //쿠폰 선택 할인율
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
    //timePicker dialog

    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            pick_time = hourOfDay + "시 " + minute + "분";
            time_pick_Text.setText(pick_time);
        }
    };

    public void sendMessage(String message) {
        JSONObject requestData = new JSONObject();

        try {
            requestData.put("priority", "high");

            JSONObject dataObj = new JSONObject();
            dataObj.put("contents", message);
            requestData.put("data", dataObj);

            JSONArray idArray = new JSONArray();
            idArray.put(0, regId);
            requestData.put("registration_ids", idArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "https://fcm.googleapis.com/fcm/send",
                requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "key=AAAAjN4Kt-M:APA91bFf4hkKeX2OTn-nNG2NXWkxMnGkQZvL4w38OBArEYAQach4z_m1nZc9DjGwY4xXW5VZCrF6rnbAmLTLl_s8_PNXkekikIxRtfbTy4JFiusPSoGNtcMBueJqpJkLK7NxV9748Vxb");

                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }

}