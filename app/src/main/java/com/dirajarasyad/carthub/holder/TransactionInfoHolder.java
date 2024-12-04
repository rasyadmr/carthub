package com.dirajarasyad.carthub.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;

public class TransactionInfoHolder extends RecyclerView.ViewHolder {
    public CardView transaction_infoContainerCV;
    public TextView transaction_infoItemNameTV, transaction_infoItemPriceTV, transaction_infoSellerUsernameTV, transaction_infoTimeTV, transaction_infoQuantityTV, transaction_infoTotalTV, transaction_infoStatusTV;

    public TransactionInfoHolder(@NonNull View itemView) {
        super(itemView);

        this.transaction_infoContainerCV = itemView.findViewById(R.id.transaction_infoContainerCV);
        this.transaction_infoItemNameTV = itemView.findViewById(R.id.transaction_infoItemNameTV);
        this.transaction_infoItemPriceTV = itemView.findViewById(R.id.transaction_infoItemPriceTV);
        this.transaction_infoSellerUsernameTV = itemView.findViewById(R.id.transaction_infoSellerUsernameTV);
        this.transaction_infoTimeTV = itemView.findViewById(R.id.transaction_infoTimeTV);
        this.transaction_infoQuantityTV = itemView.findViewById(R.id.transaction_infoQuantityTV);
        this.transaction_infoTotalTV = itemView.findViewById(R.id.transaction_infoTotalTV);
        this.transaction_infoStatusTV = itemView.findViewById(R.id.transaction_infoStatusTV);
    }
}
