package com.dirajarasyad.carthub.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;

public class HistoryHolder extends RecyclerView.ViewHolder {
    public LinearLayout historyDataLL;
    public TextView historyItemDataTV, historyPriceDataTV, historyStatusDataTV;

    public HistoryHolder(@NonNull View itemView) {
        super(itemView);
        this.historyDataLL = itemView.findViewById(R.id.historyDataLL);
        this.historyItemDataTV = itemView.findViewById(R.id.historyItemDataTV);
        this.historyPriceDataTV = itemView.findViewById(R.id.historyPriceDataTV);
        this.historyStatusDataTV = itemView.findViewById(R.id.historyStatusDataTV);
    }
}
