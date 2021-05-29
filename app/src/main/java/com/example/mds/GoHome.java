package com.example.mds;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class GoHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        DBHelper database = new DBHelper(this);
        SessionManagement sessionManagement = new SessionManagement(GoHome.this);
        String emailUser = sessionManagement.getSession();
        Cursor cursor = database.getUser(emailUser);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            if (cursor.getString(4).equals("client")) {
                Intent intent = new Intent(GoHome.this, ClientHomeActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(GoHome.this, FarmerHomeActivity.class);
                startActivity(intent);
            }
        }
    }
}
