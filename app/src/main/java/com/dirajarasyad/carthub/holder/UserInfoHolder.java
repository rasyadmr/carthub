package com.dirajarasyad.carthub.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;

public class UserInfoHolder extends RecyclerView.ViewHolder {
    public CardView user_infoContainerCV;
    public ImageView user_infoImageIV;
    public TextView user_infoUsernameTV, user_infoEmailTV, user_infoPhoneTV, user_infoRoleTV;

    public UserInfoHolder(@NonNull View itemView) {
        super(itemView);
        this.user_infoContainerCV = itemView.findViewById(R.id.user_infoContainerCV);
        this.user_infoImageIV = itemView.findViewById(R.id.user_infoImageIV);
        this.user_infoUsernameTV = itemView.findViewById(R.id.user_infoUsernameTV);
        this.user_infoEmailTV = itemView.findViewById(R.id.user_infoEmailTV);
        this.user_infoPhoneTV = itemView.findViewById(R.id.user_infoPhoneTV);
        this.user_infoRoleTV = itemView.findViewById(R.id.user_infoRoleTV);
    }
}
