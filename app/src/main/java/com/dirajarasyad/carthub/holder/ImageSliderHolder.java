package com.dirajarasyad.carthub.holder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;

public class ImageSliderHolder extends RecyclerView.ViewHolder {
    public ImageView homeImgsliderIV;
    public ImageSliderHolder(@NonNull View itemView) {
        super(itemView);
        homeImgsliderIV = itemView.findViewById(R.id.homeImgsliderIV);
    }
}
