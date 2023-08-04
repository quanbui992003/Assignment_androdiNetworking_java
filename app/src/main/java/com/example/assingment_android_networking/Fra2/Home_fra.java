package com.example.assingment_android_networking.Fra2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.assingment_android_networking.Interface.DataListener;
import com.example.assingment_android_networking.Interface.id_cate;
import com.example.assingment_android_networking.Model.Category;
import com.example.assingment_android_networking.Model.InsertProduct;
import com.example.assingment_android_networking.Model.Product;
import com.example.assingment_android_networking.R;
import com.example.assingment_android_networking.Retrofit.Retrofit_Api;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Home_fra extends Fragment   {


    RecyclerView recyclerView;
    FloatingActionButton actionButton;
    List<Product> list = new ArrayList<>();
    Product product = new Product();
    OkHttpClient client = new OkHttpClient();
    int categoryId;
    List<Category> selectedCategory = new ArrayList<>();


    BottomNavigationView bottomNavigationView;

    private static List<Category> categoryListToPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_fra, container, false);

        actionButton = view.findViewById(R.id.Float_add_product);

        Bundle bundle = getArguments();
        if (bundle != null) {
            selectedCategory = (List<Category>) bundle.getSerializable("selectedCategory");
         Log.e("qqqq","data"+selectedCategory);
        }
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
            }
        });




        return view;
    }
    @SuppressLint("MissingInflatedId")
    private void Dialog() {



        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
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

            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categoryNameList);
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
                        .baseUrl("http://192.168.1.182:4000")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Retrofit_Api retrofit_api = retrofit.create(Retrofit_Api.class);


                Call<InsertProduct> call = retrofit_api.sendProduct(product.getName(), product.getPrice(), product.getDescription(), product.getImage(), product.getQuantity(), categoryId);

                call.enqueue(new Callback<InsertProduct>() {
                    @Override
                    public void onResponse(Call<InsertProduct> call, Response<InsertProduct> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(
                                    getContext(),
                                    "Data sent successfully",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<InsertProduct> call, Throwable t) {
                        Toast.makeText(
                                getContext(),
                                "Failed to send data: " + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });

            }


        });

        dialog.show();
    }








}