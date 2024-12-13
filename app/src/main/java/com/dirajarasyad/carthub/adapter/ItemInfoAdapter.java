package com.dirajarasyad.carthub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.fragment.ItemFragment;
import com.dirajarasyad.carthub.holder.ItemInfoHolder;
import com.dirajarasyad.carthub.model.Item;

import java.util.List;

public class ItemInfoAdapter extends RecyclerView.Adapter<ItemInfoHolder> {
    private Context context;
    private final List<Item> itemList;

    public ItemInfoAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.holder_item_info, parent, false);

        return new ItemInfoHolder(view);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ItemInfoHolder holder, int position) {
        holder.item_infoImageIV.setImageDrawable(itemList.get(position).getImage());
        holder.item_infoNameTV.setText(itemList.get(position).getName());
        holder.item_infoPriceTV.setText(String.format("Rp %d", itemList.get(position).getPrice()));
        holder.item_infoStockTV.setText(itemList.get(position).getStock().toString());
        holder.item_infoRatingTV.setText(itemList.get(position).getRating().toString());
        holder.item_infoSellerTV.setText(itemList.get(position).getUser().getUsername());
        holder.item_infoCategoryTV.setText(itemList.get(position).getCategory().getName());

        holder.item_infoContainerCV.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("item_id", itemList.get(position).getId());

            ItemFragment itemFragment = new ItemFragment();
            itemFragment.setArguments(bundle);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.homeContainerFL, itemFragment).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
