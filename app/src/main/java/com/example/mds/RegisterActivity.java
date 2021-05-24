
package com.example.mds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    EditText name, email, phone, password;
    RadioButton radioButtonFarmer,radioButtonClient;
    Button register;
    TextView login;
    boolean isNameValid, isEmailValid, isPhoneValid, isPasswordValid, isButtonChecked;
    TextInputLayout nameError, emailError, phoneError, passError;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        radioButtonClient = findViewById(R.id.radioButtonClient);
        radioButtonFarmer = findViewById(R.id.radioButtonFarmer);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        nameError = findViewById(R.id.nameError);
        emailError = findViewById(R.id.emailError);
        phoneError = findViewById(R.id.phoneError);
        passError = findViewById(R.id.passError);
        dbHelper = new DBHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void SetValidation() {
        // Check for a valid name.
        if (name.getText().toString().isEmpty()) {
            nameError.setError("Please write a name");
            isNameValid = false;
        } else  {
            isNameValid = true;
            nameError.setErrorEnabled(false);
        }

        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            emailError.setError("Please write an email");
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailError.setError("Invalid email");
            isEmailValid = false;
        } else if(dbHelper.getUser(email.getText().toString()).getCount()>0) {
            emailError.setError("There is already an account with this id");
            isEmailValid = false;
        }
        else{
            isEmailValid = true;
            emailError.setErrorEnabled(false);
        }

        // Check for a valid phone number.
        if (phone.getText().toString().isEmpty()) {
            phoneError.setError("Please write a phone number");
            isPhoneValid = false;
        } else  {
            isPhoneValid = true;
            phoneError.setErrorEnabled(false);
        }

        // Check for a valid password.
        if (password.getText().toString().isEmpty()) {
            passError.setError("Please write a password");
            isPasswordValid = false;
        } else if (password.getText().length() < 6) {
            passError.setError("At least 6 characters");
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }

        if(radioButtonFarmer.isChecked() || radioButtonClient.isChecked()){
            isButtonChecked = true;
        }else{
            isButtonChecked =  false;
        }

        if (isNameValid && isEmailValid && isPhoneValid && isPasswordValid && isButtonChecked) {
            String role = radioButtonClient.isChecked() == true ? "client" : "farmer";
            Boolean check = dbHelper.insertUser(email.getText().toString(),password.getText().toString(),name.getText().toString(),phone.getText().toString(),role);
            if(check == true){
                Toast.makeText(RegisterActivity.this, "New Client Inserted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(RegisterActivity.this, "NOT INSERTED", Toast.LENGTH_SHORT).show();
            }
        }

    }
}