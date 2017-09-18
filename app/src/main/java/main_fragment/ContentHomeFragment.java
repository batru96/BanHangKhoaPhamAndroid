package main_fragment;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.gietb.banhangkhoapham.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.ListCategoryAdapter;
import adapter.ProductAdapter;
import models.Category;
import models.Product;
import singleton.DataUrl;
import singleton.VolleySingleton;

public class ContentHomeFragment extends Fragment {
    private RecyclerView lvProducts;
    private ArrayList<Product> ds;

    private ViewPager pager;
    private ListCategoryAdapter categoryAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_home, container, false);
        initControls(view);
        return view;

    }

    private void initControls(final View view) {
        pager = view.findViewById(R.id.pagerCategory);
        lvProducts = view.findViewById(R.id.lvProducts);
        lvProducts.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        lvProducts.setLayoutManager(layoutManager);

        ds = new ArrayList<Product>();

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, DataUrl.indexUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray typeArray = response.getJSONArray("type");
                            ArrayList<Category> dsCategory = new ArrayList<>();
                            for (int i = 0; i < typeArray.length(); i++) {
                                Category category = new Category();
                                JSONObject typeObj = typeArray.getJSONObject(i);
                                category.setId(typeObj.getInt("id"));
                                category.setName(typeObj.getString("name"));
                                category.setImage(typeObj.getString("image"));
                                dsCategory.add(category);
                            }
                            categoryAdapter = new ListCategoryAdapter(view.getContext(), dsCategory);
                            pager.setAdapter(categoryAdapter);

                            JSONArray productArray = response.getJSONArray("product");
                            for (int i = 0; i < productArray.length(); i++) {
                                Product product = new Product();
                                JSONObject obj = productArray.getJSONObject(i);
                                product.setId(obj.getInt("id"));
                                product.setName(obj.getString("name"));
                                product.setIdType(obj.getInt("idType"));
                                product.setPrice(obj.getInt("price"));
                                product.setColor(obj.getString("color"));
                                product.setMaterial(obj.getString("material"));
                                product.setDescription(obj.getString("description"));

                                JSONArray imageArray = obj.getJSONArray("images");
                                String[] images = {imageArray.getString(0), imageArray.getString(1)};
                                product.setImages(images);
                                ds.add(product);
                            }
                            ProductAdapter productAdapter = new ProductAdapter(ds, view.getContext());
                            lvProducts.setAdapter(productAdapter);
                        } catch (JSONException e) {
                            Log.d("REQUEST_DATA", e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("AAA", error.getMessage());
                    }
                });
        VolleySingleton.getInstance(view.getContext()).addToRequestQueue(jsObjRequest);
    }
}