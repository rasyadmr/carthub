package com.dirajarasyad.carthub.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;

public class StatHolder extends RecyclerView.ViewHolder {
    public TextView statTitleTV, statValueTV;

    public StatHolder(@NonNull View itemView) {
        super(itemView);
        this.statTitleTV = itemView.findViewById(R.id.statTitleTV);
        this.statValueTV = itemView.findViewById(R.id.statValueTV);
    }
}
