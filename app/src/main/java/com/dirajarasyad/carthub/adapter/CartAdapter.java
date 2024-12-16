package com.dirajarasyad.carthub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.database.manager.DBCartManager;
import com.dirajarasyad.carthub.holder.CartHolder;
import com.dirajarasyad.carthub.model.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartHolder> {
    private Context context;
    private final List<Cart> cartList;
    private final OnCartEmptyListener onCartEmptyListener;

    public CartAdapter(List<Cart> cartList, OnCartEmptyListener onCartEmptyListener) {
        this.cartList = cartList;
        this.onCartEmptyListener = onCartEmptyListener;
    }

    public interface OnCartEmptyListener {
        void onCartEmpty(boolean isEmpty);
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.holder_cart, parent, false);

        return new CartHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        if (position >= this.getItemCount()) {
            return;
        }

        String name = cartList.get(position).getItem().getName();
        String quantity = String.format("Quantity: %d", cartList.get(position).getQuantity());
        String price = String.format("Total: Rp %d", cartList.get(position).getItem().getPrice() * cartList.get(position).getQuantity());

        holder.cartImageIV.setImageDrawable(cartList.get(position).getItem().getImage());
        holder.cartNameTV.setText(name);
        holder.cartQuantityTV.setText(quantity);
        holder.cartPriceTV.setText(price);
        holder.cartDeleteIV.setOnClickListener(view -> {
            DBCartManager cartManager = new DBCartManager(context);
            cartManager.open();
            cartManager.deleteCart(cartList.get(position).getId());
            cartManager.close();
            cartList.remove(position);
            notifyItemRemoved(position);

            onCartEmptyListener.onCartEmpty(cartList.isEmpty());
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
