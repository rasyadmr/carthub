package com.dirajarasyad.carthub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.holder.TransactionInfoHolder;
import com.dirajarasyad.carthub.model.Transaction;

import java.util.List;

public class TransactionInfoAdapter extends RecyclerView.Adapter<TransactionInfoHolder> {
    private List<Transaction> transactionList;
    private Context context;

    public TransactionInfoAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.holder_transaction_info, parent, false);

        return new TransactionInfoHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull TransactionInfoHolder holder, int position) {
        String name = transactionList.get(position).getItem().getName();
        String price = String.format("Rp %d", transactionList.get(position).getItem().getPrice());
        String seller = transactionList.get(position).getItem().getUser().getUsername();
        String quantity = String.format("%dx", transactionList.get(position).getQuantity());
        String total = String.format("Rp %d", transactionList.get(position).getItem().getPrice() * transactionList.get(position).getQuantity());
        String date = "Yesterday";
        String status = transactionList.get(position).getStatus().value();

        holder.transaction_infoItemNameTV.setText(name);
        holder.transaction_infoItemPriceTV.setText(price);
        holder.transaction_infoSellerUsernameTV.setText(seller);
        holder.transaction_infoQuantityTV.setText(quantity);
        holder.transaction_infoTotalTV.setText(total);
        holder.transaction_infoTimeTV.setText(date);
        holder.transaction_infoStatusTV.setText(status);
        holder.transaction_infoContainerCV.setOnClickListener(view -> {
            Toast.makeText(context, transactionList.get(position).getId(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }
}
