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
import com.dirajarasyad.carthub.adapter.ItemInfoAdapter;
import com.dirajarasyad.carthub.database.manager.DBItemManager;
import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.model.Item;
import com.dirajarasyad.carthub.model.User;

import java.util.List;

public class SellerItemFragment extends Fragment {
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
        admin_listTitleTV.setText(R.string.seller_item_title);
    }

    private void onBind() {
        SessionManager sessionManager = new SessionManager(requireContext());
        User user = sessionManager.getUser();

        DBItemManager itemManager = new DBItemManager(requireContext());
        itemManager.open();
        List<Item> itemList = itemManager.getAllItemsBySeller(user);
        itemManager.close();

        if (!itemList.isEmpty()) {
            listEmptyTV.setVisibility(View.GONE);

            ItemInfoAdapter infoAdapter = new ItemInfoAdapter(itemList);
            listDataRV.setLayoutManager(new LinearLayoutManager(requireContext()));
            listDataRV.setAdapter(infoAdapter);
        }
    }
}
