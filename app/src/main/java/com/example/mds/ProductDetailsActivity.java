package com.example.mds;

import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mds.model.Product;

public class ProductDetailsActivity extends AppCompatActivity {

    TextView name, price, description, farmerName;
    ImageView image;
    DBHelper dbHelper;
    Button btn1, btn2;
    RadioGroup purchaseTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        SessionManagement sessionManagement = new SessionManagement(ProductDetailsActivity.this);
        String emailUser = sessionManagement.getSession();
        Intent intent = getIntent();
        int productId = intent.getIntExtra("idProduct",0);
        dbHelper = new DBHelper(this);
        Product product = dbHelper.getProductById(productId);
        name = findViewById(R.id.productName);
        price = findViewById(R.id.productPrice);
        description = findViewById(R.id.productDescription);
        farmerName = findViewById(R.id.farmerName);
        // image = findViewById(R.id.productPhoto);
        name.setText(product.getProdName());
        price.setText(String.valueOf(product.getProdPrice()));
        description.setText(product.getProdDescription());
        farmerName.setText(product.getFarmerId());

        farmerName.setOnClickListener(v -> {
            Intent intent12 = new Intent(ProductDetailsActivity.this, FarmerProfileActivity.class);
            intent12.putExtra("emailFarmer",product.getFarmerId());
            startActivity(intent12);
        });

        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);

        purchaseTypes = findViewById(R.id.purchasesTypes);

        Cursor cursor = dbHelper.getUser(emailUser);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            if (cursor.getString(4).equals("client")) {
                btn1.setText("Add to cart");
                btn2.setText("See products");
            } else {
                purchaseTypes.setVisibility(View.INVISIBLE);
                findViewById(R.id.chooseText).setVisibility(View.INVISIBLE);
                btn1.setText("Edit product");
                btn2.setText("Delete");
            }
        }



        btn1.setOnClickListener(v -> {
            if(btn1.getText().equals("Add to cart")){
                int selectedId = purchaseTypes.getCheckedRadioButtonId();
                if(selectedId == 0){
                    Toast.makeText(getApplicationContext(), "Choose a type of subscription", Toast.LENGTH_SHORT).show();
                }
                else {
                    RadioButton radioButton = findViewById(selectedId);
                    System.out.println(radioButton.getText());
                    dbHelper.insertProductInShopCart(String.valueOf(farmerName), emailUser, productId,String.valueOf(radioButton.getText()));
                    Intent intent1 = new Intent(ProductDetailsActivity.this, ShopCart.class);
                    intent1.putExtra("idProduct", product.getProdId());
                    startActivity(intent1);
                }
            }
            else{
                Intent intent1 = new Intent(ProductDetailsActivity.this, EditProduct.class);
                intent1.putExtra("idProduct",product.getProdId());
                startActivity(intent1);
            }
        });

        btn2.setOnClickListener(v -> {
            if(btn2.getText().equals("Back to products")){
                Intent intent1 = new Intent(ProductDetailsActivity.this, FeedActivity.class);
                startActivity(intent1);
            }
            else{
                dbHelper.deleteProduct(productId);
                Intent intent1 = new Intent(ProductDetailsActivity.this, FarmerProfileActivity.class);
                Toast.makeText(getApplicationContext(), "Product was deleted", Toast.LENGTH_SHORT).show();
                startActivity(intent1);
            }
        });
    }
}