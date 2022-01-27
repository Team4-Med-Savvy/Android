package com.example.medsavvy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.medsavvy.retrofit.model.UserDto;
import com.example.medsavvy.retrofit.network.IPostUserApi;
import com.example.medsavvy.retrofit.networkmanager.UserRetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUp extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        EditText fn = findViewById(R.id.et_signup_name);
        EditText em = findViewById(R.id.et_signup_email);
        EditText pwd = findViewById(R.id.et_signup_password);
        EditText cpwd = findViewById(R.id.et_signup_confirm);

        findViewById(R.id.bn_signup_submit).setOnClickListener(v -> {
            boolean isAllFieldChecked=CheckAllFields();

            if(isAllFieldChecked){
                SharedPreferences sharedPreferences = getSharedPreferences("com.example.medsavvy", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("pass", pwd.getText().toString());
                editor.putString("confirm", cpwd.getText().toString());
                editor.putString("name", fn.getText().toString());
                editor.putString("em", em.getText().toString());
                editor.apply();
                initApi(createRequest());
                Intent k = new Intent(SignUp.this, Login.class);
                startActivity(k);
                finish();
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
   public UserDto createRequest(){
        UserDto userDto=new UserDto();
       EditText etFirstName=findViewById(R.id.et_signup_name);
       EditText etPassword=findViewById(R.id.et_signup_password);
       EditText etEmail=findViewById(R.id.et_signup_email);
        userDto.setName(etFirstName.getText().toString());
        userDto.setPassword(etPassword.getText().toString());
        userDto.setPoints(new Long(0));
        userDto.setUsername(etEmail.getText().toString());
        userDto.setEmail(etEmail.getText().toString());
        return userDto;
   }
    private void initApi(UserDto userDto){
        Retrofit retrofit= UserRetrofitBuilder.getInstance();
        IPostUserApi iPostUserApi=retrofit.create(IPostUserApi.class);
        Call<Void> response=iPostUserApi.save(userDto);
        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(SignUp.this,"Success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SignUp.this,"Failure",Toast.LENGTH_SHORT).show();

            }
        });

    }

}