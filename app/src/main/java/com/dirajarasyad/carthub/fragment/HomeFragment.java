package com.dirajarasyad.carthub.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.adapter.CategoryAdapter;
import com.dirajarasyad.carthub.model.Category;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private TextView homeTitleTV, homeCategoryTV;
    private RecyclerView homeItemRV;

    ArrayList<Category> Categories = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.initial(view);
        CategoryData();
        CategoryAdapter adapter = new CategoryAdapter(Categories);
        homeItemRV.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        homeItemRV.setAdapter(adapter);


        return view;
    }


    private void initial(View view) {
        homeTitleTV = view.findViewById(R.id.homeTitleTV);
        homeItemRV = view.findViewById(R.id.homeItemRV);
        homeCategoryTV = view.findViewById(R.id.homeCategoryTV);
    }

    private void CategoryData(){
        Categories.add(new Category(R.drawable.baseline_electronic_other_24, "Electronics"));
        Categories.add(new Category(R.drawable.category_clotheicon, "Clothing"));
        Categories.add(new Category(R.drawable.baseline_food_24, "Food"));
        Categories.add(new Category(R.drawable.category_beautyicon, "Beauty"));
        Categories.add(new Category(R.drawable.category_bookicon, "Books"));

    }
}