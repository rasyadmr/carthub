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
import com.dirajarasyad.carthub.adapter.UserInfoAdapter;
import com.dirajarasyad.carthub.database.manager.DBUserManager;
import com.dirajarasyad.carthub.model.User;

import java.util.List;

public class AdminRequestFragment extends Fragment {
    private RecyclerView listDataRV;
    private TextView listEmptyTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.template_list, container, false);

        this.initial(view);
        this.onBind();

        return view;
    }

    private void initial(View view) {
        listDataRV = view.findViewById(R.id.listDataRV);
        listEmptyTV = view.findViewById(R.id.listEmptyTV);

        TextView admin_listTitleTV = view.findViewById(R.id.listTitleTV);
        admin_listTitleTV.setText(R.string.admin_request_title);
    }

    private void onBind() {
        DBUserManager userManager = new DBUserManager(requireContext());
        userManager.open();
        List<User> userList = userManager.getAllUsersByRole(User.Role.REQUESTED);
        userManager.close();

        if (!userList.isEmpty()) {
            listEmptyTV.setVisibility(View.GONE);

            UserInfoAdapter infoAdapter = new UserInfoAdapter(userList);
            listDataRV.setLayoutManager(new LinearLayoutManager(requireContext()));
            listDataRV.setAdapter(infoAdapter);
        }
    }
}