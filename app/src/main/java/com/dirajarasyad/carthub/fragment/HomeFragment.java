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
import com.dirajarasyad.carthub.adapter.TopAdapter;
import com.dirajarasyad.carthub.database.manager.DBCategoryManager;
import com.dirajarasyad.carthub.database.manager.DBItemManager;

public class HomeFragment extends Fragment {
    private TextView homeTitleTV, homeCategoryTV;
    private RecyclerView homeItemRV, homeTopRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.initial(view);

        DBCategoryManager categoryManager = new DBCategoryManager(requireContext());
        categoryManager.open();
        CategoryAdapter adapter = new CategoryAdapter(categoryManager.getAllCategories());
        categoryManager.close();
        homeItemRV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        homeItemRV.setAdapter(adapter);

        DBItemManager itemManager = new DBItemManager(requireContext());
        itemManager.open();
        homeTopRV.setLayoutManager(new LinearLayoutManager(getContext()));
        homeTopRV.setAdapter(new TopAdapter(itemManager.getAllItems()));
        itemManager.close();

        return view;
    }

    private void initial(View view) {
        homeTitleTV = view.findViewById(R.id.homeTitleTV);
        homeItemRV = view.findViewById(R.id.homeItemRV);
        homeTopRV = view.findViewById(R.id.homeTopRV);
        homeCategoryTV = view.findViewById(R.id.homeCategoryTV);
    }
}