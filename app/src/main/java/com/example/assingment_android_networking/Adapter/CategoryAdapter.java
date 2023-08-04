package com.example.assingment_android_networking.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.assingment_android_networking.Model.Category;
import com.example.assingment_android_networking.Model.Product;
import com.example.assingment_android_networking.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder_2>{
   private Context context;

    private List<Category> list;


    public CategoryAdapter(Context context, List<Category> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CategoryAdapter.viewHolder_2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);
        return new CategoryAdapter.viewHolder_2(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewHolder_2 holder, int position) {

        Category category = list.get(position);


        String imageUrl = category.getImage();
        Glide.with(holder.imageView.getContext())
                .load(imageUrl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(holder.imageView);

        holder.txtName.setText(category.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class viewHolder_2 extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView txtName;
        LinearLayout linearLayout;
        public viewHolder_2(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linear_cate);
            imageView = itemView.findViewById(R.id.myImageView_cate);
            txtName = itemView.findViewById(R.id.txt_cate);
          //  txtPrice = itemView.findViewById(R.id.sp_price);

        }
    }
}
