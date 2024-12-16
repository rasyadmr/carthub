package com.dirajarasyad.carthub.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dirajarasyad.carthub.CheckoutActivity;
import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.adapter.CartAdapter;
import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.database.manager.DBCartManager;
import com.dirajarasyad.carthub.model.Cart;

import java.util.List;

public class CartFragment extends Fragment implements CartAdapter.OnCartEmptyListener {
    private TextView cartEmptyTV;
    private RecyclerView cartItemRV;
    private Button cartCheckoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        this.initial(view);
        this.onBind();

        return view;
    }

    private void initial(View view) {
        cartEmptyTV = view.findViewById(R.id.cartEmptyTV);
        cartItemRV = view.findViewById(R.id.cartItemRV);
        cartCheckoutBtn = view.findViewById(R.id.cartCheckoutBtn);
    }

    private void onBind() {
        SessionManager sessionManager = new SessionManager(requireContext());
        DBCartManager cartManager = new DBCartManager(requireContext());
        cartManager.open();
        List<Cart> cartList = cartManager.getAllCartsByUser(sessionManager.getUser());
        cartManager.close();

        if (cartList.isEmpty()) {
            cartCheckoutBtn.setEnabled(false);

            cartItemRV.setVisibility(View.GONE);
            cartEmptyTV.setVisibility(View.VISIBLE);
        } else {
            cartItemRV.setVisibility(View.VISIBLE);
            cartEmptyTV.setVisibility(View.GONE);
        }

        CartAdapter cartAdapter = new CartAdapter(cartList, this);
        cartItemRV.setLayoutManager(new LinearLayoutManager(requireContext()));
        cartItemRV.setAdapter(cartAdapter);

        cartCheckoutBtn.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        Intent checkout = new Intent(requireActivity(), CheckoutActivity.class);
        requireActivity().startActivity(checkout);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.onBind();
    }

    @Override
    public void onCartEmpty(boolean isEmpty) {
        cartCheckoutBtn.setEnabled(!isEmpty);
        cartEmptyTV.setVisibility(View.VISIBLE);
    }
}