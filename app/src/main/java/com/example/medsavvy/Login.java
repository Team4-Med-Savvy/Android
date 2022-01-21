package com.example.medsavvy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button yourButton = (Button) findViewById(R.id.bn_login);

        findViewById(R.id.bn_login_signup).setOnClickListener(v -> {
            Intent intent=new Intent(Login.this,SignUp.class);
            startActivity(intent);
            finish();
        });
    }
}