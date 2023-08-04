package com.example.assingment_android_networking.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assingment_android_networking.Model.Account;
import com.example.assingment_android_networking.Model.InsertResponse;
import com.example.assingment_android_networking.R;
import com.example.assingment_android_networking.Retrofit.Retrofit_Api;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {

    EditText email;
    EditText userName;
    EditText passWord;
    EditText userPhone;
    Button btnSingUp;
    Account account = new Account();
    OkHttpClient client = new OkHttpClient();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = findViewById(R.id.sing_up_email);
        userName = findViewById(R.id.sing_up_userName);
        passWord = findViewById(R.id.sing_up_password);
        userPhone = findViewById(R.id.sing_up_userPhone);
        btnSingUp = findViewById(R.id.btn_SingUp);

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                account.setEmail(email.getText().toString().trim());
                account.setName(userName.getText().toString().trim());
                account.setPassword(passWord.getText().toString().trim());
                account.setPhone(userPhone.getText().toString().trim());

//                String textEmail = email.getText().toString().trim();
//                String password = passWord.getText().toString().trim();
//                String name = userName.getText().toString().trim();
//                String phone = userPhone.getText().toString().trim();

//                if (textEmail.isEmpty() || password.isEmpty() || name.isEmpty() || phone.isEmpty()) {
//                    Toast.makeText(SignUp.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
//                    return; // Exit the onClick method and prevent further execution
//                }
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.182:4000")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Retrofit_Api retrofit_api = retrofit.create(Retrofit_Api.class);


                Call<InsertResponse> call = retrofit_api.sendText(account.getEmail(), account.getPassword(), account.getName(), account.getPhone());

                call.enqueue(new Callback<InsertResponse>() {
                    @Override
                    public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(
                                    SignUp.this,
                                    "Data sent successfully",
                                    Toast.LENGTH_SHORT
                            ).show();
                            startActivity(new Intent(SignUp.this, Login.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<InsertResponse> call, Throwable t) {
                        Toast.makeText(
                                SignUp.this,
                                "Failed to send data: " + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });

            }
        });

    }
}