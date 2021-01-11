package com.peridot.o_der.client;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.ViewHolder>
 {
    ArrayList<Coffee> items = new ArrayList<Coffee>();
    static MenuFragment menuFragment = new MenuFragment();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.coffee_item, viewGroup, false); //View에 coffee_item 연결

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Coffee item = items.get(position);
        viewHolder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //****OnClickListener 추가
     public class ViewHolder extends RecyclerView.ViewHolder{

        TextView coffeename;
        TextView coffeeprice;
//        public ImageButton menuplusbutton = itemView.findViewById(R.id.menuplusbutton);

        public ViewHolder(View itemView) {
            super(itemView);

            coffeename = itemView.findViewById(R.id.coffeename);
            coffeeprice = itemView.findViewById(R.id.coffeeprice);
            ImageButton menuplusbutton = itemView.findViewById(R.id.menuplusbutton);

            Button orderbutton = ((MenuPage)MenuPage.context_menu).findViewById(R.id.orderbutton);
            Animation translateUpAnim = ((MenuPage)MenuPage.context_menu).translateUpAnim;
            LinearLayout fragmentPage = ((MenuPage)MenuPage.context_menu).findViewById(R.id.fragmentPage);
            //int coffee_position = ((MenuPage)MenuPage.context_menu).coffee_position;
            //String coffeeid = ((MenuPage)MenuPage.context_menu).coffeeId;

            menuplusbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //해당 position외 다른 메뉴 position 값이 있으면 ItemSetting이 제대로 동작안함
                    ((MenuPage)MenuPage.context_menu).dessert_position=-1;
                    ((MenuPage)MenuPage.context_menu).tea_position=-1;
                    menuFragment.ItemSetting();

                    orderbutton.setVisibility(View.GONE);
                    fragmentPage.setVisibility(View.VISIBLE);
                    fragmentPage.startAnimation(translateUpAnim);

                }
            });


        }

        public void setItem(Coffee item) {
            coffeename.setText(item.getName());
            coffeeprice.setText(item.getPrice());
        }

     }//View에 내용 추가

    public void addItem(Coffee item) {
        items.add(item);
    }

    public void setItems(ArrayList<Coffee> items) {
        this.items = items;
    }

    public Coffee getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Coffee item) {
        items.set(position, item);
    }

 }
