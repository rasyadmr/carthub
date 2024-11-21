package com.dirajarasyad.carthub.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;

public class TopHolder extends RecyclerView.ViewHolder {
    public LinearLayout topContainerLL;
    public TextView topNameTV, topPriceTV, topRatingTV;

    public TopHolder(@NonNull View itemView) {
        super(itemView);
        this.topContainerLL = itemView.findViewById(R.id.topContainerLL);
        this.topNameTV = itemView.findViewById(R.id.topNameTV);
        this.topPriceTV = itemView.findViewById(R.id.topPriceTV);
        this.topRatingTV = itemView.findViewById(R.id.topRatingTV);
    }
}
