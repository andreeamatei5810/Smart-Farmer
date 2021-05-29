package com.example.mds;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UserProfileEdit extends AppCompatActivity {
    Button profileButton;
    DBHelper database;
    EditText emailText;
    EditText passwordText;
    EditText usernameText;
    EditText phoneText;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        database = new DBHelper(this);
        SessionManagement sessionManagement = new SessionManagement(UserProfileEdit.this);
        String emailUser = sessionManagement.getSession();
        ArrayList<String> str = database.getUserInfo(emailUser);
        emailText = findViewById(R.id.editEmail);
        passwordText = findViewById(R.id.editPassword);
        usernameText = findViewById(R.id.editUsername);
        phoneText = findViewById(R.id.editPhone);
        emailText.setText(str.get(0));
        final String emailTextFirstValue =  String.valueOf(emailText.getText());
        passwordText.setText(str.get(1));
        usernameText.setText(str.get(2));
        phoneText.setText(str.get(3));
        emailText.setEnabled(false);
        passwordText.setEnabled(false);
        usernameText.setEnabled(false);
        phoneText.setEnabled(false);


        profileButton = findViewById(R.id.editProfile);
        profileButton.setOnClickListener(v -> {
            enableDisableEditText(phoneText, profileButton);
            enableDisableEditText(passwordText, profileButton);
            enableDisableEditText(usernameText, profileButton);
            if(!String.valueOf(profileButton.getText()).equalsIgnoreCase("Done editing")){
                String passwordNewValue = String.valueOf(passwordText.getText());
                String usernameNewValue = String.valueOf(usernameText.getText());
                String phoneNewValue = String.valueOf(phoneText.getText());
                database.editUserProfile(emailTextFirstValue, passwordNewValue, usernameNewValue,
                        phoneNewValue);

            }
        });

        backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            Intent i = new Intent(UserProfileEdit.this, UserProfile.class);
            i.putExtra("email", emailUser);
            startActivity(i);
        });

    }

    private void enableDisableEditText(EditText text, Button btn) {
        if (text.isEnabled()) {
            text.setEnabled(false);
            btn.setText("EDIT PROFILE");
        } else {
            text.setEnabled(true);
            String aux = "Done editing";
            btn.setText(aux);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.home) {
            Intent intent = new Intent(getApplicationContext(), GoHome.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
