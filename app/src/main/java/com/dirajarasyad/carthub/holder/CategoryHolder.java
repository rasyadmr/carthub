package com.dirajarasyad.carthub.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;

public class CategoryHolder extends RecyclerView.ViewHolder {
    public ImageView homeCategoryIV;
    public TextView homeCategoryTV;
    public CardView homeCategoryCardView;

    public CategoryHolder(@NonNull View itemView) {
        super(itemView);
        homeCategoryIV = itemView.findViewById(R.id.homeCategoryIV);
        homeCategoryTV = itemView.findViewById(R.id.homeCategoryTV);
        homeCategoryCardView = itemView.findViewById(R.id.homeCategoryCardView);

    }
}
