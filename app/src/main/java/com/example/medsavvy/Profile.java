package com.example.medsavvy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medsavvy.RecycleView.adapter.OrderAdapter;
import com.example.medsavvy.RecycleView.adapter.RecommendAdapter;
import com.example.medsavvy.RecycleView.model.ApiOrder;
import com.example.medsavvy.RecycleView.model.ApiProduct;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity implements OrderAdapter.IApiResponseClick{
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;
    TextView Name,Email,Points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        displayLocalRecyclerView();
        Name=findViewById(R.id.tv_profile_name);
        Email=findViewById(R.id.tv_profile_email);
        Points=findViewById(R.id.tv_profile_points);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.medsavvy", Context.MODE_PRIVATE);
        Name.setText(sharedPreferences.getString("name","customer"));
        Email.setText(sharedPreferences.getString("em","email"));
        Points.setText(sharedPreferences.getString("points","0"));

        findViewById(R.id.bn_log_out).setOnClickListener(v -> {
            Intent i=new Intent(Profile.this,Login.class);
            startActivity(i);
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {

            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Name.setText(personName);
            Email.setText(personEmail);
        }
        // Set the dimensions of the sign-in button.
        Button button = findViewById(R.id.bn_log_out);
        //button.setSize(SignInButton.SIZE_STANDARD);

        button.setOnClickListener(v -> {
            signOut();

        });


    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Profile.this , "Signout",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
    }
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }


    private  void displayLocalRecyclerView(){
        List<ApiOrder> userDataList=new ArrayList<>();
        generateUserData(userDataList);
        RecyclerView recyclerView=findViewById(R.id.recycleList);
        OrderAdapter recycleViewAdapter=new OrderAdapter(userDataList,Profile.this);
        LinearLayoutManager HorizontalLayout= new LinearLayoutManager(Profile.this,LinearLayoutManager.HORIZONTAL,false);


        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setAdapter(recycleViewAdapter);


    }


    private void generateUserData(List<ApiOrder> userDataList) {
        userDataList.add(new ApiOrder("Order 1", 10.0 ,"12/12/1999"));
        userDataList.add(new ApiOrder("Order 2", 10.0 ,"12/12/1999"));
        userDataList.add(new ApiOrder("Order 3", 10.0 ,"12/12/1999"));
        userDataList.add(new ApiOrder("Order 4", 10.0 ,"12/12/1999"));
        userDataList.add(new ApiOrder("Order 5", 10.0 ,"12/12/1999"));

    }

    @Override
    public void onUserClick(ApiOrder userDatamodel) {
//        Intent intent=new Intent(Profile.this,ProductDetail.class);
//        startActivity(intent);
    }
}