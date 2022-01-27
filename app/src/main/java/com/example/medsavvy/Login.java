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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class Login extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;
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

        EditText li = findViewById(R.id.login_email);
        EditText pw = findViewById(R.id.login_password);

        SharedPreferences sharedpreferences = getSharedPreferences("com.example.medsavvy", Context.MODE_PRIVATE);
        findViewById(R.id.bn_login).setOnClickListener(view -> {
            String s1=pw.getText().toString();
            String s2=sharedpreferences.getString(pw.getText().toString(),"default");
            if(s1.equals(s2)) {
                Intent i = new Intent(Login.this, HomePage.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(this,"Incorrect Credentials",Toast.LENGTH_SHORT).show();
            }

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
            startActivity(new Intent(this , HomePage.class));
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("Message", e.toString());

        }
    }
}