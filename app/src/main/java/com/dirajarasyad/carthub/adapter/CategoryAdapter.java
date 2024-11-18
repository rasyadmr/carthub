package com.dirajarasyad.carthub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    Context context;
    List<Category> listCategory;

    public CategoryAdapter(List<Category> listCategory) {
        this.listCategory = listCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_categories, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int image = listCategory.get(position).getImage();
        String name = listCategory.get(position).getName();

        holder.homecategoryIV.setImageResource(image);
        holder.homeCategoryTV.setText(name);

        holder.homeCategoryCardView.setOnClickListener(v -> {
            Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show();
        });



    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView homecategoryIV;
        TextView homeCategoryTV;
        CardView homeCategoryCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            homecategoryIV = itemView.findViewById(R.id.homecategoryIV);
            homeCategoryTV = itemView.findViewById(R.id.homeCategoryTV);
            homeCategoryCardView = itemView.findViewById(R.id.homeCategoryCardView);

        }
    }
}
