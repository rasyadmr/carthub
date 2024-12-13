package com.dirajarasyad.carthub.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;

public class AdminMenuHolder extends RecyclerView.ViewHolder {
    public ImageView admin_menuIconIV;
    public TextView admin_menuTitleTV;
    public ConstraintLayout  admin_menuContainerCL;

    public AdminMenuHolder(@NonNull View itemView) {
        super(itemView);
        this.admin_menuIconIV = itemView.findViewById(R.id.admin_menuIconIV);
        this.admin_menuTitleTV = itemView.findViewById(R.id.admin_menuTitleTV);
        this.admin_menuContainerCL = itemView.findViewById(R.id.admin_menuContainerCL);
    }
}
