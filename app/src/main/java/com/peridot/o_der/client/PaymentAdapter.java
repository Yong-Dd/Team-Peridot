package com.peridot.o_der.client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    ArrayList<Payment> items = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.payment_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Payment item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Payment item){
        items.add(item);
    }

    public void setItems(ArrayList<Payment> items){
        this.items = items;
    }
    public Payment getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, Payment item){
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        final DecimalFormat priceFormat = new DecimalFormat("###,###");
        TextView payment_coffeeName;
        TextView payment_coffeeCount;
        TextView payment_coffeePrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            payment_coffeeName = itemView.findViewById(R.id.payment_coffeeName);
            payment_coffeeCount = itemView.findViewById(R.id.payment_coffeeCount);
            payment_coffeePrice = itemView.findViewById(R.id.payment_coffeePrice);
        }

        public void setItem(Payment item){
            payment_coffeeName.setText(item.getCoffeeName()+item.getHotIce());
            payment_coffeeCount.setText(item.getCount()+"개");

            String price = priceFormat.format(item.getCoffeePrice());
            payment_coffeePrice.setText(price+"원");
        }
    }
}
