package com.example.mds;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import chat.ChatContactActivity;

public class MainActivity extends AppCompatActivity {

    private EditText imageDetailsET;
    private ImageView objectImageView;

    Button farmerProfile,login,dbButton,deleteUsers, clientProfile,addProduct,dbProd,contacts,logout,upload;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        farmerProfile = findViewById(R.id.buttonFarmerProfile);
        farmerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,FarmerProfileActivity.class);
                startActivity(i);
            }
        });

        login = findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        logout = findViewById(R.id.buttonLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                sessionManagement.removeSession();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        contacts = findViewById(R.id.contacts);
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ChatContactActivity.class);
                startActivity(i);
            }
        });

        dbHelper = new DBHelper(this);
        dbButton = findViewById(R.id.buttonGetUsers);
        dbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.getUsers();
                if(cursor.getCount() >0){
                    StringBuffer buffer= new StringBuffer();
                    while(cursor.moveToNext()){
                        buffer.append("Email = ").append(cursor.getString(0)).append(", Password = ").append(cursor.getString(1)).append(", Role = ").append(cursor.getString(4)).append("\n");
                    }
                    AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Entries");
                    builder.setMessage(buffer.toString());
                    builder.show();
                }
                else{
                    Toast.makeText(MainActivity.this, "NO ENTRIES", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteUsers = findViewById(R.id.buttonDeleteUsers);
        deleteUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteUsers();
            }
        });

        clientProfile = findViewById(R.id.buttonAccount);
        clientProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ClientProfile.class);
                startActivity(i);
            }


        });


        upload = findViewById(R.id.buttonUpload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,UploadActivity.class);
                startActivity(i);
            }
        });

    }
}