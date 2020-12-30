package com.peridot.o_der.client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeaAdapter extends RecyclerView.Adapter<TeaAdapter.ViewHolder> {
    ArrayList<com.peridot.o_der.client.Tea> items = new ArrayList<com.peridot.o_der.client.Tea>();

    @NonNull
    @Override
    public com.peridot.o_der.client.TeaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.tea_item, viewGroup, false);

        return new com.peridot.o_der.client.TeaAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull com.peridot.o_der.client.TeaAdapter.ViewHolder viewHolder, int position) {
        com.peridot.o_der.client.Tea item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView teaname;
        TextView teaprice;

        public ViewHolder(View itemView) {
            super(itemView);

            teaname = itemView.findViewById(R.id.teaname);
            teaprice = itemView.findViewById(R.id.teaprice);
        }

        public void setItem(com.peridot.o_der.client.Tea item) {
            teaname.setText(item.getName());
            teaprice.setText(item.getPrice());
        }
    }
    public void addItem(com.peridot.o_der.client.Tea item) {
        items.add(item);
    }

    public void setItems(ArrayList<com.peridot.o_der.client.Tea> items) {
        this.items = items;
    }

    public com.peridot.o_der.client.Tea getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, com.peridot.o_der.client.Tea item) {
        items.set(position, item);
    }
}

