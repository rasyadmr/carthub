package com.dirajarasyad.carthub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dirajarasyad.carthub.auth.SessionManager;
import com.dirajarasyad.carthub.database.manager.DBCartManager;
import com.dirajarasyad.carthub.database.manager.DBItemManager;
import com.dirajarasyad.carthub.model.Item;

public class ItemActivity extends AppCompatActivity {
    private TextView itemNameTV, itemPriceTV, itemRatingTV, itemStockTV, itemSellerTV, itemCategoryTV, itemDescriptionTV, itemQuantityTV, itemPriceCheckoutTV;
    private Button itemCartBtn;
    private ImageView itemMinusIV, itemPlusIV;
    private Item item;
    private Integer quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.initial();
        this.onBind();

        itemMinusIV.setOnClickListener(this::onClick);
        itemPlusIV.setOnClickListener(this::onClick);
        itemCartBtn.setOnClickListener(this::onClick);
    }

    private void initial() {
        itemNameTV = findViewById(R.id.itemNameTV);
        itemPriceTV = findViewById(R.id.itemPriceTV);
        itemRatingTV = findViewById(R.id.itemRatingTV);
        itemStockTV = findViewById(R.id.itemStockTV);
        itemSellerTV = findViewById(R.id.itemSellerTV);
        itemCategoryTV = findViewById(R.id.itemCategoryTV);
        itemDescriptionTV = findViewById(R.id.itemDescriptionTV);
        itemPriceCheckoutTV = findViewById(R.id.itemPriceCheckoutTV);

        itemMinusIV = findViewById(R.id.itemMinusIV);
        itemPlusIV = findViewById(R.id.itemPlusIV);

        itemQuantityTV = findViewById(R.id.itemQuantityTV);
        itemCartBtn = findViewById(R.id.itemCartBtn);

        Intent intentData = getIntent();
        String itemId = intentData.getStringExtra("item_id");
        DBItemManager itemManager = new DBItemManager(this);
        itemManager.open();
        item = itemManager.getItemById(itemId);
        itemManager.close();
    }

    @SuppressLint("DefaultLocale")
    private void onBind() {
        itemNameTV.setText(item.getName());
        itemPriceTV.setText(String.format("Rp %d", item.getPrice()));
        itemRatingTV.setText(String.format("Rating: %d", item.getRating()));
        itemStockTV.setText(String.format("Stock: %d", item.getStock()));
        itemSellerTV.setText(item.getUser().getUsername());
        itemCategoryTV.setText(item.getCategory().getName());
        itemDescriptionTV.setText(item.getDescription());

        itemQuantityTV.setText(quantity.toString());
        itemPriceCheckoutTV.setText(String.format("Rp %d", item.getPrice() * quantity));
    }

    @SuppressLint("DefaultLocale")
    private void onClick(View view) {
        if (view == itemMinusIV) {
            if (quantity > 1) {
                quantity--;
                itemQuantityTV.setText(quantity.toString());
                itemPriceCheckoutTV.setText(String.format("Rp %d", item.getPrice() * quantity));
            }
        } else if (view == itemPlusIV) {
            if (quantity < 10) {
                quantity++;
                itemQuantityTV.setText(quantity.toString());
                itemPriceCheckoutTV.setText(String.format("Rp %d", item.getPrice() * quantity));
            }
        } else if (view == itemCartBtn) {
            SessionManager sessionManager = new SessionManager(this);
            DBCartManager cartManager = new DBCartManager(this);
            cartManager.open();
            cartManager.addCart(sessionManager.getUser(), item, quantity);
            cartManager.close();
            String text = item.getName() + " has been added to cart!";
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}