package com.example.medsavvy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medsavvy.retrofit.model.AuthDto;
import com.example.medsavvy.retrofit.model.ResponseDto;
import com.example.medsavvy.retrofit.model.UserDto;
import com.example.medsavvy.retrofit.network.IPostUserApi;
import com.example.medsavvy.retrofit.networkmanager.UserRetrofitBuilder;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;
    private static int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.bn_login_signup).setOnClickListener(v -> {
            Intent intent=new Intent(Login.this,SignUp.class);
            startActivity(intent);
            finish();

        });
        findViewById(R.id.bn_login).setOnClickListener(view -> {

                    initApi(generateRequest());
                  //  Intent i;
//                    if (flag == 1) {

//                    } else {
                        //Toast.makeText(this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
//                    }


        });

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        Button button = findViewById(R.id.sign_in_button);
        //button.setSize(SignInButton.SIZE_STANDARD);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        button.setOnClickListener(v -> {
            signIn();
        });

    }
//    @Override
//    public void onClick(View v) {
//         signIn();
//        }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {

                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                Toast.makeText(this , "User Email : " + personEmail , Toast.LENGTH_SHORT).show();
            }
            initApi(generateRequest());
            startActivity(new Intent(this , HomePage.class));
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("Message", e.toString());

        }
    }
    public  AuthDto generateRequest()
    {

        EditText etemail= findViewById(R.id.login_email);
        EditText etpass = findViewById(R.id.login_password);

        AuthDto authDto=new AuthDto();
        authDto.setUsername(etemail.getText().toString());
        authDto.setPassword(etpass.getText().toString());

        return authDto;

    }
    private void initApi(AuthDto authDto)
    {
        Retrofit retrofit= UserRetrofitBuilder.getInstance();
        IPostUserApi iPostUserApi=retrofit.create(IPostUserApi.class);
        Call<ResponseDto> response=iPostUserApi.generateToken(authDto);

        response.enqueue(new Callback<ResponseDto>() {
            @Override
            public void onResponse(Call<ResponseDto> call, Response<ResponseDto> response) {
                Toast.makeText(Login.this,"Success",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Login.this, HomePage.class);
                startActivity(i);
                System.out.println(response);
                flag=1;

            }

            @Override
            public void onFailure(Call<ResponseDto> call, Throwable t) {
                Toast.makeText(Login.this,"Failure",Toast.LENGTH_SHORT).show();

            }
        });
    }


}