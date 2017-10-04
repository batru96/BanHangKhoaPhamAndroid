package com.example.gietb.banhangkhoapham;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

import adapter.IClickListener;
import adapter.SearchAdapter;
import models.SearchProduct;
import singleton.DataUrl;
import singleton.VolleySingleton;

public class CollectionActivity extends AppCompatActivity implements IClickListener{
    private int numPage = 1;

    private SwipeRefreshLayout mSwiper;
    private RecyclerView revCollection;
    private ArrayList<SearchProduct> ds;
    private SearchAdapter adapter;
    private String requestUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        Intent intent = getIntent();
        String fromContext = intent.getStringExtra("FROM_CONTEXT");
        if (fromContext.equals("CATEGORY")) {
            requestUrl = DataUrl.getCollection;
            int id_type = intent.getIntExtra("ID_TYPE", -1);
            requestUrl = DataUrl.productByTypeUrl + id_type + "&page=";
        } else {
            requestUrl = DataUrl.getCollection;
        }

        initControls();
    }
    private void initControls() {
        revCollection = findViewById(R.id.revCollection);
        ds = new ArrayList<>();
        adapter = new SearchAdapter(this, ds);
        adapter.setClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        revCollection.setLayoutManager(layoutManager);
        revCollection.setAdapter(adapter);
        loadDataToRecyclerView(numPage);

        mSwiper = findViewById(R.id.refreshLayout);
        mSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDataToRecyclerView(numPage++);
                mSwiper.setRefreshing(false);
            }
        });
    }

    private void loadDataToRecyclerView(int page) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, requestUrl + page, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                ds.add(new SearchProduct(obj));
                            } catch (JSONException e) {
                                Log.d("JSON", e.getMessage());
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY_COLLECTION", error.getMessage());
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    @Override
    public void itemClick(View view, int position) {
        SearchProduct product = ds.get(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("ID", product.getId());
        intent.putExtra("STATE", product.getIsNew());
        startActivity(intent);
    }
}
