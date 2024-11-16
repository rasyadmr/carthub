package com.dirajarasyad.carthub.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dirajarasyad.carthub.R;

public class HomeFragment extends Fragment {
    private TextView homeTitleTV;
    private RecyclerView homeItemRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.initial(view);

        return view;
    }

    private void initial(View view) {
        homeTitleTV = view.findViewById(R.id.homeTitleTV);
        homeItemRV = view.findViewById(R.id.homeItemRV);
    }
}