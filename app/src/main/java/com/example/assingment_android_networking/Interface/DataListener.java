package com.example.assingment_android_networking.Interface;

import com.example.assingment_android_networking.Model.Category;

import java.util.List;

public interface DataListener {
    void onDataReceived(List<Category> data);
}
