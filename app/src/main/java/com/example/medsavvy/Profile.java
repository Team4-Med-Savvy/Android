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
import com.example.medsavvy.retrofit.model.OrdersDto;
import com.example.medsavvy.retrofit.network.IPostOrderApi;
import com.example.medsavvy.retrofit.networkmanager.OrderRetrofitBuilder;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Profile extends AppCompatActivity implements OrderAdapter.IApiResponseClick{
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;
    TextView Name,Email,Points,Membership;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
       // displayLocalRecyclerView();
        initorder();
        Name=findViewById(R.id.tv_profile_name);
        Email=findViewById(R.id.tv_profile_email);
        Points=findViewById(R.id.tv_profile_points);
        Membership=findViewById(R.id.tv_profile_member);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.medsavvy", Context.MODE_PRIVATE);
        Name.setText(sharedPreferences.getString("name","customer"));
        Email.setText(sharedPreferences.getString("em","email"));
        Points.setText(sharedPreferences.getString("points","0"));
        Membership.setText(sharedPreferences.getString("userId","default"));

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
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
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


    private void initorder() {
        Retrofit retrofit = OrderRetrofitBuilder.getInstance();
        IPostOrderApi iPostOrderApi = retrofit.create(IPostOrderApi.class);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.medsavvy", Context.MODE_PRIVATE);

        Call<List<OrdersDto>>  orders=iPostOrderApi.findUserOrders(sharedPreferences.getString("userId","0"));
       orders.enqueue(new Callback<List<OrdersDto>>() {
           @Override
           public void onResponse(Call<List<OrdersDto>> call, Response<List<OrdersDto>> response) {
               List<ApiOrder> apiOrders=new ArrayList<>();
               List<OrdersDto> ordersDtos=response.body();
               for(int i=0;i<ordersDtos.size();i++)
               {
                   ApiOrder apiOrder=new ApiOrder();
                   apiOrder.setOrderid(ordersDtos.get(i).getId());
                   apiOrder.setDate(ordersDtos.get(i).getTimeStamp());
                   apiOrder.setAmount(Double.parseDouble(ordersDtos.get(i).getTotal()+""));

                   apiOrders.add(apiOrder);
               }
               RecyclerView recyclerView=findViewById(R.id.recycleList);
               OrderAdapter recycleViewAdapter=new OrderAdapter(apiOrders,Profile.this);
               LinearLayoutManager HorizontalLayout= new LinearLayoutManager(Profile.this,LinearLayoutManager.HORIZONTAL,false);

               recyclerView.setLayoutManager(HorizontalLayout);
               recyclerView.setAdapter(recycleViewAdapter);



           }

           @Override
           public void onFailure(Call<List<OrdersDto>> call, Throwable t) {

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
//        userDataList.add(new ApiOrder("Order 1", 10.0 ,"12/12/1999"));
//        userDataList.add(new ApiOrder("Order 2", 10.0 ,"12/12/1999"));
//        userDataList.add(new ApiOrder("Order 3", 10.0 ,"12/12/1999"));
//        userDataList.add(new ApiOrder("Order 4", 10.0 ,"12/12/1999"));
//        userDataList.add(new ApiOrder("Order 5", 10.0 ,"12/12/1999"));

    }

    @Override
    public void onUserClick(ApiOrder userDatamodel) {
        Intent intent=new Intent(Profile.this,OrderHistory.class);
        intent.putExtra("orderid",userDatamodel.getOrderid());
        startActivity(intent);
    }
}