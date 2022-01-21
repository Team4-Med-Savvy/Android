package com.example.medsavvy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        findViewById(R.id.bn_signup_submit).setOnClickListener(v -> {
            boolean isAllFieldChecked=CheckAllFields();

            if(isAllFieldChecked){
//                Intent intent=new Intent(SignUp.this,Login.class);
//                startActivity(intent);
            }
        });
    }

    private boolean CheckAllFields() {
        EditText etFirstName=findViewById(R.id.et_signup_name);
        EditText etPassword=findViewById(R.id.et_signup_password);
        EditText etConfirm=findViewById(R.id.et_signup_confirm);
        EditText etEmail=findViewById(R.id.et_signup_email);

        if (etFirstName.length() == 0) {
            etFirstName.setError("This field is required");
            return false;
        }

        if (etEmail.length() == 0) {
            etEmail.setError("Email is required");
            return false;
        }

        if (etPassword.length() == 0) {
            etPassword.setError("Password is required");
            return false;
        } else if (etPassword.length() < 8) {
            etPassword.setError("Password must be minimum 8 characters");
            return false;
        }

        if(!(etConfirm.getText().toString().equals(etPassword.getText().toString())))
        {
            etConfirm.setError("Doesn't match Password");
            return false;
        }

        // after all validation return true.
        return true;
    }

}