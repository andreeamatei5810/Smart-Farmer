package com.example.mds;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;

public class ClientProfile extends AppCompatActivity {

    Button privacyButton;
    Button deleteButton;
    Button editButton;
    DBHelper database;
    TextView username;
    TextView phoneNumber;
    TextView preferences;
    Button backMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);
        database = new DBHelper(this);
        ArrayList<String> str = database.getClientInfo("test@gmail.com");
        if (str.size() != 6) {
            Intent i = new Intent(ClientProfile.this, MainActivity.class);
            startActivity(i);
        } else {
            username = findViewById(R.id.seeUsername);
            phoneNumber = findViewById(R.id.seePhoneNumber);
            preferences = findViewById(R.id.seePreferences);
            username.setText(str.get(2));
            phoneNumber.setText(str.get(3));
            preferences.setText(str.get(4));
            final String email = String.valueOf(str.get(0));
            final String password = String.valueOf(str.get(1));
            String privacyType = str.get(5);

            privacyButton = findViewById(R.id.editPrivacy);
            if (privacyType.equalsIgnoreCase("public")) {
                privacyButton.setText("MAKE PROFILE PRIVATE");
            } else {
                privacyButton.setText("MAKE PROFILE PUBLIC");
            }

            privacyButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (privacyButton.getText().equals("MAKE PROFILE PRIVATE")) {
                        database.editUserProfile(email, email, password, username.getText().toString(),
                                phoneNumber.getText().toString(), preferences.getText().toString(), "private");
                        privacyButton.setText("MAKE PROFILE PUBLIC");
                    } else {
                        database.editUserProfile(email, email, password, username.getText().toString(),
                                phoneNumber.getText().toString(), preferences.getText().toString(), "public");
                        privacyButton.setText("MAKE PROFILE PRIVATE");
                    }


                }
            });

            deleteButton = findViewById(R.id.deleteAccount);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    database.deleteClient(email);
                    Intent i = new Intent(ClientProfile.this, MainActivity.class);
                    startActivity(i);
                }
            });

            editButton = findViewById(R.id.editAccount);
            editButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(ClientProfile.this, ClientEditProfile.class);
                    startActivity(i);
                }
            });

            backMain = findViewById(R.id.backMain);
            backMain.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(ClientProfile.this, MainActivity.class);
                    startActivity(i);
                }
            });

        }
    }



}
