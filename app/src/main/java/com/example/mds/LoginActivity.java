package com.example.mds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    TextView register;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailError, passError;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        emailError = (TextInputLayout) findViewById(R.id.emailError);
        passError = (TextInputLayout) findViewById(R.id.passError);
        dbHelper = new DBHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean check = SetValidation();
                if(check){
                    User user = new User(email.getText().toString());
                    Cursor cursor = dbHelper.getUser(email.getText().toString());
                    cursor.moveToFirst();
                    SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                    sessionManagement.saveSession(user);
                    if(cursor.getString(4).equals("client")){
                        Intent intent = new Intent(LoginActivity.this,ClientHomeActivity.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(LoginActivity.this, FarmerHomeActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        String email = sessionManagement.getSession();
        if (email != null) {
            Cursor cursor = dbHelper.getUser(email);
            if(cursor.getCount()!=0){
            cursor.moveToFirst();
            if (cursor.getString(4).equals("client")) {
                Intent intent = new Intent(LoginActivity.this, ClientHomeActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(LoginActivity.this, FarmerHomeActivity.class);
                startActivity(intent);
            }
            }
        }
    }

    public boolean SetValidation() {
        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            emailError.setError("Please write an email address");
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailError.setError("Invalid email");
            isEmailValid = false;
        } else  {
            isEmailValid = true;
            emailError.setErrorEnabled(false);
        }

        // Check for a valid password.
        if (password.getText().toString().isEmpty()) {
            passError.setError("Please write a password");
            isPasswordValid = false;
        } else if (password.getText().length() < 6) {
            passError.setError("The password must be at least 6 characters long");
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }

        if (isEmailValid && isPasswordValid) {
            System.out.println(email.getText().toString());
            Cursor cursor = dbHelper.getUser(email.getText().toString());
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                if(password.getText().toString().equals(cursor.getString(1))){
                    Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
                    return true;
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "No account with this email address", Toast.LENGTH_SHORT).show();
            }

        }
        return false;

    }
}