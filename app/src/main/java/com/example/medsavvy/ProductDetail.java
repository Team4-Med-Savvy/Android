package com.example.medsavvy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.medsavvy.RecycleView.model.ApiProduct;

public class ProductDetail extends AppCompatActivity {
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ImageView imageurl=findViewById(R.id.iv_prod_detail);
        TextView productname=findViewById(R.id.tv_product_name);

        Intent intent = getIntent();
        String url = intent.getExtras().getString("imageUrl");
        String name = intent.getExtras().getString("productName");
        productname.setText(name);
        Glide.with(imageurl.getContext()).load(url).placeholder(R.drawable.ic_login).into(imageurl);

        findViewById(R.id.iv_home_login).setOnClickListener(v -> {
            Intent i=new Intent(ProductDetail.this,Login.class);
            startActivity(i);
        });

        findViewById(R.id.iv_home_cart).setOnClickListener(v -> {
            Intent i=new Intent(ProductDetail.this,Cart.class);
            startActivity(i);
        });

        Button increment=findViewById(R.id.bn_increment);
        Button decrement=findViewById(R.id.bn_decrement);
        TextView display=findViewById(R.id.tv_quant);

        increment.setOnClickListener(v -> {
            count++;
            display.setText(""+count);
        });

        decrement.setOnClickListener(v -> {
                count--;
            if(count<0)count=0;
            display.setText(""+count);
        });

    }
}