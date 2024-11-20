package com.dirajarasyad.carthub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.holder.CategoryHolder;
import com.dirajarasyad.carthub.holder.ImageSliderHolder;
import com.dirajarasyad.carthub.model.Category;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderHolder> {
    Context context;
    List<Integer> images;

    public ImageSliderAdapter(List<Integer> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public ImageSliderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.holder_imageslider, parent, false);

        return new ImageSliderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderHolder holder, int position) {
        holder.homeImgsliderIV.setImageResource(images.get(position));

    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
