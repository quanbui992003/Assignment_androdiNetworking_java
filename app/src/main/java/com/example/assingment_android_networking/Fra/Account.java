package com.example.assingment_android_networking.Fra;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assingment_android_networking.Model.DeleteResponse;
import com.example.assingment_android_networking.Model.UpdateResponse;
import com.example.assingment_android_networking.R;
import com.example.assingment_android_networking.Retrofit.Retrofit_Api;
import com.example.assingment_android_networking.activity.Login;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Account extends Fragment {


    TextView textView;
    com.example.assingment_android_networking.Model.Account account1 = new com.example.assingment_android_networking.Model.Account();

    OkHttpClient client = new OkHttpClient();
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");



        Log.e("aaa","aaa"+email);
        // Hiển thị dữ liệu email lên TextView trong Fragment
        TextView textViewEmail = view.findViewById(R.id.txt_email_account);
      Button btnDelete = view.findViewById(R.id.delete_account);
        textViewEmail.setText(email);

        textViewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccountById();
            }


        });

        return view;
    }
    @SuppressLint("MissingInflatedId")
    private void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View view = inflater.inflate(R.layout.layout_update_product, null);
        builder.setView(view);

        EditText editTextName = view.findViewById(R.id.edt_update_userName);
        EditText editTextPhone = view.findViewById(R.id.update_userPhone);
        Button btnUpdate = view.findViewById(R.id.btn_Update);

        editTextName.setText(account1.getName());
        editTextPhone.setText(account1.getPhone());

        AlertDialog dialog = builder.create();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editTextName.getText().toString().trim();
                String newPhone = editTextPhone.getText().toString().trim();

                if (!newName.isEmpty() || !newPhone.isEmpty()) {
                    // Call the method to update user info using Retrofit
                    updateUserInfoWithRetrofit(newName, newPhone);
                    dialog.dismiss();
                } else {
                    // Show an error message if both fields are empty
                    Toast.makeText(getContext(), "Name and Phone cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
//
//
    private void updateUserInfoWithRetrofit(String newName, String newPhone) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        int id_user = sharedPreferences.getInt("id_user", 0);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.182:4000") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retrofit_Api retrofit_api = retrofit.create(Retrofit_Api.class);

        Call<UpdateResponse> call = retrofit_api.updateUserInfoById(id_user, newName, newPhone);

        call.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UpdateResponse updateResponse = response.body();
                    if (updateResponse.isSuccess()) {
                        // Update the displayed name and phone in the TextViews
//                        textViewName.setText(newName);
//                        textViewPhone.setText(newPhone);

                        Toast.makeText(getActivity(), "Update successful", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle update failure
                        Toast.makeText(getActivity(), "Update failed: " + updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Error-11", "Error: " + updateResponse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                // Handle failure
                Toast.makeText(getActivity(), "Failed to update data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error: " + t.getMessage());
            }
        });

    }
    private void deleteAccountById() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        int id_user = sharedPreferences.getInt("id_user", 0);
        Log.e("id_user", "Error: " + id_user);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.182:4000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        Retrofit_Api retrofit_api = retrofit.create(Retrofit_Api.class);

        Call<DeleteResponse> call = retrofit_api.deleteAccountById(id_user);

        call.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DeleteResponse deleteResponse = response.body();
                    if (deleteResponse.isSuccess()) {

                        Toast.makeText(getActivity(), "Account deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(getActivity(), "Failed to delete account: " + deleteResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Xử lý lỗi khi không nhận được phản hồi hợp lệ từ máy chủ
                    if (response.errorBody() != null) {
                        try {
                            String errorString = response.errorBody().string();
                            Toast.makeText(getActivity(), "Failed to delete account: " + errorString, Toast.LENGTH_SHORT).show();
                            Log.e("Error", "Error: " + errorString);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Failed to delete account: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Failed to delete account: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                // Xử lý lỗi khi yêu cầu API thất bại
                Toast.makeText(getActivity(), "Failed to delete account: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error: " + t.getMessage());
            }
        });
    }


}
