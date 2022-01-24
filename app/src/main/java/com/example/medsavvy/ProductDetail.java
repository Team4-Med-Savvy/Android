package com.example.medsavvy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.medsavvy.RecycleView.model.ApiProduct;

public class ProductDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ImageView imageurl=findViewById(R.id.iv_prod_detail);
        Intent intent = getIntent();
        String url = intent.getExtras().getString("imageUrl");
        Glide.with(imageurl.getContext()).load(url).placeholder(R.drawable.ic_login).into(imageurl);

        findViewById(R.id.iv_home_login).setOnClickListener(v -> {
            Intent i=new Intent(ProductDetail.this,Login.class);
            startActivity(i);
        });

    }
}