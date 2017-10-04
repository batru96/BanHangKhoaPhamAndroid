package main_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.gietb.banhangkhoapham.CollectionActivity;
import com.example.gietb.banhangkhoapham.DetailActivity;
import com.example.gietb.banhangkhoapham.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.IClickListener;
import adapter.ListCategoryAdapter;
import adapter.ProductAdapter;
import models.Category;
import models.Product;
import singleton.DataUrl;
import singleton.VolleySingleton;

public class TabHomeFragment extends Fragment implements IClickListener {
    private ViewPager pager;
    private ArrayList<Category> dsCategory;
    private ListCategoryAdapter categoryAdapter;

    private RecyclerView lvProducts;
    private ArrayList<Product> dsProduct;
    private ProductAdapter productAdapter;

    private ImageButton btnCollection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_home, container, false);
        initControls(view);
        return view;
    }

    private void initControls(View view) {
        pager = view.findViewById(R.id.pagerCategory);
        dsCategory = new ArrayList<>();
        categoryAdapter = new ListCategoryAdapter(view.getContext(), dsCategory);
        pager.setAdapter(categoryAdapter);

        lvProducts = view.findViewById(R.id.lvProducts);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        lvProducts.setLayoutManager(layoutManager);
        lvProducts.setHasFixedSize(true);
        dsProduct = new ArrayList<>();
        productAdapter = new ProductAdapter(dsProduct, getContext());
        productAdapter.setClickListener(this);
        lvProducts.setAdapter(productAdapter);

        btnCollection = view.findViewById(R.id.collectionButton);
        btnCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CollectionActivity.class);
                intent.putExtra("FROM_CONTEXT", "COLLECTION");
                startActivity(intent);
            }
        });


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, DataUrl.indexUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray typeArray = response.getJSONArray("type");
                            for (int i = 0; i < typeArray.length(); i++) {
                                Category category = new Category();
                                JSONObject typeObj = typeArray.getJSONObject(i);
                                category.setId(typeObj.getInt("id"));
                                category.setName(typeObj.getString("name"));
                                category.setImage(typeObj.getString("image"));
                                dsCategory.add(category);
                            }
                            categoryAdapter.notifyDataSetChanged();

                            JSONArray productArray = response.getJSONArray("product");
                            for (int i = 0; i < productArray.length(); i++) {
                                JSONObject obj = productArray.getJSONObject(i);
                                dsProduct.add(new Product(obj));
                            }
                            productAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.d("REQUEST_DATA", e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY", error.getMessage());
                    }
                });
        VolleySingleton.getInstance(view.getContext()).addToRequestQueue(jsObjRequest);
    }

    @Override
    public void itemClick(View view, int position) {
        Product product = dsProduct.get(position);
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("ID", product.getId());
        startActivity(intent);
    }
}
