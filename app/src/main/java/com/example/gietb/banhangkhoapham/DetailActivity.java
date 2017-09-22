package com.example.gietb.banhangkhoapham;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
    ImageButton btnBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("PRODUCT");

        initControls(product);
        eventListener();
    }

    private void eventListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initControls(Product product) {
        btnBack = findViewById(R.id.backToMainButton);
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
