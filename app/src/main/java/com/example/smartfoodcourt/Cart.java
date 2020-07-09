package com.example.smartfoodcourt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartfoodcourt.Adapter.CartStallAdapter;
import com.example.smartfoodcourt.Common.Common;
import com.example.smartfoodcourt.Database.Database;
import com.example.smartfoodcourt.Model.CartStallItem;
import com.example.smartfoodcourt.Model.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requestList;

    TextView txtTotalPrice;
    Button btnPay;

    List<CartStallItem> cartStallItemList = new ArrayList<>();

    CartStallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseDatabase.getInstance();
        requestList = database.getReference("Order/CurrentOrder/List");

        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPay = (Button)findViewById(R.id.btnPayment);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirmOrder();
            }
        });
        loadCart();
    }
    private void confirmOrder() {

        Intent paymentIntent = new Intent(Cart.this, Payment.class);
        startActivity(paymentIntent);

        for(CartStallItem t: cartStallItemList){
            Order order =  new Order(Common.user.getPhone(),Common.user.getPhone(), t);
            requestList.child(String.valueOf(System.currentTimeMillis())).setValue(order);
        }
        new Database(getBaseContext()).cleanCart();
        //Toast.makeText(Cart.this, "Order confirmed", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void loadCart() {
        cartStallItemList = new Database(this).getCart();
        adapter = new CartStallAdapter(cartStallItemList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getBaseContext(),R.anim.layout_item_from_left));

        

        //Calculate Total
        float total = 0;
        for (CartStallItem cartStallItem :cartStallItemList) {
            total += cartStallItem.getTotal();
        }

        txtTotalPrice.setText(Common.convertPricetoVND(total));
    }
}