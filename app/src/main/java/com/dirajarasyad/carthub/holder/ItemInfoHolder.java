package com.dirajarasyad.carthub.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;

public class ItemInfoHolder extends RecyclerView.ViewHolder {
    public CardView item_infoContainerCV;
    public ImageView item_infoImageIV;
    public TextView item_infoNameTV, item_infoPriceTV, item_infoStockTV, item_infoRatingTV, item_infoSellerTV, item_infoCategoryTV;

    public ItemInfoHolder(@NonNull View itemView) {
        super(itemView);
        this.item_infoContainerCV = itemView.findViewById(R.id.item_infoContainerCV);
        this.item_infoImageIV = itemView.findViewById(R.id.item_infoImageIV);
        this.item_infoNameTV = itemView.findViewById(R.id.item_infoNameTV);
        this.item_infoPriceTV = itemView.findViewById(R.id.item_infoPriceTV);
        this.item_infoStockTV = itemView.findViewById(R.id.item_infoStockTV);
        this.item_infoRatingTV = itemView.findViewById(R.id.item_infoRatingTV);
        this.item_infoSellerTV = itemView.findViewById(R.id.item_infoSellerTV);
        this.item_infoCategoryTV = itemView.findViewById(R.id.item_infoCategoryTV);
    }
}
