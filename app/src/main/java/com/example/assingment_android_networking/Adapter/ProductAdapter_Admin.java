package com.example.assingment_android_networking.Adapter;


import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.assingment_android_networking.Fra.Abate;
import com.example.assingment_android_networking.Model.DeleteResponse;
import com.example.assingment_android_networking.Model.Product;
import com.example.assingment_android_networking.R;
import com.example.assingment_android_networking.Retrofit.Retrofit_Api;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductAdapter_Admin extends RecyclerView.Adapter<ProductAdapter_Admin.viewHolder_1> {

    private Context context;
    private List<Product> list;
    OkHttpClient client = new OkHttpClient();

    public ProductAdapter_Admin(Context context, List<Product> products) {
        this.context = context;
        this.list = products;
    }

    @NonNull
    @Override
    public viewHolder_1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.product_item_admin,parent,false);
        return new viewHolder_1(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder_1 holder, int position) {
        Product product = list.get(position);

        String imageUrl = product.getImage();
        Glide.with(holder.imageView.getContext())
                .load(imageUrl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(holder.imageView);

        holder.txtName.setText(product.getName());
        holder.txtPrice.setText(String.valueOf(product.getPrice()));
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccountById();
            }
        });




    }


    @Override
    public int getItemCount () {
        return list.size();
    }
    public class viewHolder_1 extends RecyclerView.ViewHolder{

        ImageView imageView,imageEdit,imageDelete;
        TextView txtName;
        TextView txtPrice;

        public viewHolder_1(@NonNull View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.imageView_admin);
            imageEdit = itemView.findViewById(R.id.imageView_edit);
            imageDelete = itemView.findViewById(R.id.imageView_delete);
            txtName = itemView.findViewById(R.id.textView_name_admin);
            txtPrice = itemView.findViewById(R.id.textView2_price);


        }
    }
    private void deleteAccountById() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int id_user = sharedPreferences.getInt("id_product", 0);
        Log.e("id_user", "Error: " + id_user);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.182:4000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        Retrofit_Api retrofit_api = retrofit.create(Retrofit_Api.class);

        Call<DeleteResponse> call = retrofit_api.deleteProductById(id_user);

        call.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().toString();
                        JsonElement jsonElement = JsonParser.parseString(jsonString);
                        // Xử lý dữ liệu JSON ở đây
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Xử lý lỗi khi không thể parse JSON
                    }
                    Toast.makeText(context, "delete thah cong ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                // Xử lý lỗi khi yêu cầu API thất bại
                Toast.makeText(context, "Failed to delete account: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error: " + t.getMessage());
            }
        });
    }
}
