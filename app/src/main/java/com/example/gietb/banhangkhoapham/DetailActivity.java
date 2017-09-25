package com.example.gietb.banhangkhoapham;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.gietb.banhangkhoapham.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

import main_fragment.TabCartFragment;
import models.Cart;
import models.Product;
import singleton.DataUrl;
import singleton.VolleySingleton;

public class DetailActivity extends AppCompatActivity {
    private ImageButton btnBack, btnAddProductToCart;
    private ImageView img1, img2;
    private TextView tvName, tvDecription, tvMaterial, tvColor;

    private Product product;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        int productId = intent.getIntExtra("ID", -1);

        initControls(productId);
        eventListener();
    }

    private void setValues() {
        tvName.setText(product.getName());
        tvColor.setText("Color: " + product.getColor());
        tvMaterial.setText("Material: " + product.getMaterial());
        tvDecription.setText(product.getDescription());

        Picasso.with(this).load(product.getImages()[0]).into(img1);
        Picasso.with(this).load(product.getImages()[1]).into(img2);
    }

    private void eventListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnAddProductToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductToCart(product);
            }
        });
    }

    private void addProductToCart(Product product) {
        if (product.getName().equals("null")) {
            Toast.makeText(this, "Sorry, but SERVER IS FAILED!", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put("Id", product.getId());
        values.put("Name", product.getName());
        values.put("Price", product.getPrice());
        values.put("ImageUrl1", product.getImages()[0]);
        values.put("ImageUrl2", product.getImages()[1]);
        values.put("Counting", 1);

        SQLiteDatabase db = MainActivity.database.getWritableDatabase();
        long isSuccess = db.insert(MainActivity.tableCartName, null, values);
        if (isSuccess == -1) {
            return;
        }
        Cart cart = new Cart(product.getId(), product.getName(), product.getPrice(), product.getImages(), 1);
        TabCartFragment.ds.add(cart);
        TabCartFragment.adapter.notifyDataSetChanged();
    }

    private void initControls(final int id) {
        btnBack = findViewById(R.id.backToMainButton);
        btnAddProductToCart = findViewById(R.id.btnAddProductToCart);
        img1 = findViewById(R.id.image_detail_1);
        img2 = findViewById(R.id.image_detail_2);

        tvName = findViewById(R.id.nameText);
        tvDecription = findViewById(R.id.decriptionText);
        tvMaterial = findViewById(R.id.materialText);
        tvColor = findViewById(R.id.colorText);

        String url = DataUrl.productDetailUrl + id;
        JsonArrayRequest arrRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    product = new Product();
                    JSONObject productObj = response.getJSONObject(0);
                    product.setId(id);
                    product.setName(productObj.getString("nameProduct"));
                    product.setMaterial(productObj.getString("material"));
                    product.setColor(productObj.getString("color"));
                    product.setDescription(productObj.getString("description"));
                    product.setPrice(productObj.getInt("price"));

                    JSONArray imageArray = productObj.getJSONArray("images");
                    String[] images = DataUrl.convertJsonImgArrToStrArr(imageArray);
                    product.setImages(images);

                    setValues();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY_DETAIL", error.getMessage());
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(arrRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}