package com.example.gietb.banhangkhoapham;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.OrderHistoryAdapter;
import models.OrderHistory;
import singleton.DataUrl;
import singleton.VolleySingleton;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView lvOrderHistory;
    private ArrayList<OrderHistory> ds;
    private OrderHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        initControls();
        getData();
    }

    private void getData() {
        String token = getIntent().getStringExtra("TOKEN");
        Log.d("TOKEN", token);
        final Map<String, String> map = new HashMap<>();
        map.put("token", token);
        final JSONObject jsonObject = new JSONObject(map);

        StringRequest request = new StringRequest(Request.Method.POST, DataUrl.orderHistory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(res);
    }

    private void initControls() {
        ImageButton btnBackToMain = findViewById(R.id.backToMainButton);
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        lvOrderHistory = findViewById(R.id.lvOrderHistory);
        lvOrderHistory.setHasFixedSize(true);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lvOrderHistory.setLayoutManager(layoutManager);
        ds = new ArrayList<>();
        adapter = new OrderHistoryAdapter(this, ds);
        lvOrderHistory.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
