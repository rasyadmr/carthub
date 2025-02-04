package com.dirajarasyad.carthub.fragment;

import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
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
import com.dirajarasyad.carthub.adapter.TopAdapter;
import com.dirajarasyad.carthub.database.manager.DBItemManager;
import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.model.Category;
import com.dirajarasyad.carthub.model.User;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private TextView homeTopTV;
    private RecyclerView homeItemRV, homeImgsliderRV, homeTopRV;
    private SessionManager sessionManager;
    private ImageSliderAdapter SliderAdapter;
    private final Handler handler = new Handler();


    private Integer currentPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.initial(view);
        this.onBind();

        return view;
    }

    private void initial(View view) {
        homeItemRV = view.findViewById(R.id.homeItemRV);
        homeTopRV = view.findViewById(R.id.homeTopRV);
        homeImgsliderRV = view.findViewById(R.id.homeImgsliderRV);
        homeTopTV = view.findViewById(R.id.homeTopTV);

        sessionManager = new SessionManager(requireContext());
    }

    private void onBind() {
        // Image slider
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.promoimage1);
        images.add(R.drawable.promoimage2);
        images.add(R.drawable.promoimage3);

        SliderAdapter = new ImageSliderAdapter(images);
        homeImgsliderRV.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        homeImgsliderRV.setAdapter(SliderAdapter);
        startAutoScroll();

        DBCategoryManager categoryManager = new DBCategoryManager(requireContext());
        categoryManager.open();
        List<Category> categoryList = categoryManager.getAllCategories();
        categoryList.add(0, new Category(null, "All", AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_border_all_24)));
        CategoryAdapter adapter = new CategoryAdapter(categoryList);
        categoryManager.close();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3);
        homeItemRV.setLayoutManager(gridLayoutManager);
        homeItemRV.setAdapter(adapter);

        DBItemManager itemManager = new DBItemManager(requireContext());
        itemManager.open();
        homeTopRV.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));

        User user = sessionManager.getUser();
        if (user.getRole() == User.Role.MERCHANT) {
            homeTopRV.setAdapter(new TopAdapter(itemManager.getTopSeller(5, false, user)));
            homeTopTV.setText(R.string.home_top_merchant);
        } else {
            homeTopRV.setAdapter(new TopAdapter(itemManager.getTop(5, false)));
        }
        itemManager.close();
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