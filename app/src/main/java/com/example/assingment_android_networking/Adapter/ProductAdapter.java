package com.example.assingment_android_networking.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.assingment_android_networking.Fra.Abate;
import com.example.assingment_android_networking.Model.Product;
import com.example.assingment_android_networking.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.viewHolder_1> {

 private Context context;
 private List<Product> list;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.list = products;
    }

    @NonNull
    @Override
    public viewHolder_1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
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

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Abate abate = new Abate();
                bundle.putString("image", product.getImage());
                bundle.putString("name", product.getName());
                bundle.putString("price", String.valueOf(product.getPrice()));
                abate.setArguments(bundle);
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, abate)
                        .addToBackStack(null)
                        .commit();

            }
        });


    }


    @Override
    public int getItemCount () {
        return list.size();
    }
    public class viewHolder_1 extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView txtName;
        TextView txtPrice;
        LinearLayout linearLayout;
        public viewHolder_1(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.Liner_Layout);
            imageView = itemView.findViewById(R.id.sp_image);
            txtName = itemView.findViewById(R.id.sp_Name);
            txtPrice = itemView.findViewById(R.id.sp_price);

        }
    }
}
