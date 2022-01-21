package com.example.medsavvy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        findViewById(R.id.iv_home_login).setOnClickListener(v -> {
            Intent i=new Intent(HomePage.this,Login.class);
            startActivity(i);
        });
    }
}