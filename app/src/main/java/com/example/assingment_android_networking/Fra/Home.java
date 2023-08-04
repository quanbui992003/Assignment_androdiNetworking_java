package com.example.assingment_android_networking.Fra;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assingment_android_networking.Adapter.CategoryAdapter;
import com.example.assingment_android_networking.Adapter.ProductAdapter;
import com.example.assingment_android_networking.Model.Category;
import com.example.assingment_android_networking.Model.CategoryAge;
import com.example.assingment_android_networking.Model.Product;
import com.example.assingment_android_networking.Model.ProductAge;
import com.example.assingment_android_networking.Interface.id_cate;
import com.example.assingment_android_networking.R;
import com.example.assingment_android_networking.Retrofit.Retrofit_Api;
import com.example.assingment_android_networking.activity.Home_Admin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Home extends Fragment {

    OkHttpClient client = new OkHttpClient();
    ;
    RecyclerView recyclerView;

    RecyclerView ReCyCATE;
    ProductAdapter productAdapter;

    CategoryAdapter categoryAdapter;
    List<Product> productList = new ArrayList<>();
    List<Category> categoryList = new ArrayList<>();

    private id_cate categoryDataListener;

    Home_Admin home_admin;

    FloatingActionButton floatingActionButton;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Home_Admin) {
            categoryDataListener = (Home_Admin) context;
        } else {
//          throw new ClassCastException(context.toString() + " must implement id_cate");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.Recyclerview);
        floatingActionButton = view.findViewById(R.id.FloatingActionButton);
        ReCyCATE = view.findViewById(R.id.reCy_Cate);
        ReCyCATE.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ShowCate();
        ShowSp();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }


        });
        return view;
    }


    private void ShowSp() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.182:4000")
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
                    Log.e("adapter_Product", "onBindViewHolder: " + productList);
                    productAdapter = new ProductAdapter(getContext(), productList);
                    recyclerView.setAdapter(productAdapter);

                }
                //   Toast.makeText(getContext(),"thanh cong ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ProductAge> call, Throwable t) {
                // Toast.makeText(getContext(),t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });


    }

    private void ShowCate() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.182:4000")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retrofit_Api retrofit_api = retrofit.create(Retrofit_Api.class);

        Call<CategoryAge> call = retrofit_api.getAllCategory();

        call.enqueue(new Callback<CategoryAge>() {
            @Override
            public void onResponse(Call<CategoryAge> call, Response<CategoryAge> response) {
                if (response.isSuccessful()) {
                    categoryList = response.body().getCategory();
                    Log.e("adapter", "onBindViewHolder: " + categoryList);
                    categoryAdapter = new CategoryAdapter(getContext(), categoryList);
                    ReCyCATE.setAdapter(categoryAdapter);

//                    Intent intent = new Intent(getContext(), Home_Admin.class);
//                    intent.putParcelableArrayListExtra("categories", new ArrayList<>(categoryList));
//                    startActivity(intent);
                    if (categoryDataListener != null) {
                        categoryDataListener.onCategoryDataReceived(categoryList);

                    }
//                    Home_fra homeFra = Home_fra.newInstance(categoryList);
//                    getChildFragmentManager().beginTransaction()  // Sử dụng getChildFragmentManager() thay vì getSupportFragmentManager()
//                            .replace(R.id.main2, homeFra)
//                            .commit();

                }
                Toast.makeText(getContext(), "thanh cong Category", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<CategoryAge> call, Throwable t) {
                Toast.makeText(getContext(), "Category Fail" + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });


    }

}