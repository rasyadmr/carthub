package com.dirajarasyad.carthub.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.adapter.AdminMenuAdapter;
import com.dirajarasyad.carthub.database.manager.DBItemManager;
import com.dirajarasyad.carthub.database.manager.DBTransactionManager;
import com.dirajarasyad.carthub.database.manager.DBUserManager;
import com.dirajarasyad.carthub.model.Menu;
import com.dirajarasyad.carthub.model.User;

import java.util.ArrayList;
import java.util.List;

public class AdminFragment extends Fragment {
    private TextView adminUserTV, adminSellerTV, adminRequestTV, adminItemTV, adminTransactionTV;
    private RecyclerView adminMenuRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        this.initial(view);
        this.onBind();

        return view;
    }

    private void initial(View view) {
        adminUserTV = view.findViewById(R.id.adminUserTV);
        adminSellerTV = view.findViewById(R.id.adminSellerTV);
        adminRequestTV = view.findViewById(R.id.adminRequestTV);
        adminItemTV = view.findViewById(R.id.adminItemTV);
        adminTransactionTV = view.findViewById(R.id.adminTransactionTV);

        adminMenuRV = view.findViewById(R.id.adminMenuRV);
    }

    @SuppressLint("DefaultLocale")
    private void onBind() {
        DBUserManager userManager = new DBUserManager(requireContext());
        DBItemManager itemManager = new DBItemManager(requireContext());
        DBTransactionManager transactionManager = new DBTransactionManager(requireContext());

        userManager.open();
        Integer userCount = userManager.getAllUsers().size();
        Integer sellerCount = userManager.getAllUsersByRole(User.Role.SELLER).size();
        Integer requestCount = userManager.getAllUsersByRole(User.Role.REQUESTED).size();
        userManager.close();

        itemManager.open();
        Integer itemCount = itemManager.getAllItems().size();
        itemManager.close();

        transactionManager.open();
        Integer transactionCount = transactionManager.getAllTransactions().size();
        transactionManager.close();

        adminUserTV.setText(String.format("%d user(s)", userCount));
        adminSellerTV.setText(String.format("%d user(s)", sellerCount));
        adminRequestTV.setText(String.format("%d user(s)", requestCount));
        adminItemTV.setText(String.format("%d item(s)", itemCount));
        adminTransactionTV.setText(String.format("%d transaction(s)", transactionCount));

        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu("User", AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_person_100), new AdminUserFragment()));
        menuList.add(new Menu("Request", AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_add_business_100), new AdminRequestFragment()));
        menuList.add(new Menu("Item", AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_edit_document_100), new AdminItemFragment()));
        AdminMenuAdapter menuAdapter = new AdminMenuAdapter(menuList);

        adminMenuRV.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        adminMenuRV.setAdapter(menuAdapter);
    }
}