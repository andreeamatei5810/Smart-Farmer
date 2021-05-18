package com.example.mds;


import android.content.Intent;
import android.widget.Button;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;

public class ClientEditProfile extends AppCompatActivity {

    Button accountButton;
    Button profileButton;
    DBHelper database;
    EditText emailText;
    EditText passwordText;
    EditText usernameText;
    EditText preferencesText;
    EditText phoneText;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        database = new DBHelper(this);
        Intent pastIntent = getIntent();
        String emailUser = pastIntent.getStringExtra("email");
        ArrayList<String> str = database.getClientInfo(emailUser);
        emailText = findViewById(R.id.editEmail);
        passwordText = findViewById(R.id.editPassword);
        usernameText = findViewById(R.id.editUsername);
        preferencesText = findViewById(R.id.editPreferences);
        phoneText = findViewById(R.id.editPhone);
        emailText.setText(str.get(0));
        final String emailTextFirstValue =  String.valueOf(emailText.getText());
        passwordText.setText(str.get(1));
        final String passwordTextFirstValue =  String.valueOf(passwordText.getText());
        usernameText.setText(str.get(2));
        final String usernameTextFirstValue =  String.valueOf(usernameText.getText());
        phoneText.setText(str.get(3));
        final String phoneTextFirstValue =  String.valueOf(phoneText.getText());
        preferencesText.setText(str.get(4));
        final String preferencesTextFirstValue =  String.valueOf(preferencesText.getText());
        final String privacyType =  str.get(5);
        emailText.setEnabled(false);
        passwordText.setEnabled(false);
        usernameText.setEnabled(false);
        preferencesText.setEnabled(false);
        phoneText.setEnabled(false);

        profileButton = findViewById(R.id.editProfile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                enableDisableEditText(phoneText, profileButton, "EDIT PROFILE");
                enableDisableEditText(preferencesText, profileButton, "EDIT PROFILE");
                if(!String.valueOf(profileButton.getText()).equalsIgnoreCase("Done editing")){
                    String phoneNewValue =  String.valueOf(phoneText.getText());
                    String preferencesNewValue =  String.valueOf(preferencesText.getText());
                    database.editUserProfile(emailTextFirstValue, emailTextFirstValue,passwordTextFirstValue,
                            usernameTextFirstValue, phoneNewValue,preferencesNewValue,privacyType);
                }
            }
        });

        accountButton = findViewById(R.id.editAccount);
        accountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                enableDisableEditText(emailText, accountButton, "EDIT ACCOUNT");
                enableDisableEditText(passwordText, accountButton, "EDIT ACCOUNT");
                enableDisableEditText(usernameText, accountButton, "EDIT ACCOUNT");
                if(!String.valueOf(accountButton.getText()).equalsIgnoreCase("Done editing")){
                    String emailNewValue = String.valueOf(emailText.getText());
                    String passwordNewValue = String.valueOf(passwordText.getText());
                    String usernameNewValue = String.valueOf(usernameText.getText());
                    database.editUserProfile(emailTextFirstValue, emailNewValue, passwordNewValue, usernameNewValue,
                            phoneTextFirstValue, preferencesTextFirstValue,privacyType);

                }
            }
        });

        backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ClientEditProfile.this, ClientProfile.class);
                i.putExtra("email",emailUser);
                startActivity(i);
            }
        });

    }

    private void enableDisableEditText(EditText text, Button btn, String textButton) {
        if (text.isEnabled()) {
            text.setEnabled(false);
            btn.setText(textButton);
        } else {
            text.setEnabled(true);
            String aux = "Done editing";
            btn.setText(aux);
        }
    }
}