package com.dirajarasyad.carthub.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dirajarasyad.carthub.MainActivity;
import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.auth.SessionManager;
import com.dirajarasyad.carthub.model.User;

public class ProfileFragment extends Fragment {
    TextView profileTitleTV, profileUsernameTV, profileUsernameShowTV, profileEmailTV, profileEmailShowTV
            , profilePhoneTV, profilePhoneShowTV, profileAddressTV, profileAddressShowTV;
    Button profileEditBtn, profileLogoutBtn;
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        this.initial(view);
        this.applyData();

        profileEditBtn.setOnClickListener(this::onClick);
        profileLogoutBtn.setOnClickListener(this::onClick);

        return view;
    }

    private void initial(View view) {
        profileTitleTV = view.findViewById(R.id.profileTitleTV);

        profileUsernameTV = view.findViewById(R.id.profileUsernameTV);
        profileEmailTV = view.findViewById(R.id.profileEmailTV);
        profilePhoneTV = view.findViewById(R.id.profilePhoneTV);
        profileAddressTV = view.findViewById(R.id.profileAddressTV);

        profileUsernameShowTV = view.findViewById(R.id.profileUsernameShowTV);
        profileEmailShowTV = view.findViewById(R.id.profileEmailShowTV);
        profilePhoneShowTV = view.findViewById(R.id.profilePhoneShowTV);
        profileAddressShowTV = view.findViewById(R.id.profileAddressShowTV);

        profileEditBtn = view.findViewById(R.id.profileEditBtn);
        profileLogoutBtn = view.findViewById(R.id.profileLogoutBtn);

        sessionManager = new SessionManager(requireContext());
    }

    private void applyData() {
        User user = sessionManager.getUser();

        profileUsernameShowTV.setText(user.getUsername());
        profileEmailShowTV.setText(user.getEmail());
        profilePhoneShowTV.setText(user.getPhone());
        profileAddressShowTV.setText(user.getAddress());
    }

    private void onClick(View view) {
        if (view == profileEditBtn) {

        } else if (view == profileLogoutBtn) {
            sessionManager.destroySession();
            Intent mainpage = new Intent(requireContext(), MainActivity.class);
            startActivity(mainpage);
            requireActivity().finishAffinity();
        }
    }
}