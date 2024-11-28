package com.dirajarasyad.carthub.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;

public class CartHolder extends RecyclerView.ViewHolder {
    public LinearLayout cartDataLL, cartDetailLL;
    public ImageView cartImageIV, cartDeleteIV;
    public TextView cartNameTV, cartQuantityTV, cartPriceTV;

    public CartHolder(@NonNull View itemView) {
        super(itemView);
        cartDataLL = itemView.findViewById(R.id.cartDataLL);
        cartDetailLL = itemView.findViewById(R.id.cartDetailLL);
        cartImageIV = itemView.findViewById(R.id.cartImageIV);
        cartDeleteIV = itemView.findViewById(R.id.cartDeleteIV);
        cartNameTV = itemView.findViewById(R.id.cartNameTV);
        cartQuantityTV = itemView.findViewById(R.id.cartQuantityTV);
        cartPriceTV = itemView.findViewById(R.id.cartPriceTV);
    }
}
