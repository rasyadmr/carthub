package com.dirajarasyad.carthub.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.adapter.TransactionInfoAdapter;
import com.dirajarasyad.carthub.database.manager.DBTransactionManager;
import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.model.Transaction;
import com.dirajarasyad.carthub.model.User;

import java.util.List;

public class SellerCompletedFragment extends Fragment {
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
        admin_listTitleTV.setText(R.string.merchant_completed_title);
    }

    private void onBind() {
        SessionManager sessionManager = new SessionManager(requireContext());
        User user = sessionManager.getUser();

        DBTransactionManager transactionManager = new DBTransactionManager(requireContext());
        transactionManager.open();
        List<Transaction> transactionList = transactionManager.getSellerAndStatusTransactionList(user, Transaction.Status.COMPLETED);
        transactionManager.close();

        if (!transactionList.isEmpty()) {
            listEmptyTV.setVisibility(View.GONE);

            TransactionInfoAdapter transactionInfoAdapter = new TransactionInfoAdapter(transactionList);
            listDataRV.setLayoutManager(new LinearLayoutManager(requireContext()));
            listDataRV.setAdapter(transactionInfoAdapter);
        }
    }
}
