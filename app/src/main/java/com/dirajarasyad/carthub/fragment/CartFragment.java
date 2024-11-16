package com.dirajarasyad.carthub.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dirajarasyad.carthub.R;

public class CartFragment extends Fragment {
    TextView cartTitleTV;
    RecyclerView cartItemRV;
    Button cartCheckoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        this.initial(view);

        return view;
    }

    private void initial(View view) {
        cartTitleTV = view.findViewById(R.id.cartTitleTV);
        cartItemRV = view.findViewById(R.id.cartItemRV);
        cartCheckoutBtn = view.findViewById(R.id.cartCheckoutBtn);
    }
}