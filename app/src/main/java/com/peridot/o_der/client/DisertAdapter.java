package com.peridot.o_der.client;

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
    static MenuFragment menuFragment = new MenuFragment();
    TextView disertname;
    TextView disertprice;

    public ViewHolder(View itemView) {
        super(itemView);

        disertname = itemView.findViewById(R.id.disertname);
        disertprice = itemView.findViewById(R.id.disertprice);
        ImageButton menuplusbutton = itemView.findViewById(R.id.menuplusbutton);

        Button orderbutton = ((MenuPage)MenuPage.context_menu).findViewById(R.id.orderbutton);
        Animation translateUpAnim = ((MenuPage)MenuPage.context_menu).translateUpAnim;
        LinearLayout fragmentPage = ((MenuPage)MenuPage.context_menu).findViewById(R.id.fragmentPage);

        menuplusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //해당 position외 다른 메뉴 position 값이 있으면 ItemSetting이 제대로 동작안함
                ((MenuPage)MenuPage.context_menu).coffee_position=-1;
                ((MenuPage)MenuPage.context_menu).tea_position=-1;
                menuFragment.ItemSetting();

                orderbutton.setVisibility(View.GONE);
                fragmentPage.setVisibility(View.VISIBLE);
                fragmentPage.startAnimation(translateUpAnim);
            }
        });
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

