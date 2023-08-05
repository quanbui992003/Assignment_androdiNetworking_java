package com.example.assingment_android_networking.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.assingment_android_networking.Model.Account;
import com.example.assingment_android_networking.Model.LoginResponse;
import com.example.assingment_android_networking.R;
import com.example.assingment_android_networking.Retrofit.Retrofit_Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    EditText edtEmail;
    EditText edtPassword;
    Button btnLogin;
    TextView txtSingUp;

    Account account = new Account();

    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtEmail = findViewById(R.id.editEmail);
        edtPassword = findViewById(R.id.editTextPass);
        btnLogin = findViewById(R.id.btn_Login);

        txtSingUp = findViewById(R.id.txt_singUp);

        txtSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                account.setEmail(edtEmail.getText().toString());
                account.setPassword(edtPassword.getText().toString());
                String textEmail = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();


                if (textEmail.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.24.28.57:4000")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                        .build();

                Retrofit_Api retrofit_api = retrofit.create(Retrofit_Api.class);


                Call<LoginResponse> call = retrofit_api.login(account.getEmail(), account.getPassword());


                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            LoginResponse loginResponse = response.body();
                            Log.d("Debug", "Response body: " + new Gson().toJson(loginResponse)); // Log the entire response body
                            int userId = loginResponse.getId_user();
                            Log.e("llll", "aaa" + userId);
                            saveEmailToSharedPreferences(textEmail, userId);
                            String message = response.body().getMessage();

                            if ("Admin login successful".equals(message)) {
                                // Chuyển hướng người dùng đến màn hình quản trị viên (ví dụ: Activity Admin)
                                startActivity(new Intent(Login.this, Home_Admin.class));
                            } else if ("User login successful".equals(message)) {
                                // Chuyển hướng người dùng đến màn hình người dùng thường (ví dụ: Activity User)
                                startActivity(new Intent(Login.this, MainActivity.class));
                            }
                        }
                        // Lấy ID từ phản hồi
//                        String userId = String.valueOf(response.body().getUserId());
//                        Log.e("llll", "aaa" + userId);
                        ;
                        Toast.makeText(
                                Login.this,
                                "Data sent successfully",
                                Toast.LENGTH_SHORT
                        ).show();

                    }


                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(
                                Login.this,
                                "Failed to send data: " + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                        Log.e("aa", "aaa" + t.getMessage());
                    }
                });
            }
        });
    }

    private void saveEmailToSharedPreferences(String textEmail, int userId) {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", textEmail);
        editor.putInt("id_user", userId);
        editor.apply();


    }
}