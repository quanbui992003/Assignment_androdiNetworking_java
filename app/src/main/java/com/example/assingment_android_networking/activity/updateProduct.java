package com.example.assingment_android_networking.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.assingment_android_networking.Interface.id_cate;
import com.example.assingment_android_networking.Model.Category;
import com.example.assingment_android_networking.Model.Product;
import com.example.assingment_android_networking.Model.UpdateResponse;
import com.example.assingment_android_networking.R;
import com.example.assingment_android_networking.Retrofit.Retrofit_Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class updateProduct extends AppCompatActivity  {

    List<Category> selectedCategory = new ArrayList<>();
    EditText txtName,txtPrice,txtDrc,txtImage,txtQuantity;
    Button button;
    Spinner spinner;
    Product product = new Product();

    int categoryId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_product);

        txtName = findViewById(R.id.update_name);
        txtPrice = findViewById(R.id.update_admin_price);
        txtDrc = findViewById(R.id.update_admin_drc);
        txtImage = findViewById(R.id.update_admin_image);
        txtQuantity = findViewById(R.id.update_admin_quantity);
        button = findViewById(R.id.btn_Update_admin);



        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        String price = extras.getString("price");
        String desc = extras.getString("desc");
        String image = extras.getString("image");
        int quantity = extras.getInt("quantity");

        // Đặt dữ liệu vào các trường EditText tương ứng
        txtName.setText(name);
        txtPrice.setText(price);
        txtDrc.setText(desc);
        txtImage.setText(image);
        txtQuantity.setText(String.valueOf(quantity));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newName = txtName.getText().toString();
                String newPrice = txtPrice.getText().toString();
                String newDrc = txtDrc.getText().toString();
                String newImage = txtImage.getText().toString();
                String newQuantity = txtQuantity.getText().toString();


                updateUserInfoWithRetrofit(newName,newPrice,newDrc,newImage,newQuantity);


            }
        });

    }

    private void updateUserInfoWithRetrofit(String newName, String newPrice, String newDrc, String newImage, String newQuantity ) {

        txtName.setText(product.getName());
        txtPrice.setText(String.valueOf(product.getPrice()));
        txtDrc.setText(product.getDescription());
        txtImage.setText(product.getImage());
        txtQuantity.setText(String.valueOf(product.getQuantity()));

        SharedPreferences sharedPreferences = updateProduct.this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int id_user = sharedPreferences.getInt("id_product", 0);
        Log.e("id_user", "Error: " + id_user);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.24.28.57:4000") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retrofit_Api retrofit_api = retrofit.create(Retrofit_Api.class);

        Call<UpdateResponse> call = retrofit_api.updateProduct(id_user, newName, newPrice,newDrc,newImage,newQuantity);

        call.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UpdateResponse updateResponse = response.body();
                    if (updateResponse.isSuccess()) {
                        Toast.makeText(updateProduct.this, "Update successful", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // Handle update failure
                        Toast.makeText(updateProduct.this, "Update failed: " + updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Error-11", "Error: " + updateResponse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                // Handle failure
                Toast.makeText(updateProduct.this, "Failed to update data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error: " + t.getMessage());
            }
        });

    }



}