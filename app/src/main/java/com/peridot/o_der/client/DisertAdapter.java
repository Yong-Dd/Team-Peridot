package com.peridot.o_der.client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DisertAdapter extends RecyclerView.Adapter<DisertAdapter.ViewHolder> {
        ArrayList<Disert> items = new ArrayList<Disert>();

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.disert_item, viewGroup, false);

        return new ViewHolder(itemView);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Disert item = items.get(position);
        viewHolder.setItem(item);
    }

@Override
public int getItemCount() {
        return items.size();
        }

static class ViewHolder extends RecyclerView.ViewHolder{
    TextView disertname;
    TextView disertprice;

    public ViewHolder(View itemView) {
        super(itemView);

        disertname = itemView.findViewById(R.id.disertname);
        disertprice = itemView.findViewById(R.id.disertprice);
    }

    public void setItem(Disert item) {
        disertname.setText(item.getName());
        disertprice.setText(item.getPrice());
    }
}
    public void addItem(Disert item) {
        items.add(item);
    }

    public void setItems(ArrayList<Disert> items) {
        this.items = items;
    }

    public Disert getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Disert item) {
        items.set(position, item);
    }
}

