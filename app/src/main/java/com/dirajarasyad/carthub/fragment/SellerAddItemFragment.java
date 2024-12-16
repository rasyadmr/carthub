package com.dirajarasyad.carthub.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.database.manager.DBCategoryManager;
import com.dirajarasyad.carthub.database.manager.DBItemManager;
import com.dirajarasyad.carthub.manager.ImageManager;
import com.dirajarasyad.carthub.manager.PickerManager;
import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SellerAddItemFragment extends Fragment {
    private ImageView item_editImageIV, item_editDeleteIV;
    private TextView item_editNameTV, item_editPriceTV, item_editStockTV, item_editCategoryTV, item_editNameErrorTV, item_editPriceErrorTV, item_editStockErrorTV, item_editCategoryErrorTV, item_editAddressTV;
    private EditText item_editNameET, item_editPriceET, item_editRatingET, item_editStockET, item_editSellerET, item_editDescriptionET, item_editAddressET;
    private Spinner item_editCategorySpn;
    private Button item_editSaveBtn;
    private Category categorySelected;
    private ActivityResultLauncher<PickVisualMediaRequest> pickLauncher;
    private SessionManager sessionManager;
    private Uri uri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_edit, container, false);

        this.initial(view);
        this.onBind();

        return view;
    }

    private void initial(View view) {
        item_editImageIV = view.findViewById(R.id.item_editImageIV);
        item_editDeleteIV = view.findViewById(R.id.item_editDeleteIV);

        item_editNameTV = view.findViewById(R.id.item_editNameTV);
        item_editPriceTV = view.findViewById(R.id.item_editPriceTV);
        item_editStockTV = view.findViewById(R.id.item_editStockTV);
        item_editCategoryTV = view.findViewById(R.id.item_editCategoryTV);
        item_editAddressTV = view.findViewById(R.id.item_editAddressTV);

        item_editNameET = view.findViewById(R.id.item_editNameET);
        item_editPriceET = view.findViewById(R.id.item_editPriceET);
        item_editRatingET = view.findViewById(R.id.item_editRatingET);
        item_editStockET = view.findViewById(R.id.item_editStockET);
        item_editSellerET = view.findViewById(R.id.item_editSellerET);
        item_editDescriptionET = view.findViewById(R.id.item_editDescriptionET);
        item_editAddressET = view.findViewById(R.id.item_editAddressET);

        item_editNameErrorTV = view.findViewById(R.id.item_editNameErrorTV);
        item_editPriceErrorTV = view.findViewById(R.id.item_editPriceErrorTV);
        item_editStockErrorTV = view.findViewById(R.id.item_editStockErrorTV);
        item_editCategoryErrorTV = view.findViewById(R.id.item_editCategoryErrorTV);


        item_editCategorySpn = view.findViewById(R.id.item_editCategorySpn);
        item_editSaveBtn = view.findViewById(R.id.item_editSaveBtn);

        sessionManager = new SessionManager(requireContext());

        pickLauncher = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    this.uri = uri;

                    if (uri != null) {
                        item_editImageIV.setImageURI(uri);
                    }
                }
        );
    }

    @SuppressLint("SetTextI18n")
    private void onBind() {
        DBCategoryManager categoryManager = new DBCategoryManager(requireContext());
        categoryManager.open();
        List<Category> categories = categoryManager.getAllCategories();
        categoryManager.close();

        List<String> options = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            options.add(categories.get(i).getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        item_editCategorySpn.setAdapter(adapter);

        item_editCategorySpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString();
                for (Category c: categories) {
                    if (c.getName().equals(selected)) {
                        categorySelected = c;
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                categorySelected = null;
            }
        });

        item_editImageIV.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_add_photo_alternate_100));
        item_editImageIV.setOnClickListener(this::onClick);
        item_editDeleteIV.setVisibility(View.GONE);
        item_editSaveBtn.setOnClickListener(this::onClick);
        item_editSaveBtn.setText("Add Item");
        item_editSellerET.setText(sessionManager.getUser().getUsername());
        item_editSellerET.setEnabled(false);
        item_editRatingET.setText("0");
        item_editRatingET.setEnabled(false);
    }

    private void onClick(View view) {
        if (view == item_editImageIV) {
            PickerManager pickerManager = new PickerManager(pickLauncher);
            pickerManager.pickImageOnly();
        } else if (view == item_editSaveBtn) {
            this.clearError();

            String name = item_editNameET.getText().toString();
            String priceText = item_editPriceET.getText().toString();
            String stockText = item_editStockET.getText().toString();
            String address = item_editAddressET.getText().toString();

            if (priceText.isEmpty() & stockText.isEmpty()) {
                item_editPriceTV.setTextColor(getResources().getColor(R.color.danger, null));
                item_editPriceErrorTV.setVisibility(View.VISIBLE);
                item_editPriceErrorTV.setText(R.string.no_input);

                item_editStockTV.setTextColor(getResources().getColor(R.color.danger, null));
                item_editStockErrorTV.setVisibility(View.VISIBLE);
                item_editStockErrorTV.setText(R.string.no_input);
                return;
            } else if (priceText.isEmpty()) {
                item_editPriceTV.setTextColor(getResources().getColor(R.color.danger, null));
                item_editPriceErrorTV.setVisibility(View.VISIBLE);
                item_editPriceErrorTV.setText(R.string.no_input);
                return;
            } else if (stockText.isEmpty()) {
                item_editStockTV.setTextColor(getResources().getColor(R.color.danger, null));
                item_editStockErrorTV.setVisibility(View.VISIBLE);
                item_editStockErrorTV.setText(R.string.no_input);
                return;
            } else if (address.isEmpty()) {
                // TODO Address
                item_editAddressTV.setText("Address Cannot be Empty");
            }

            Integer price = Integer.parseInt(priceText);
            Integer stock = Integer.parseInt(stockText);
            String description = item_editDescriptionET.getText().toString();

            Drawable image;

            if (uri == null) {
                image = AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_no_photography_100);
            } else {
                image = new ImageManager(uri, requireContext()).getImage();
            }

            if (this.validateInput(name, price, stock)) {
                DBItemManager itemManager = new DBItemManager(requireContext());
                itemManager.open();
                itemManager.addItem(name, description, price, stock, 0, image, sessionManager.getUser(), categorySelected, address);

                requireActivity().getSupportFragmentManager().popBackStack();
            }
        }
    }

    private void clearError() {
        item_editNameTV.setTextColor(getResources().getColor(R.color.black, null));
        item_editPriceTV.setTextColor(getResources().getColor(R.color.black, null));
        item_editStockTV.setTextColor(getResources().getColor(R.color.black, null));
        item_editCategoryTV.setTextColor(getResources().getColor(R.color.black, null));

        item_editNameErrorTV.setVisibility(View.GONE);
        item_editPriceErrorTV.setVisibility(View.GONE);
        item_editStockErrorTV.setVisibility(View.GONE);
        item_editCategoryErrorTV.setVisibility(View.GONE);

        item_editNameErrorTV.setText("");
        item_editPriceErrorTV.setText("");
        item_editStockErrorTV.setText("");
        item_editCategoryErrorTV.setText("");
    }

    private boolean validateInput(String name, Integer price, Integer stock) {
        boolean flag = true;

        if (!Pattern.matches("^[a-zA-Z0-9 ]+$", name)) {
            item_editNameTV.setTextColor(getResources().getColor(R.color.danger, null));
            item_editNameErrorTV.setVisibility(View.VISIBLE);
            item_editNameErrorTV.setText(R.string.item_name_error);
            flag = false;
        }

        if (price < 100) {
            item_editPriceTV.setTextColor(getResources().getColor(R.color.danger, null));
            item_editPriceErrorTV.setVisibility(View.VISIBLE);
            item_editPriceErrorTV.setText(R.string.item_price_error);
            flag = false;
        }

        if (stock < 0) {
            item_editStockTV.setTextColor(getResources().getColor(R.color.danger, null));
            item_editStockErrorTV.setVisibility(View.VISIBLE);
            item_editStockErrorTV.setText(R.string.item_stock_error);
            flag = false;
        }

        if (categorySelected == null) {
            item_editCategoryTV.setTextColor(getResources().getColor(R.color.danger, null));
            item_editCategoryErrorTV.setVisibility(View.VISIBLE);
            item_editCategoryErrorTV.setText(R.string.item_category_error);
            flag = false;
        }

        return flag;
    }
}