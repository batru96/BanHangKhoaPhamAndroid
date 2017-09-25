package com.example.gietb.banhangkhoapham;

import android.content.ContentValues;
import android.content.Intent;
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

import com.example.gietb.banhangkhoapham.R;
import com.squareup.picasso.Picasso;

import models.Product;

public class DetailActivity extends AppCompatActivity {
    private ImageButton btnBack, btnAddProductToCart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("PRODUCT");

        initControls(product);
        eventListener(product);
    }

    private void eventListener(final Product product) {
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
        ContentValues values = new ContentValues();
        values.put("Id", product.getId());
        values.put("Name", product.getName());
        values.put("Price", product.getPrice());
        values.put("ImageUrl1", product.getImages()[0]);
        values.put("ImageUrl2", product.getImages()[1]);
        values.put("Counting", 1);

        SQLiteDatabase db = MainActivity.database.getWritableDatabase();
        db.insert(MainActivity.tableCartName, null, values);
    }

    private void initControls(Product product) {
        btnBack = findViewById(R.id.backToMainButton);
        btnAddProductToCart = findViewById(R.id.btnAddProductToCart);
        ImageView img1 = findViewById(R.id.image_detail_1);
        ImageView img2 = findViewById(R.id.image_detail_2);

        Picasso.with(this).load(product.getImages()[0]).into(img1);
        Picasso.with(this).load(product.getImages()[1]).into(img2);

        TextView tvName = findViewById(R.id.nameText);
        TextView tvDecription = findViewById(R.id.decriptionText);
        TextView tvMaterial = findViewById(R.id.materialText);
        TextView tvColor = findViewById(R.id.colorText);

        tvDecription.setText(product.getDescription());
        tvName.setText(product.getName());
        tvMaterial.setText("Material " + product.getMaterial());
        tvColor.setText("Color " + product.getColor());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}