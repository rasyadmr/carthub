package com.dirajarasyad.carthub.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;

public class CheckoutHolder extends RecyclerView.ViewHolder {
    public ImageView checkoutImageIV;
    public TextView checkoutNameTV, checkoutSellerTV, checkoutPriceTV, checkoutQuantityTV, checkoutTotalTV;

    public CheckoutHolder(@NonNull View itemView) {
        super(itemView);
        this.checkoutImageIV = itemView.findViewById(R.id.checkoutImageIV);
        this.checkoutNameTV = itemView.findViewById(R.id.checkoutNameTV);
        this.checkoutSellerTV = itemView.findViewById(R.id.checkoutSellerTV);
        this.checkoutPriceTV = itemView.findViewById(R.id.checkoutPriceTV);
        this.checkoutQuantityTV = itemView.findViewById(R.id.checkoutQuantityTV);
        this.checkoutTotalTV = itemView.findViewById(R.id.checkoutTotalTV);
    }
}
