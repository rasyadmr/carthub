package com.dirajarasyad.carthub.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.adapter.AdminMenuAdapter;
import com.dirajarasyad.carthub.adapter.StatAdapter;
import com.dirajarasyad.carthub.database.manager.DBItemManager;
import com.dirajarasyad.carthub.database.manager.DBTransactionManager;
import com.dirajarasyad.carthub.database.manager.DBUserManager;
import com.dirajarasyad.carthub.model.Menu;
import com.dirajarasyad.carthub.model.Stat;
import com.dirajarasyad.carthub.model.User;

import java.util.ArrayList;
import java.util.List;

public class AdminFragment extends Fragment {
    private TextView panelTitleTV, panelMenuTV, panelStatTV;
    private RecyclerView panelMenuRV, panelStatRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_panel, container, false);

        this.initial(view);
        this.onBind();

        return view;
    }

    private void initial(View view) {
        panelTitleTV = view.findViewById(R.id.panelTitleTV);
        panelStatTV = view.findViewById(R.id.panelStatTV);
        panelMenuTV = view.findViewById(R.id.panelMenuTV);

        panelStatRV = view.findViewById(R.id.panelStatRV);
        panelMenuRV = view.findViewById(R.id.panelMenuRV);
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

        panelTitleTV.setText(R.string.menu_admin);
        panelStatTV.setText(R.string.admin_stat);
        panelMenuTV.setText(R.string.admin_menu);

        List<Stat> statList = new ArrayList<>();
        statList.add(new Stat("Total User", String.format("%d user(s)", userCount)));
        statList.add(new Stat("Total Seller", String.format("%d user(s)", sellerCount)));
        statList.add(new Stat("Total Request Seller", String.format("%d user(s)", requestCount)));
        statList.add(new Stat("Total Item", String.format("%d item(s)", itemCount)));
        statList.add(new Stat("Total Transaction", String.format("%d transaction(s)", transactionCount)));

        StatAdapter statAdapter = new StatAdapter(statList);
        panelStatRV.setLayoutManager(new LinearLayoutManager(requireContext()));
        panelStatRV.setAdapter(statAdapter);

        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu("User", AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_person_100), new AdminUserFragment()));
        menuList.add(new Menu("Request", AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_add_business_100), new AdminRequestFragment()));
        menuList.add(new Menu("Item", AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_edit_document_100), new AdminItemFragment()));
        AdminMenuAdapter menuAdapter = new AdminMenuAdapter(menuList);

        panelMenuRV.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        panelMenuRV.setAdapter(menuAdapter);
    }
}