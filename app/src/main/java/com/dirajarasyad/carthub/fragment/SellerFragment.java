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
import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.model.Menu;
import com.dirajarasyad.carthub.model.Stat;
import com.dirajarasyad.carthub.model.Transaction;
import com.dirajarasyad.carthub.model.User;

import java.util.ArrayList;
import java.util.List;

public class SellerFragment extends Fragment {
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
        SessionManager sessionManager = new SessionManager(requireContext());
        User user = sessionManager.getUser();
        DBItemManager itemManager = new DBItemManager(requireContext());
        DBTransactionManager transactionManager = new DBTransactionManager(requireContext());

        itemManager.open();
        Integer itemCount = itemManager.getAllItemsBySeller(user).size();
        itemManager.close();

        transactionManager.open();
        List<Transaction> transactionList = transactionManager.getSellerTransactionList(user);
        List<Transaction> transactionPendingList = transactionManager.getSellerAndStatusTransactionList(user, Transaction.Status.PENDING);
        List<Transaction> transactionOnprogressList = transactionManager.getSellerAndStatusTransactionList(user, Transaction.Status.ON_PROGRESS);
        List<Transaction> transactionCompletedList = transactionManager.getSellerAndStatusTransactionList(user, Transaction.Status.COMPLETED);
        transactionManager.close();

        int itemSold = 0;
        for (Transaction t : transactionList) {
            itemSold += t.getQuantity();
        }

        panelTitleTV.setText(R.string.menu_seller);
        panelStatTV.setText(R.string.seller_stat);
        panelMenuTV.setText(R.string.seller_menu);

        List<Stat> statList = new ArrayList<>();
        statList.add(new Stat("Total Item", String.format("%d item(s)", itemCount)));
        statList.add(new Stat("Total Item Sold", String.format("%d item(s)", itemSold)));
        statList.add(new Stat("Total Pending Transaction", String.format("%d transaction(s)", transactionPendingList.size())));
        statList.add(new Stat("Total On Progress Transaction", String.format("%d transaction(s)", transactionOnprogressList.size())));
        statList.add(new Stat("Total Completed Transaction", String.format("%d transaction(s)", transactionCompletedList.size())));

        StatAdapter statAdapter = new StatAdapter(statList);
        panelStatRV.setLayoutManager(new LinearLayoutManager(requireContext()));
        panelStatRV.setAdapter(statAdapter);

        List<Menu> menuList = new ArrayList<>();
        // TODO: Continue
        menuList.add(new Menu("Add Item", AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_add_circle_100), new SellerAddItemFragment()));
        menuList.add(new Menu("My Item", AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_edit_document_100), new SellerItemFragment()));
        menuList.add(new Menu("Pending Transactions", AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_pending_actions_100), new SellerPendingFragment()));
        menuList.add(new Menu("On Progress Transactions", AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_access_time_100), new SellerOnprogressFragment()));
        menuList.add(new Menu("Completed Transactions", AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_check_circle_100), new SellerCompletedFragment()));
        AdminMenuAdapter menuAdapter = new AdminMenuAdapter(menuList);

        panelMenuRV.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        panelMenuRV.setAdapter(menuAdapter);
    }
}