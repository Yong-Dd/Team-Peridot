package com.peridot.o_der.client;

import android.view.LayoutInflater;
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

     public class ViewHolder extends RecyclerView.ViewHolder{

        TextView coffeename;
        TextView coffeeprice;

        public ViewHolder(View itemView) {
            super(itemView);

            coffeename = itemView.findViewById(R.id.coffeename);
            coffeeprice = itemView.findViewById(R.id.coffeeprice);
            ImageButton menuplusbutton = itemView.findViewById(R.id.menuplusbutton);

            Button orderbutton = ((MenuPage)MenuPage.context_menu).findViewById(R.id.orderbutton);
            Animation translateUpAnim = ((MenuPage)MenuPage.context_menu).translateUpAnim;
            LinearLayout fragmentPage = ((MenuPage)MenuPage.context_menu).findViewById(R.id.fragmentPage);

            menuplusbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
