package com.dirajarasyad.carthub.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.adapter.HistoryAdapter;
import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.database.manager.DBTransactionManager;
import com.dirajarasyad.carthub.model.User;

public class HistoryFragment extends Fragment {
    private RecyclerView historyDataRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        this.initial(view);
        this.onBind();

        return view;
    }

    private void initial(View view) {
        historyDataRV = view.findViewById(R.id.historyDataRV);
    }

    private void onBind() {
        SessionManager sessionManager = new SessionManager(requireContext());
        User user = sessionManager.getUser();
        DBTransactionManager transactionManager = new DBTransactionManager(requireContext());
        transactionManager.open();
        HistoryAdapter adapter = new HistoryAdapter(transactionManager.getUserTransactionList(user));
        transactionManager.close();

        historyDataRV.setLayoutManager(new LinearLayoutManager(requireContext()));
        historyDataRV.setAdapter(adapter);
    }
}