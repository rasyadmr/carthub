package com.dirajarasyad.carthub.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.database.manager.DBTransactionManager;
import com.dirajarasyad.carthub.model.Transaction;

public class HistoryDetailFragment extends Fragment {
    private ImageView history_detailImageIV;
    private TextView history_detailNameTV, history_detailSellerTV, history_detailIdTV, history_detailPriceTV, history_detailQuantityTV, history_detailTotalTV, history_detailUsernameTV, history_detailEmailTV, history_detailPhoneTV, history_detailAddressTV, history_detailStatusTV;
    private Transaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_detail, container, false);

        this.initial(view);
        this.onBind();

        return view;
    }

    private void initial(View view) {
        history_detailImageIV = view.findViewById(R.id.history_detailImageIV);

        history_detailNameTV = view.findViewById(R.id.history_detailNameTV);
        history_detailSellerTV = view.findViewById(R.id.history_detailSellerTV);
        history_detailIdTV = view.findViewById(R.id.history_detailIdTV);
        history_detailPriceTV = view.findViewById(R.id.history_detailPriceTV);
        history_detailQuantityTV = view.findViewById(R.id.history_detailQuantityTV);
        history_detailTotalTV = view.findViewById(R.id.history_detailTotalTV);
        history_detailUsernameTV = view.findViewById(R.id.history_detailUsernameTV);
        history_detailEmailTV = view.findViewById(R.id.history_detailEmailTV);
        history_detailPhoneTV  = view.findViewById(R.id.history_detailPhoneTV);
        history_detailAddressTV = view.findViewById(R.id.history_detailAddressTV);
        history_detailStatusTV = view.findViewById(R.id.history_detailStatusTV);

        Bundle bundle = getArguments();

        if (bundle != null) {
            String transactionId = bundle.getString("transaction_id");

            DBTransactionManager transactionManager = new DBTransactionManager(requireContext());
            transactionManager.open();
            transaction = transactionManager.getTransactionById(transactionId);
            transactionManager.close();
        } else {
            Toast.makeText(requireContext(), "Error detected!", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void onBind() {
        history_detailImageIV.setImageDrawable(transaction.getItem().getImage());
        history_detailNameTV.setText(transaction.getItem().getName());
        history_detailSellerTV.setText(transaction.getItem().getUser().getUsername());
        history_detailIdTV.setText(transaction.getId());
        history_detailPriceTV.setText(String.format("Rp %d", transaction.getItem().getPrice()));
        history_detailQuantityTV.setText(transaction.getQuantity().toString());
        history_detailTotalTV.setText(String.format("Rp %d", transaction.getItem().getPrice() * transaction.getQuantity()));

        history_detailUsernameTV.setText(transaction.getUser().getUsername());
        history_detailEmailTV.setText(transaction.getUser().getEmail());
        history_detailPhoneTV.setText(transaction.getUser().getPhone());
        history_detailAddressTV.setText(transaction.getUser().getAddress());

        history_detailStatusTV.setText(transaction.getStatus().value());
    }
}