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
import com.dirajarasyad.carthub.adapter.HistoryAdapter;
import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.database.manager.DBTransactionManager;

public class HistoryFragment extends Fragment {
    private TextView historyTitleTV, historyItemTV, historyPriceTV, historyStatusTV;
    private RecyclerView historyDataRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        this.initial(view);

        return view;
    }

    private void initial(View view) {
        historyTitleTV = view.findViewById(R.id.historyTitleTV);
        historyItemTV = view.findViewById(R.id.historyItemTV);
        historyPriceTV = view.findViewById(R.id.historyPriceTV);
        historyStatusTV = view.findViewById(R.id.historyStatusTV);
        historyDataRV = view.findViewById(R.id.historyDataRV);

        SessionManager sessionManager = new SessionManager(requireContext());
        DBTransactionManager transactionManager = new DBTransactionManager(requireContext());
        transactionManager.open();
        HistoryAdapter adapter = new HistoryAdapter(transactionManager.getUserTransactionList(sessionManager.getUser().getId()));
        transactionManager.close();

        historyDataRV.setLayoutManager(new LinearLayoutManager(requireContext()));
        historyDataRV.setAdapter(adapter);
    }
}