package com.dirajarasyad.carthub.fragment;

import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.adapter.ItemInfoAdapter;
import com.dirajarasyad.carthub.database.manager.DBCategoryManager;
import com.dirajarasyad.carthub.database.manager.DBItemManager;
import com.dirajarasyad.carthub.model.Category;
import com.dirajarasyad.carthub.model.Item;

import java.util.List;

public class CategoryFragment extends Fragment {
    private TextView listTitleTV, listEmptyTV;
    private RecyclerView listDataRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.template_list, container, false);

        this.initial(view);
        this.onBind();

        return view;
    }

    private void initial(View view) {
        listTitleTV = view.findViewById(R.id.listTitleTV);
        listEmptyTV = view.findViewById(R.id.listEmptyTV);
        listDataRV = view.findViewById(R.id.listDataRV);
    }

    private void onBind() {
        List<Item> itemList = null;
        Category category = null;

        if (getArguments() == null) {
            Toast.makeText(requireContext(), "Invalid category!", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        } else {
            String categoryId = getArguments().getString("category_id");
            DBItemManager itemManager = new DBItemManager(requireContext());
            itemManager.open();

            if (categoryId == null) {
                itemList = itemManager.getAllItems();
                category = new Category(null, "All", AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_border_all_24));
            } else {
                DBCategoryManager categoryManager = new DBCategoryManager(requireContext());
                categoryManager.open();
                category = categoryManager.getCategoryById(categoryId);
                categoryManager.close();

                itemList = itemManager.getAllItemsByCategory(category);
            }

            itemManager.close();
        }

        assert category != null;
        String title = String.format("Items in %s category", category.getName());
        listTitleTV.setText(title);

        if (!itemList.isEmpty()) {
            listEmptyTV.setVisibility(View.GONE);

            ItemInfoAdapter itemInfoAdapter = new ItemInfoAdapter(itemList);
            listDataRV.setLayoutManager(new LinearLayoutManager(requireContext()));
            listDataRV.setAdapter(itemInfoAdapter);
        }
    }
}