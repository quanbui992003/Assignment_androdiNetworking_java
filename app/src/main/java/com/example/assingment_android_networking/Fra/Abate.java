package com.example.assingment_android_networking.Fra;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.assingment_android_networking.R;


public class Abate extends Fragment {


    TextView txtName;
    TextView txtPrice;
    ImageView imageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_abate, container, false);
       txtName = view.findViewById(R.id.name_tt);
        txtPrice = view.findViewById(R.id.price_tt);
        imageView = view.findViewById(R.id.img_tt);

       Bundle bundle = getArguments();
        String image = bundle.getString("image");
        String name = bundle.getString("name");
        String price = bundle.getString("price");

        txtName.setText(name);
        txtPrice.setText(price);
        Glide.with(this)
                .load(image)
                .into(imageView);

        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }


        });
        return view;
    }
    private void ShowDialog() {


    }
}