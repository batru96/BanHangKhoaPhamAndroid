package main_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.gietb.banhangkhoapham.ActivityDetail;
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

public class TabHomeFragment extends Fragment implements ProductAdapter.ClickListener {
    private ViewPager pager;
    private ArrayList<Category> dsCategory;
    private ListCategoryAdapter categoryAdapter;

    private RecyclerView lvProducts;
    private ArrayList<Product> dsProduct;
    private ProductAdapter productAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_home, container, false);
        initControls(view);
        return view;
    }

    private void initControls(final View view) {
        pager = view.findViewById(R.id.pagerCategory);
        lvProducts = view.findViewById(R.id.lvProducts);
        lvProducts.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        lvProducts.setLayoutManager(layoutManager);
        dsCategory = new ArrayList<>();
        categoryAdapter = new ListCategoryAdapter(view.getContext(), dsCategory);
        pager.setAdapter(categoryAdapter);

        dsProduct = new ArrayList<Product>();
        productAdapter = new ProductAdapter(dsProduct, view.getContext());
        productAdapter.setClickListener(this);
        lvProducts.setAdapter(productAdapter);

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
                                String[] images = {
                                        DataUrl.imageProductUrl + imageArray.getString(0),
                                        DataUrl.imageProductUrl + imageArray.getString(1)
                                };
                                for (int idx = 0; idx < images.length; idx++) {
                                    String imageUrl = images[idx];
                                    images[idx] = imageUrl.replaceAll("jpeg", "jpg");
                                }
                                product.setImages(images);
                                dsProduct.add(product);
                            }

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
    public void itemClicked(View view, int position) {
        Product product = dsProduct.get(position);
        Intent intent = new Intent(getContext(), ActivityDetail.class);
        intent.putExtra("PRODUCT", product);
        startActivity(intent);
    }
}
