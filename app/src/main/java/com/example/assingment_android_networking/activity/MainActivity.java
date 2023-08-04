package com.example.assingment_android_networking.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.assingment_android_networking.Fra.Account;
import com.example.assingment_android_networking.Fra.Home;
import com.example.assingment_android_networking.Fra.Shopping_Cart;
import com.example.assingment_android_networking.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    Home home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.main_container);
        bottomNavigationView = findViewById(R.id.bottom);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new Home())
                    .commit();
        }
        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                int id = item.getItemId();
                try {
                    if (id == R.id.Home) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_container, new Home())
                                .commit();

                    } else if (id == R.id.shopping_cart) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_container, new Shopping_Cart())
                                .commit();
                        ;
                    } else if (id == R.id.person) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_container, new Account())
                                .commit();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Xử lý ngoại lệ nếu có
                }


            }
        });


    }
}