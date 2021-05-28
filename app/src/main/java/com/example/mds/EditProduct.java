package com.example.mds;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mds.model.Product;

public class EditProduct extends AppCompatActivity {
    String name, description;
    int price;
    private EditText productName, productPrice, productDescription;
    private ImageView objectImageView;
    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;
    private Bitmap imageToStore;
    Button edit;
    DBHelper dbHelper;
    private int productId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Intent intent = getIntent();
        productId = intent.getIntExtra("idProduct",0);
        try {
            productName = findViewById(R.id.prodN);
            productPrice = findViewById(R.id.prodP);
            productDescription = findViewById(R.id.prodD);
            objectImageView = findViewById(R.id.image);
            dbHelper = new DBHelper(this);
            Product product = dbHelper.getProductById(productId);
            productName.setText(product.getProdName());
            productPrice.setText(String.valueOf(product.getProdPrice()));
            productDescription.setText(product.getProdDescription());
            edit = findViewById(R.id.buttonSave);
            edit.setText("Edit product");

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void chooseImage(View objectView) {
        try {
            Intent objectIntent = new Intent();
            objectIntent.setType("image/*");
            objectIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent, PICK_IMAGE_REQUEST);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);
                objectImageView.setImageBitmap(imageToStore);

            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void storeImage(View view) {
        try {
            if (!productName.getText().toString().isEmpty() && objectImageView.getDrawable() != null && imageToStore != null) {
                name = productName.getText().toString();
                price = Integer.parseInt(productPrice.getText().toString());
                description = productDescription.getText().toString();
                SessionManagement sessionManagement = new SessionManagement(EditProduct.this);
                String emailUser = sessionManagement.getSession();
                dbHelper.editProduct(new Product(emailUser, name, price, description, imageToStore), productId);
                Intent intent1 = new Intent(EditProduct.this, FarmerProfileActivity.class);
                startActivity(intent1);
            } else {
                Toast.makeText(this, "Please fill in all details about the product", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

