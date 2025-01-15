package com.dirajarasyad.carthub.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.database.manager.DBItemManager;
import com.dirajarasyad.carthub.model.Item;

public class ItemFragment extends Fragment {
    private ImageView f_itemImageIV;
    private TextView f_itemNameTV, f_itemStockTV, f_itemPriceTV, f_itemRatingTV, f_itemSellerTV, f_itemCategoryTV, f_itemDescriptionTV;
    private Button f_itemDeleteBtn, f_itemEditBtn;

    private Item item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        this.initial(view);
        this.onBind();

        return view;
    }

    private void initial(View view) {
        f_itemImageIV = view.findViewById(R.id.f_itemImageIV);
        f_itemNameTV = view.findViewById(R.id.f_itemNameTV);
        f_itemStockTV = view.findViewById(R.id.f_itemStockTV);
        f_itemPriceTV = view.findViewById(R.id.f_itemPriceTV);
        f_itemRatingTV = view.findViewById(R.id.f_itemRatingTV);
        f_itemSellerTV = view.findViewById(R.id.f_itemSellerTV);
        f_itemCategoryTV = view.findViewById(R.id.f_itemCategoryTV);
        f_itemDescriptionTV = view.findViewById(R.id.f_itemDescriptionTV);
        f_itemDeleteBtn = view.findViewById(R.id.f_itemDeleteBtn);
        f_itemEditBtn = view.findViewById(R.id.f_itemEditBtn);

        if (getArguments() != null) {
            String itemId = getArguments().getString("item_id");

            DBItemManager itemManager = new DBItemManager(requireContext());
            itemManager.open();
            item = itemManager.getItemById(itemId);
            itemManager.close();
        } else {
            Toast.makeText(requireContext(), "Error acquired! ItemFragment @initial", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        }


    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void onBind() {
        Log.d("ID", item.getId());
        f_itemImageIV.setImageDrawable(item.getImage());
        f_itemNameTV.setText(item.getName());
        f_itemStockTV.setText(String.format("Stock: %d", item.getStock()));
        f_itemPriceTV.setText(String.format("Rp %d", item.getPrice()));
        f_itemRatingTV.setText(item.getRating().toString());
        f_itemSellerTV.setText(item.getUser().getUsername());
        f_itemCategoryTV.setText(item.getCategory().getName());
        f_itemDescriptionTV.setText(item.getDescription());

        f_itemDeleteBtn.setOnClickListener(this::onClick);
        f_itemEditBtn.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        if (view == f_itemDeleteBtn) {
            DBItemManager itemManager = new DBItemManager(requireContext());

            itemManager.open();
            itemManager.deleteItem(item);
            itemManager.close();
            requireActivity().getSupportFragmentManager().popBackStack();
        } else if (view == f_itemEditBtn) {
            Bundle bundle = new Bundle();
            bundle.putString("item_id", item.getId());

            ItemEditFragment itemEdit = new ItemEditFragment();
            itemEdit.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeContainerFL, itemEdit).addToBackStack(null).commit();
        }
    }
}