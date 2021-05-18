package com.example.mds;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class ProductActivity extends AppCompatActivity {

    EditText prodName, prodPrice,prodDescription;
    Button addProduct;
    DBHelper dbHelper;
    boolean check;
    private Intent imageReturnedIntent;
    private ImageSwitcher imageview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        prodName = (EditText) findViewById(R.id.productN);
        prodPrice = (EditText) findViewById(R.id.productP);
        prodDescription = (EditText) findViewById(R.id.productD);
        addProduct = (Button) findViewById(R.id.confirm);

        dbHelper = new DBHelper(this);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check =  dbHelper.insertProduct(prodName.getText().toString(), Integer.parseInt(prodPrice.getText().toString()),prodDescription.getText().toString());

                if(check == true){
                    Toast.makeText(ProductActivity.this, "New Client Inserted", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(ProductActivity.this, "NOT INSERTED", Toast.LENGTH_SHORT).show();
                }

            }
        });














    }
}