package com.dirajarasyad.carthub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.fragment.HistoryDetailFragment;
import com.dirajarasyad.carthub.holder.HistoryHolder;
import com.dirajarasyad.carthub.model.Transaction;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder> {
    private Context context;
    private final List<Transaction> transactionList;

    public HistoryAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.holder_history, parent, false);

        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        String item = transactionList.get(position).getItem().getName();
        @SuppressLint("DefaultLocale") String price = String.format("Rp %d", transactionList.get(position).getItem().getPrice() * transactionList.get(position).getQuantity());
        String status = transactionList.get(position).getStatus().value();

        holder.historyItemDataTV.setText(item);
        holder.historyPriceDataTV.setText(price);
        holder.historyStatusDataTV.setText(status);
        holder.historyDataLL.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("transaction_id", transactionList.get(position).getId());

            HistoryDetailFragment historyDetail = new HistoryDetailFragment();
            historyDetail.setArguments(bundle);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.homeContainerFL, historyDetail).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }
}
