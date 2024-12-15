package com.dirajarasyad.carthub.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.fragment.CategoryFragment;
import com.dirajarasyad.carthub.holder.CategoryHolder;
import com.dirajarasyad.carthub.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder>{
    Context context;
    List<Category> categoryList;

    public CategoryAdapter(List<Category> listCategory) {
        this.categoryList = listCategory;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.holder_category, parent, false);

        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Drawable image = categoryList.get(position).getImage();
        String name = categoryList.get(position).getName();

        holder.homeCategoryIV.setImageDrawable(image);
        holder.homeCategoryTV.setText(name);

        holder.homeCategoryCardView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("category_id", categoryList.get(position).getId());

            CategoryFragment categoryFragment = new CategoryFragment();
            categoryFragment.setArguments(bundle);

            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.homeContainerFL, categoryFragment).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}