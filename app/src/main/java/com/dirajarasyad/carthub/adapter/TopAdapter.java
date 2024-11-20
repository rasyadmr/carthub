package com.dirajarasyad.carthub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.ItemActivity;
import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.holder.TopHolder;
import com.dirajarasyad.carthub.model.Item;

import java.util.List;

public class TopAdapter extends RecyclerView.Adapter<TopHolder> {
    private Context context;
    private List<Item> itemList;

    public TopAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public TopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.holder_top, parent, false);

        return new TopHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull TopHolder holder, int position) {
        String name = itemList.get(position).getName();
        Integer price = itemList.get(position).getPrice();

        holder.topNameTV.setText(name);
        holder.topPriceTV.setText(String.format("Rp %d", price));
        holder.topContainerLL.setOnClickListener(view -> {
            Intent itempage = new Intent(context, ItemActivity.class);
            itempage.putExtra("item_id", itemList.get(position).getId());
            context.startActivity(itempage);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
