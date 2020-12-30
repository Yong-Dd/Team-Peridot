package com.peridot.o_der.client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.ViewHolder>
 {
    ArrayList<com.peridot.o_der.client.Coffee> items = new ArrayList<com.peridot.o_der.client.Coffee>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.coffee_item, viewGroup, false); //View에 coffee_item 연결

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        com.peridot.o_der.client.Coffee item = items.get(position);
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

        }

        public void setItem(com.peridot.o_der.client.Coffee item) {
            coffeename.setText(item.getName());
            coffeeprice.setText(item.getPrice());
        }

    }//View에 내용 추가

    public void addItem(com.peridot.o_der.client.Coffee item) {
        items.add(item);
    }

    public void setItems(ArrayList<com.peridot.o_der.client.Coffee> items) {
        this.items = items;
    }

    public com.peridot.o_der.client.Coffee getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, com.peridot.o_der.client.Coffee item) {
        items.set(position, item);
    }

 }
