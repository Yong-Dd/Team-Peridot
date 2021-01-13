package com.peridot.o_der.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MenuFragment extends Fragment {
    String coffeeName;   // 커피 이름
    int coffeePrice;     // 커피 가격

    TextView coffee_quan;      // 커피 개수
    static Button Add_btn_text;     // 커프 담기
    static TextView menu_name;        // 메뉴 이름

    Animation translateDownAnim;
    static int ordercount ;
    static LinearLayout hot_ice_choice;
    RadioButton HotBtn;
    RadioButton IceBtn;
    Button orderbutton;

    int count; //개수
    static int count2;  //메뉴 가격
    static int count3;  //총 값
    static String hotIce;//아이스 ,핫 선택





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.menu_fragment, container, false);

        ordercount = 0;
        coffee_quan = rootView.findViewById(R.id.coffee_quan);
        Add_btn_text = rootView.findViewById(R.id.Add_btn);
        hot_ice_choice = rootView.findViewById(R.id.hot_ice_choice);
        HotBtn = rootView.findViewById(R.id.Hot_btn);
        IceBtn = rootView.findViewById(R.id.Ice_btn);
        menu_name = rootView.findViewById(R.id.coffee_name);
        count2 = Integer.parseInt(Add_btn_text.getText().toString());
        count3 = Integer.parseInt(Add_btn_text.getText().toString());
        count = 1;
        hotIce = "";


        ImageButton closeBtn = rootView.findViewById(R.id.close_btn);         // 닫기 버튼
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation translateDownAnim = ((MenuPage)MenuPage.context_menu).translateDownAnim;
                LinearLayout fragmentpage = ((MenuPage) MenuPage.context_menu).findViewById(R.id.fragmentPage);

                fragmentpage.startAnimation(translateDownAnim);
                fragmentpage.setVisibility(View.GONE);

                if(ordercount>=1){
                    orderbutton.setText(Integer.toString(ordercount)+"개");
                    Animation translateUpAnim = ((MenuPage)MenuPage.context_menu).translateUpAnim;
                    orderbutton.setVisibility(View.VISIBLE);
                    orderbutton.startAnimation(translateUpAnim);
                }
                count = 1;
            }
        });

        Button minusBtn = rootView.findViewById(R.id.minus_btn);              // - 버튼
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = Integer.parseInt(coffee_quan.getText().toString());
                count--;
                coffee_quan.setText(String.valueOf(count));

                count3 -= count2;
                String text = String.valueOf(count3)+"원 담기";
                String texts = "0원 담기";
                Add_btn_text.setText(text);

                if(count < 1){                                                // 개수가 1 미만일 때
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("경고");
                    builder.setMessage("잘못된 개수입니다.");
                    builder.setNegativeButton("돌아가기", null);
                    builder.create().show();

                    count = 0;
                    coffee_quan.setText(String.valueOf(count));
                    //count2 = 0;
                    Add_btn_text.setText(texts);
                }
            }
        });

        Button plusBtn = rootView.findViewById(R.id.plus_btn);                   // +버튼
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = Integer.parseInt(coffee_quan.getText().toString());
                count++;
                coffee_quan.setText(String.valueOf(count));

                count3 += count2;
                String text = String.valueOf(count3)+"원 담기";
                Add_btn_text.setText(text);

            }
        });

        Button Add_btn = rootView.findViewById(R.id.Add_btn);
        Add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation translateDownAnim = ((MenuPage)MenuPage.context_menu).translateDownAnim;
                Animation translateUpAnim = ((MenuPage)MenuPage.context_menu).translateUpAnim;
                LinearLayout fragmentpage = ((MenuPage) MenuPage.context_menu).findViewById(R.id.fragmentPage);
                orderbutton = ((MenuPage)MenuPage.context_menu).findViewById(R.id.orderbutton);

                // 핫/아이스 버튼 선택하지 않았을 경우
                if(HotBtn.isChecked() == false && IceBtn.isChecked() == false) {
                    Toast.makeText(getContext(), "핫/아이스 를 선택하세요", Toast.LENGTH_LONG).show();
                    Log.d("toast", "라디오버튼 비활성화");
                }
                else {
                    if(count2 > 0) {
                        fragmentpage.startAnimation(translateDownAnim);
                        fragmentpage.setVisibility(View.GONE);
                        orderbutton.setVisibility(View.VISIBLE);
                        orderbutton.startAnimation(translateUpAnim);

                        coffee_quan.setText(Integer.toString(1));
                        ordercount+=1;
                        orderbutton.setText(Integer.toString(ordercount)+"개");
                    }
                    else{
                        Toast.makeText(getContext(), "잘못된 개수입니다", Toast.LENGTH_LONG).show();
                    }
                }
                if(HotBtn.isChecked() == true && IceBtn.isChecked() == false){
                    hotIce = "(핫)";
                }else if(IceBtn.isChecked() == true && HotBtn.isChecked() == false){
                    hotIce = "(아이스)";
                }



                //******총 가격을 (count2)를 MenuPage의 checkItem으로 넘김
                ((MenuPage)MenuPage.context_menu).checkItem(count3,count,hotIce);
                count = 1;

            }
        });


        return rootView;

    }


    public void ItemSetting(){
        int coffee= ((MenuPage)MenuPage.context_menu).coffee_position;
        int dessert = ((MenuPage)MenuPage.context_menu).dessert_position;
        int tea = ((MenuPage)MenuPage.context_menu).tea_position;

        String menuName = "";

        if(coffee>-1){
            menuName = ((MenuPage)MenuPage.context_menu).coffeeAdapter.getItem(coffee).getName();
            count2 = ((MenuPage)MenuPage.context_menu).db_coffeePrice.get(coffee);
            count3 = count2;
            hot_ice_choice.setVisibility(View.VISIBLE);
        }else if(dessert>-1){
            menuName = ((MenuPage)MenuPage.context_menu).disertAdapter.getItem(dessert).getName();
            count2 = ((MenuPage)MenuPage.context_menu).db_dessertPrice.get(dessert);
            count3 = count2;
            hot_ice_choice.setVisibility(View.INVISIBLE);

        }else if(tea>-1){
            menuName = ((MenuPage)MenuPage.context_menu).teaAdapter.getItem(tea).getName();
            count2 = ((MenuPage)MenuPage.context_menu).db_teaPrice.get(tea);
            count3 = count2;
            hot_ice_choice.setVisibility(View.VISIBLE);
        }
        String price_text = count2+"원";
        Add_btn_text.setText(price_text);
        menu_name.setText(menuName);
    }

}