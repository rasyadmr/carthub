package com.dirajarasyad.carthub;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.adapter.CheckoutAdapter;
import com.dirajarasyad.carthub.database.manager.DBCartManager;
import com.dirajarasyad.carthub.database.manager.DBTransactionManager;
import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.model.Cart;
import com.dirajarasyad.carthub.model.Transaction;
import com.dirajarasyad.carthub.model.User;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    private RecyclerView checkoutDataRV;
    private TextView checkoutGrandTotalTV;
    private Button checkoutPayBtn;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.initial();
        this.onBind();
    }

    private void initial() {
        checkoutDataRV = findViewById(R.id.checkoutDataRV);
        checkoutGrandTotalTV = findViewById(R.id.checkoutGrandTotalTV);
        checkoutPayBtn = findViewById(R.id.checkoutPayBtn);

        sessionManager = new SessionManager(this);
    }

    @SuppressLint("DefaultLocale")
    private void onBind() {
        User user = sessionManager.getUser();

        DBCartManager cartManager = new DBCartManager(this);
        cartManager.open();
        List<Cart> cartList = cartManager.getAllCartsByUser(user);
        cartManager.close();

        CheckoutAdapter checkoutAdapter = new CheckoutAdapter(cartList);
        checkoutDataRV.setLayoutManager(new LinearLayoutManager(this));
        checkoutDataRV.setAdapter(checkoutAdapter);

        int total = 0;
        for (Cart c :
                cartList) {
            total += c.getQuantity() * c.getItem().getPrice();
        }

        checkoutGrandTotalTV.setText(String.format("Rp %d", total));
        checkoutPayBtn.setOnClickListener(view -> {
            DBTransactionManager transactionManager = new DBTransactionManager(this);
            transactionManager.open();
            cartManager.open();
            for (Cart c :
                    cartList) {
                transactionManager.addTransaction(Transaction.Status.PENDING, user, c.getItem(), c.getQuantity());
                cartManager.deleteCart(c.getId());
            }
            transactionManager.close();
            cartManager.close();
            finish();
        });
    }
}