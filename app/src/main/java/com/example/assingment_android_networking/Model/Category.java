package com.example.assingment_android_networking.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Category implements Parcelable {

    private int id_category;
    private String name;
    private String image;

    public Category(int id_category, String name, String image) {
        this.id_category = id_category;
        this.name = name;
        this.image = image;
    }

    public Category() {
    }

    protected Category(Parcel in) {
        id_category = in.readInt();
        name = in.readString();
        image = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id_category=" + id_category +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id_category);
        dest.writeString(name);
        dest.writeString(image);
    }
}
