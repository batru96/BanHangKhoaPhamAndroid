package main_fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gietb.banhangkhoapham.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.SearchAdapter;
import models.Product;
import singleton.DataUrl;

public class TabSearchFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView lvSearch;
    private ArrayList<Product> ds;
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
            if (jsonArray.length() != 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObj = jsonArray.getJSONObject(i);
                    Product product = new Product();
                    product.setId(productObj.getInt("id"));
                    product.setName(productObj.getString("name"));
                    product.setPrice(productObj.getInt("price"));
                    product.setMaterial(productObj.getString("material"));
                    product.setColor(productObj.getString("color"));

                    JSONArray imgArray = productObj.getJSONArray("images");
                    String[] images = DataUrl.convertJsonImgArrToStrArr(imgArray);
                    product.setImages(images);

                    ds.add(product);
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
}
