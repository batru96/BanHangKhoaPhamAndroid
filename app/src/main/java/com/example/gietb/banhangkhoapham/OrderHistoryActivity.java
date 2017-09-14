package com.example.gietb.banhangkhoapham;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

import adapter.OrderHistoryAdapter;
import models.OrderHistory;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView lvOrderHistory;
    private ArrayList<OrderHistory> ds;
    private OrderHistoryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        initControls();
    }

    private void initControls() {
        Log.d("AAA", "FUNCTION");
        lvOrderHistory = findViewById(R.id.lvOrderHistory);
        lvOrderHistory.setHasFixedSize(true);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lvOrderHistory.setLayoutManager(layoutManager);
        ds = new ArrayList<>();
        ds.add(new OrderHistory("1", "AAA", "222", "333"));
        ds.add(new OrderHistory("1", "AAA", "222", "333"));
        ds.add(new OrderHistory("1", "AAA", "222", "333"));
        ds.add(new OrderHistory("1", "AAA", "222", "333"));
        adapter = new OrderHistoryAdapter(this, ds);
        lvOrderHistory.setAdapter(adapter);
    }
}
