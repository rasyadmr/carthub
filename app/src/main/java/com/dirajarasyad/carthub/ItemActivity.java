package com.dirajarasyad.carthub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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

import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.database.manager.DBCartManager;
import com.dirajarasyad.carthub.database.manager.DBItemManager;
import com.dirajarasyad.carthub.model.Item;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.config.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class ItemActivity extends AppCompatActivity {
    private TextView itemNameTV, itemPriceTV, itemRatingTV, itemStockTV, itemSellerTV, itemCategoryTV, itemDescriptionTV, itemQuantityTV, itemPriceCheckoutTV, itemAddressTV;
    private Button itemCartBtn;
    private ImageView itemImageIV, itemMinusIV, itemPlusIV;

    private Item item;

    private MapView itemMapView;
    private Integer quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().setUserAgentValue("CartHub/1.0");
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

        // Initialize map
        this.initializeMap();

        // Convert address to coordinates
        this.setMapLocation(item.getAddress());
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
        itemAddressTV = findViewById(R.id.itemAddressTV);

        itemImageIV = findViewById(R.id.itemImageIV);
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

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void onBind() {
        itemImageIV.setImageDrawable(item.getImage());
        itemNameTV.setText(item.getName());
        itemPriceTV.setText(String.format("Rp %d", item.getPrice()));
        itemRatingTV.setText(String.format("Rating: %d", item.getRating()));
        itemStockTV.setText(String.format("Stock: %d", item.getStock()));
        itemSellerTV.setText(item.getUser().getUsername());
        itemCategoryTV.setText(item.getCategory().getName());
        itemDescriptionTV.setText(item.getDescription());

        itemQuantityTV.setText(quantity.toString());
        itemPriceCheckoutTV.setText(String.format("Rp %d", item.getPrice() * quantity));
        itemAddressTV.setText(item.getAddress());
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
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

    private void initializeMap() {
        itemMapView = findViewById(R.id.itemMapView);

        itemMapView.setTileSource(TileSourceFactory.MAPNIK);
        itemMapView.setBuiltInZoomControls(true);
        itemMapView.setMultiTouchControls(true);

    }

    private void setMapLocation(String address) {
        // Use Geocoder to convert address into latitude and longitude
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                android.location.Address location = addresses.get(0);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                // Set the location on the map
                GeoPoint geoPoint = new GeoPoint(latitude, longitude);
                IMapController mapController = itemMapView.getController();
                mapController.setZoom(15); // Zoom level
                mapController.setCenter(geoPoint); // Center map at this location

                // Add a marker to the map at the location
                Marker marker = new Marker(itemMapView);
                marker.setPosition(geoPoint);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setTitle("Location");
                itemMapView.getOverlays().add(marker);
            } else {
                Toast.makeText(this, "Address not found!", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error occurred while geocoding the address.", Toast.LENGTH_SHORT).show();
        }
    }


}