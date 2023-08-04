package com.example.assingment_android_networking.Adapter;


import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.assingment_android_networking.Model.DeleteResponse;
import com.example.assingment_android_networking.Model.Product;
import com.example.assingment_android_networking.Model.ProductAge;
import com.example.assingment_android_networking.Model.UpdateResponse;
import com.example.assingment_android_networking.R;
import com.example.assingment_android_networking.Retrofit.Retrofit_Api;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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
;
    OkHttpClient client = new OkHttpClient();

    public ProductAdapter_Admin(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
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
        holder.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
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
    @SuppressLint("MissingInflatedId")
    public void Dialog() {
//        Product product= new Product();
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_update_product, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

//        EditText editTextName = view.findViewById(R.id.update_admin_Name);
//        EditText editTextPrice = view.findViewById(R.id.update_admin_price);
//        EditText editTextDrc = view.findViewById(R.id.update_admin_drc);
//        EditText editTextImage = view.findViewById(R.id.update_admin_image);
//        EditText editTextQuantity = view.findViewById(R.id.update_admin_quantity);
//
//        Button btnUpdate = view.findViewById(R.id.btn_Update_admin);
//
//        editTextName.setText(product.getName());
//        editTextPrice.setText((int) product.getPrice());
//        editTextDrc.setText(product.getDescription());
//        editTextImage.setText(product.getImage());
//        editTextQuantity.setText(product.getQuantity());
//

//
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String newName = editTextName.getText().toString().trim();
//                String newPrice = editTextPrice.getText().toString().trim();
//                String newDrc = editTextDrc.getText().toString().trim();
//                String newImage = editTextImage.getText().toString().trim();
//                String newQuantity = editTextQuantity.getText().toString().trim();
//
//
//                if (!newName.isEmpty() || !newPrice.isEmpty() || !newDrc.isEmpty() || !newImage.isEmpty() || !newQuantity.isEmpty()) {
//                    // Call the method to update user info using Retrofit
//                    updateUserInfoWithRetrofit(newName, newPrice,newDrc,newImage,newQuantity);
//                    dialog.dismiss();
//                } else {
//                    // Show an error message if both fields are empty
//                    Toast.makeText(context, "Name and Phone cannot be empty", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


    }
    private void updateUserInfoWithRetrofit(String newName, String newPrice, String newDrc, String newImage, String newQuantity ) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);

        int id_user = sharedPreferences.getInt("id_product", 0);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.182:4000") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retrofit_Api retrofit_api = retrofit.create(Retrofit_Api.class);

        Call<UpdateResponse> call = retrofit_api.updateProduct(id_user, newName,newPrice,newDrc,newImage,newQuantity );

        call.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UpdateResponse updateResponse = response.body();
                    if (updateResponse.isSuccess()) {

                        Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle update failure
                        Toast.makeText(context, "Update failed: " + updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Error-11", "Error: " + updateResponse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                // Handle failure
                Toast.makeText(context, "Failed to update data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error: " + t.getMessage());
            }
        });

    }

}
