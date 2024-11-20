package com.dirajarasyad.carthub.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.adapter.CategoryAdapter;
import com.dirajarasyad.carthub.adapter.ImageSliderAdapter;
import com.dirajarasyad.carthub.database.manager.DBCategoryManager;
import com.dirajarasyad.carthub.model.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    private TextView homeTitleTV, homeCategoryTV;
    private RecyclerView homeItemRV, homeImgsliderRV;

    private ImageSliderAdapter SliderAdapter;
    private Handler handler = new Handler();


    private Integer currentPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.initial(view);

        // Image slider
        List<Integer> images = Arrays.asList(
                R.drawable.promoimage1,
                R.drawable.promoimage2,
                R.drawable.promoimage3
        );


        SliderAdapter = new ImageSliderAdapter(images);
        homeImgsliderRV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        homeImgsliderRV.setAdapter(SliderAdapter);
        startAutoScroll();


        DBCategoryManager categoryManager = new DBCategoryManager(requireContext());
        categoryManager.open();
        CategoryAdapter adapter = new CategoryAdapter(categoryManager.getAllCategories());
        categoryManager.close();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        homeItemRV.setLayoutManager(gridLayoutManager);
        homeItemRV.setAdapter(adapter);

        return view;
    }

    private void initial(View view) {
        homeTitleTV = view.findViewById(R.id.homeTitleTV);
        homeItemRV = view.findViewById(R.id.homeItemRV);
        homeCategoryTV = view.findViewById(R.id.homeCategoryTV);
        homeImgsliderRV = view.findViewById(R.id.homeImgsliderRV);

    }

    private void startAutoScroll() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPosition == SliderAdapter.getItemCount()) {
                    currentPosition = 0;
                }
                homeImgsliderRV.smoothScrollToPosition(currentPosition++);
                handler.postDelayed(this, 3000);
            }
        };
        handler.post(runnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}