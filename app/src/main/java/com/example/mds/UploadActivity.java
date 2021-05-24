package com.example.mds;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UploadActivity extends AppCompatActivity  {
    String name,description;
    int price;
    private EditText productName,productPrice,productDescription;
    private ImageView objectImageView;
    private static final int PICK_IMAGE_REQUEST=100;
    private Uri imageFilePath;
    private Bitmap imageToStore;
    DBHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        try{
            productName = findViewById(R.id.prodN);
            productPrice = findViewById(R.id.prodP);
            productDescription=findViewById(R.id.prodD);
            objectImageView = findViewById(R.id.image);

            dbHelper = new DBHelper(this);

        }
        catch(Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    public void chooseImage(View objectView){
        try{
            Intent objectIntent = new Intent();
            objectIntent.setType("image/*");

            objectIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent,PICK_IMAGE_REQUEST);

        }
        catch(Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData()!=null){

                imageFilePath=data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);

                objectImageView.setImageBitmap(imageToStore);

            }
        }
        catch(Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }

    }

    public void storeImage(View view){
        try {
            if (!productName.getText().toString().isEmpty() && objectImageView.getDrawable() != null && imageToStore != null) {
                name = productName.getText().toString();
                price= Integer.parseInt(productPrice.getText().toString());
                description=productDescription.getText().toString();
                dbHelper.addProduct(new ProductClass(name,price,description, imageToStore));
            }

            else {
                Toast.makeText(this, "Please fill in all details about the product", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    public void moveToShowActivity(View view){
        Intent i = new Intent(this,ShowImagesActivity.class);
        startActivity(i);

    }
}
