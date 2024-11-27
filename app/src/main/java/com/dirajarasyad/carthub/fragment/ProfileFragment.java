package com.dirajarasyad.carthub.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dirajarasyad.carthub.MainActivity;
import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.model.User;

public class ProfileFragment extends Fragment {
    private TextView profileUsernameShowTV, profileEmailShowTV, profilePhoneShowTV, profileAddressShowTV;
    private Button profileEditBtn, profileLogoutBtn;
    private ImageView profilePictureIV;
    private SessionManager sessionManager;

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

        profileUsernameShowTV = view.findViewById(R.id.profileUsernameShowTV);
        profileEmailShowTV = view.findViewById(R.id.profileEmailShowTV);
        profilePhoneShowTV = view.findViewById(R.id.profilePhoneShowTV);
        profileAddressShowTV = view.findViewById(R.id.profileAddressShowTV);

        profilePictureIV = view.findViewById(R.id.profilePictureIV);
        profileEditBtn = view.findViewById(R.id.profileEditBtn);
        profileLogoutBtn = view.findViewById(R.id.profileLogoutBtn);

        sessionManager = new SessionManager(requireContext());
    }

    private void applyData() {
        User user = sessionManager.getUser();

        profilePictureIV.setImageDrawable(sessionManager.getUser().getImage());
        profileUsernameShowTV.setText(user.getUsername());
        profileEmailShowTV.setText(user.getEmail());
        profilePhoneShowTV.setText(user.getPhone());
        profileAddressShowTV.setText(user.getAddress());
    }

    private void onClick(View view) {
        if (view == profileEditBtn) {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.homeContainerFL, new ProfileEditFragment())
                    .addToBackStack(null)
                    .commit();
        } else if (view == profileLogoutBtn) {
            sessionManager.destroySession();
            Intent mainpage = new Intent(requireContext(), MainActivity.class);
            startActivity(mainpage);
            requireActivity().finishAffinity();
        }
    }
}