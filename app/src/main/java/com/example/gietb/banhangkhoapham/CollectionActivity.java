package com.example.gietb.banhangkhoapham;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import adapter.SearchAdapter;
import models.SearchProduct;
import singleton.DataUrl;
import singleton.VolleySingleton;

public class CollectionActivity extends AppCompatActivity {
    private int numPage = 1;

    private RecyclerView revCollection;
    private ArrayList<SearchProduct> ds;
    private SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        initControls();
    }
    private void initControls() {
        revCollection = findViewById(R.id.revCollection);
        ds = new ArrayList<>();
        adapter = new SearchAdapter(this, ds);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        revCollection.setLayoutManager(layoutManager);
        revCollection.setAdapter(adapter);
        loadDataToRecyclerView(numPage);

       revCollection.addOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);
           }
       });
    }

    private void loadDataToRecyclerView(int page) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, DataUrl.getCollection + page, null,
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
}
