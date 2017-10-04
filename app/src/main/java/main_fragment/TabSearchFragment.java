package main_fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gietb.banhangkhoapham.DetailActivity;
import com.example.gietb.banhangkhoapham.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.IClickListener;
import adapter.SearchAdapter;
import models.Product;
import models.SearchProduct;
import singleton.DataUrl;

public class TabSearchFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener, IClickListener {

    private RecyclerView lvSearch;
    private ArrayList<SearchProduct> ds;
    private SearchAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_search, container, false);
        initControls(view);
        return view;
    }

    private void readData(String valueStr) {
        try {
            JSONArray jsonArray = new JSONArray(valueStr);
            Log.d("DDD", jsonArray.toString());
            if (jsonArray.length() != 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObj = jsonArray.getJSONObject(i);
                    ds.add(new SearchProduct(productObj));
                }
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initControls(View view) {
        lvSearch = view.findViewById(R.id.listSearch);
        lvSearch.setHasFixedSize(true);
        lvSearch.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        ds = new ArrayList<>();
        adapter = new SearchAdapter(view.getContext(), ds);
        adapter.setClickListener(this);
        lvSearch.setAdapter(adapter);

        SharedPreferences pre = getContext().getSharedPreferences("DATA_VALUE", Context.MODE_PRIVATE);
        String valueStr = pre.getString("SEARCH_JSON_ARRAY", "[]");
        readData(valueStr);
        pre.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        String valueChanged = sharedPreferences.getString(s, "[]");
        ds.clear();
        readData(valueChanged);
    }

    @Override
    public void itemClick(View view, int position) {
        SearchProduct product = ds.get(position);
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("ID", product.getId());
        intent.putExtra("STATE", product.getIsNew());
        startActivity(intent);
    }
}
