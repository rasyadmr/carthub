package com.dirajarasyad.carthub.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;

public class AdminMenuHolder extends RecyclerView.ViewHolder {
    public ImageView admin_menuIconIV;
    public TextView admin_menuTitleTV;
    public CardView admin_menuContainerCV;

    public AdminMenuHolder(@NonNull View itemView) {
        super(itemView);
        this.admin_menuIconIV = itemView.findViewById(R.id.admin_menuIconIV);
        this.admin_menuTitleTV = itemView.findViewById(R.id.admin_menuTitleTV);
        this.admin_menuContainerCV = itemView.findViewById(R.id.admin_menuContainerCV);
    }
}
