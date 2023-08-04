package com.example.assingment_android_networking.Retrofit;


import com.example.assingment_android_networking.Model.CategoryAge;
import com.example.assingment_android_networking.Model.DeleteResponse;
import com.example.assingment_android_networking.Model.InsertProduct;
import com.example.assingment_android_networking.Model.InsertResponse;
import com.example.assingment_android_networking.Model.LoginResponse;
import com.example.assingment_android_networking.Model.ProductAge;
import com.example.assingment_android_networking.Model.UpdateResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Retrofit_Api {

    @GET("/product/api/")
    Call<ProductAge> getAllProduct();

    @GET("/category/getApi/")
    Call<CategoryAge> getAllCategory();

    @FormUrlEncoded
    @POST("/product/addProduct/")
    Call<InsertProduct> sendProduct(
            @Field("name") String name,
            @Field("price") Float price,
            @Field("description") String description,
            @Field("image") String image,
            @Field("quantity") int quantity,
            @Field("id_category") int id_category
    );


    @FormUrlEncoded
    @POST("/account/addAccount/")
    Call<InsertResponse> sendText(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("name") String name,
            @Field("phone") String phone
    );


    @FormUrlEncoded
    @POST("/account/Login/")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @FormUrlEncoded
    @POST("/account/Update/")
    Call<UpdateResponse> updateUserInfoById(
            @Field("id_user") int id_user, // Replace 'userId' with the correct column name
            @Field("name") String newName,
            @Field("phone") String newPhone
    );

    @DELETE("/account/Delete/{id_user}")
    Call<DeleteResponse> deleteAccountById(
            @Path("id_user") int id_user);

    @FormUrlEncoded
    @POST("/product/updateProduct/")
    Call<UpdateResponse> updateProduct(
            @Field("id_product") int id_product, // Replace 'userId' with the correct column name
            @Field("name") String newName,
            @Field("price") String price,
            @Field("description") String description,
            @Field("image") String image,
            @Field("quantity") String quantity

    );

    @DELETE("/product/deleteAdmin/{id_product}")
    Call<DeleteResponse> deleteProductById(
            @Path("id_product") int id_product);
}
