package com.example.mds;

import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button farmerProfile,login,dbButton,deleteUsers, clientProfile;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        farmerProfile = findViewById(R.id.button);
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

        dbHelper = new DBHelper(this);
        dbButton = findViewById(R.id.buttonGetUsers);
        dbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.getUsers();
                if(cursor.getCount() >0){
                    StringBuffer buffer= new StringBuffer();
                    while(cursor.moveToNext()){
                        buffer.append("Email = " + cursor.getString(0) + ", Password = " + cursor.getString(1) + "\n");
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

        deleteUsers = findViewById(R.id.buttonDelete);
        deleteUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteUsers();
            }
        });

        clientProfile = findViewById(R.id.buttonCont);
        clientProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ClientProfile.class);
                startActivity(i);
            }
        });




//
//        db = new DBHelper(this);
//
//        insert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String aux = text.getText().toString();
//
//                Boolean check = db.insertUserData(aux);
//                if(check == true){
//                    Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(MainActivity.this, "NOT INSERTED", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String aux = text.getText().toString();
//
//                Boolean check = db.updateUserData(aux);
//                if(check == true){
//                    Toast.makeText(MainActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(MainActivity.this, "NOT UPDATED", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String aux = text.getText().toString();
//
//                Boolean check = db.deleteUserData(aux);
//                if(check == true){
//                    Toast.makeText(MainActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(MainActivity.this, "NOT DELETED", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        get.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Cursor cursor = db.getUserData();
//                if(cursor.getCount() >0){
//                    StringBuffer buffer= new StringBuffer();
//                    while(cursor.moveToNext()){
//                        buffer.append("Name = " + cursor.getString(0) + "\n");
//                    }
//                    AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
//                    builder.setCancelable(true);
//                    builder.setTitle("Entries");
//                    builder.setMessage(buffer.toString());
//                    builder.show();
//                }
//                else{
//                    Toast.makeText(MainActivity.this, "NO ENTRIES", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


    }
}