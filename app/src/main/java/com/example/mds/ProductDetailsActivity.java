package com.example.mds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mds.model.Product;

public class ProductDetailsActivity extends AppCompatActivity {

    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Intent intent = getIntent();
        int productId = intent.getIntExtra("idProduct",0);
        name = findViewById(R.id.productNameee);
        name.setText(String.valueOf(productId));

    }
}