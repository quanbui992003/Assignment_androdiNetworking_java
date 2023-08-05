package com.example.assingment_android_networking.activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.assingment_android_networking.Adapter.ProductAdapter;
import com.example.assingment_android_networking.Adapter.ProductAdapter_Admin;
import com.example.assingment_android_networking.Fra.Home;

import com.example.assingment_android_networking.Model.Category;

import com.example.assingment_android_networking.Model.InsertProduct;
import com.example.assingment_android_networking.Model.Product;
import com.example.assingment_android_networking.Interface.id_cate;
import com.example.assingment_android_networking.Model.ProductAge;
import com.example.assingment_android_networking.R;
import com.example.assingment_android_networking.Retrofit.Retrofit_Api;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home_Admin extends AppCompatActivity implements id_cate {

    RecyclerView recyclerView_admin;
    FloatingActionButton actionButton;
    List<Product> list = new ArrayList<>();
    Product product = new Product();
    OkHttpClient client = new OkHttpClient();
    int categoryId;

    List<Category> selectedCategory = new ArrayList<>();

    List<Product> productList = new ArrayList<>();
    ProductAdapter productAdapter;

    ProductAdapter_Admin productAdapter_admin;
    int productId;
    private static final String WS_URL = "ws://192.168.1.182:4000/";


    private WebSocket webSocket;

    public static WebSocketListener webSocketListener = new WebSocketListener() {
        @Override
        public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
            super.onClosed(webSocket, code, reason);
        }

        @Override
        public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
            super.onClosing(webSocket, code, reason);
        }

        @Override
        public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable okhttp3.Response response) {
            super.onFailure(webSocket, t, response);
        }

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            super.onMessage(webSocket, text);
        }

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
            super.onMessage(webSocket, bytes);
        }

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull okhttp3.Response response) {
            super.onOpen(webSocket, response);
        }
    };
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        actionButton = findViewById(R.id.Float_add_product);
        recyclerView_admin = findViewById(R.id.RecyclerView_home_admin);
        recyclerView_admin.setLayoutManager(new LinearLayoutManager(getApplication()));
        productAdapter_admin = new ProductAdapter_Admin(this, productList);
        recyclerView_admin.setAdapter(productAdapter_admin);
        productAdapter_admin.notifyDataSetChanged();
        ShowSp();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(0, new Home())
                    .commit();
        }
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
            }

        });

        startWebSocket();

    }

    private void startWebSocket() {
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(WS_URL)
                .build();

        webSocket = client.newWebSocket(request, webSocketListener);
    }
    private void sendMessageViaWebSocket(String message) {
        if (webSocket != null) {
            webSocket.send(message);
        }
    }

    private void closeWebSocket() {
        if (webSocket != null) {
            webSocket.close(1000, "Optional closing reason");
        }
    }

    @SuppressLint("MissingInflatedId")
    private void Dialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.add_product, null);
        builder.setView(view);

        EditText editTextName = view.findViewById(R.id.add_name);
        EditText editPrice = view.findViewById(R.id.add_price);
        EditText editDrc = view.findViewById(R.id.add_description);
        EditText editImage = view.findViewById(R.id.add_image);
        EditText editQuantity = view.findViewById(R.id.add_quantity);

        Spinner spinner = view.findViewById(R.id.Spin_cate);
        Button btnAdd = view.findViewById(R.id.btn_Add);

        editTextName.setText(product.getName());
        editPrice.setText(String.valueOf(product.getPrice()));
        editDrc.setText(product.getDescription());
        editImage.setText(product.getImage());
        editQuantity.setText(String.valueOf(product.getQuantity()));


        if (selectedCategory != null) {
            ArrayList<String> categoryNameList = new ArrayList<>();
            for (Category category : selectedCategory) {
                categoryNameList.add(category.getName());

            }

            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNameList);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(categoryAdapter);

        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                categoryId = selectedCategory.get(position).getId_category();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AlertDialog dialog = builder.create();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                product.setName(editTextName.getText().toString().trim());
                product.setPrice(Float.parseFloat(editPrice.getText().toString().trim()));
                product.setDescription(editDrc.getText().toString().trim());
                product.setImage(editImage.getText().toString().trim());
                product.setQuantity(Integer.parseInt(editQuantity.getText().toString().trim()));


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.24.28.57:4000")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


                Retrofit_Api retrofit_api= retrofit.create(Retrofit_Api.class);



                Call<InsertProduct> call =retrofit_api.sendProduct(product.getName(),product.getPrice(),product.getDescription(),product.getImage(),product.getQuantity(),categoryId);

                call.enqueue(new Callback<InsertProduct>() {
                    @Override
                    public void onResponse(Call<InsertProduct> call, Response<InsertProduct> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(
                                    Home_Admin.this,
                                    "Data sent successfully",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<InsertProduct> call, Throwable t) {
                        Toast.makeText(
                                Home_Admin.this,
                                "Failed to send data: " + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
                dialog.dismiss();
                }



        });

        dialog.show();
    }

    private void ShowSp() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.24.28.57:4000")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retrofit_Api retrofit_api = retrofit.create(Retrofit_Api.class);

        Call<ProductAge> call = retrofit_api.getAllProduct();

        call.enqueue(new Callback<ProductAge>() {
            @Override
            public void onResponse(Call<ProductAge> call, Response<ProductAge> response) {

                if (response.isSuccessful()) {
                    productList = response.body().getProducts();
                    ProductAge productResponse = response.body();
                    Log.e("adapter_Product", "onBindViewHolder: " + productList);
                    productAdapter_admin = new ProductAdapter_Admin(getApplicationContext(), productList);
                    recyclerView_admin.setAdapter(productAdapter_admin);
                    productAdapter_admin.notifyDataSetChanged();


                    for (Product product1 : productList){
                        productId = product1.getId_product();
                        Log.e("product", "product :" + productId);
                        saveEmailToSharedPreferences(productId);
                    }

                    String message = "New product added: " + product.getName();
                    sendMessageViaWebSocket(message);



                }


                //   Toast.makeText(getContext(),"thanh cong ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ProductAge> call, Throwable t) {
                // Toast.makeText(getContext(),t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });


    }



    @Override
    public void onCategoryDataReceived(List<Category> categoryList) {
        selectedCategory = categoryList;

//        for (Category category : categoryList) {
//            Log.d("Home_Admin", "Tên danh mục: " + category.getName() + ", ID danh mục: " + category.getId_category());
//        }
    }
    private void saveEmailToSharedPreferences(int userProduct) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id_product", userProduct);
        editor.apply();
    }


}