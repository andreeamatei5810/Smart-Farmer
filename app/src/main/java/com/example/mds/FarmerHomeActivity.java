package com.example.mds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import chat.ChatContactActivity;

public class FarmerHomeActivity extends AppCompatActivity {

    Button farmerProfile,addProduct,logout,contacts, farmerAccount,subs;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_home);

        SessionManagement sessionManagement = new SessionManagement(FarmerHomeActivity.this);
        String emailUserLog = sessionManagement.getSession();

        farmerProfile = findViewById(R.id.buttonProfile);
        farmerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FarmerHomeActivity.this,FarmerProfileActivity.class);
                i.putExtra("emailFarmer", emailUserLog);
                startActivity(i);
            }
        });

        addProduct = findViewById(R.id.buttonAddProduct);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FarmerHomeActivity.this,UploadActivity.class);
                startActivity(i);
            }
        });

        contacts = findViewById(R.id.buttonChat);
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FarmerHomeActivity.this, ChatContactActivity.class);
                startActivity(i);
            }
        });

        logout = findViewById(R.id.buttonLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement = new SessionManagement(FarmerHomeActivity.this);
                sessionManagement.removeSession();
                Intent i = new Intent(FarmerHomeActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        farmerAccount = findViewById(R.id.buttonAccount);
        farmerAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FarmerHomeActivity.this,UserProfile.class);
                startActivity(i);
            }
        });

//        subs = findViewById(R.id.buttonSub);
//        subs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(FarmerHomeActivity.this, UserSubscriptions.class);
//                startActivity(i);
//            }
//        });
    }
}