package com.dirajarasyad.carthub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.holder.CheckoutHolder;
import com.dirajarasyad.carthub.model.Cart;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutHolder> {
    private final List<Cart> cartList;

    public CheckoutAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CheckoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.holder_checkout, parent, false);

        return new CheckoutHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutHolder holder, int position) {
        Drawable image = cartList.get(position).getItem().getImage();
        String name = cartList.get(position).getItem().getName();
        String seller = cartList.get(position).getItem().getUser().getUsername();
        @SuppressLint("DefaultLocale") String price = String.format("Rp %d", cartList.get(position).getItem().getPrice());
        String quantity = cartList.get(position).getQuantity().toString();
        @SuppressLint("DefaultLocale") String total = String.format("Rp %d", cartList.get(position).getItem().getPrice() * cartList.get(position).getQuantity());

        holder.checkoutImageIV.setImageDrawable(image);
        holder.checkoutNameTV.setText(name);
        holder.checkoutSellerTV.setText(seller);
        holder.checkoutPriceTV.setText(price);
        holder.checkoutQuantityTV.setText(quantity);
        holder.checkoutTotalTV.setText(total);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
