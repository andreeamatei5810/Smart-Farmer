package com.example.mds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import chat.ChatContactActivity;

public class ClientHomeActivity extends AppCompatActivity {

    Button clientProfile,contacts,logout,feed,cart, subs;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        clientProfile = findViewById(R.id.buttonProfile);
        clientProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClientHomeActivity.this,UserProfile.class);
                startActivity(i);
            }
        });

        contacts = findViewById(R.id.buttonChat);
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClientHomeActivity.this, ChatContactActivity.class);
                startActivity(i);
            }
        });

        logout = findViewById(R.id.buttonLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement = new SessionManagement(ClientHomeActivity.this);
                sessionManagement.removeSession();
                Intent i = new Intent(ClientHomeActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        feed = findViewById(R.id.buttonFeed);
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClientHomeActivity.this, FeedActivity.class);
                startActivity(i);
            }
        });

       cart = findViewById(R.id.buttonCart);
       cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClientHomeActivity.this, ShopCart.class);
                startActivity(i);
            }
        });

        subs = findViewById(R.id.buttonSub);
        subs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClientHomeActivity.this, UserSubscriptions.class);
                startActivity(i);
            }
        });
    }
}