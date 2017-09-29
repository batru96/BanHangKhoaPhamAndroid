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
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.OrderHistoryAdapter;
import models.OrderHistory;
import singleton.CustomJsonRequest;
import singleton.DataUrl;
import singleton.VolleySingleton;

public class OrderHistoryActivity extends AppCompatActivity {

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
        JSONObject jsonObject = new JSONObject(map);

        CustomJsonRequest request = new CustomJsonRequest(Request.Method.POST, DataUrl.orderHistory, jsonObject,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                String id = "ORD" + obj.getInt("id");
                                String date_order = obj.getString("date_order");
                                String status = obj.getInt("status") == 0 ? "Pending" : "Done";
                                String total = obj.getInt("total") + "$";
                                ds.add(new OrderHistory(id, date_order, status, total));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY", error.getMessage());
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void initControls() {
        ImageButton btnBackToMain = findViewById(R.id.backToMainButton);
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        RecyclerView lvOrderHistory = findViewById(R.id.lvOrderHistory);
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
