package com.longg.apiretrofitdemo.Aapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.longg.apiretrofitdemo.Model.Categories;
import com.longg.apiretrofitdemo.R;

import java.util.List;

public class CategoriesApdapterSelect extends RecyclerView.Adapter<CategoriesApdapterSelect.CategoriesHolder>{
    private List<Categories> arrcate;
    private Context context;

    public CategoriesApdapterSelect(List<Categories> arrfoods, Context context) {
        this.arrcate = arrfoods;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoriesApdapterSelect.CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CategoriesApdapterSelect.CategoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesApdapterSelect.CategoriesHolder holder, int position) {
        Categories categories = arrcate.get(position);
        if(categories == null){
            return;
        }
        holder.tvname.setText(categories.getName());
        if(categories.getImage() != null){
            Glide.with(context).load(categories.getImage()).into(holder.img);
        }

    }


    @Override
    public int getItemCount() {
        if(arrcate != null) return arrcate.size();
        else return 0;
    }

    public class CategoriesHolder extends RecyclerView.ViewHolder{
        TextView tvname;
        ImageView img, imgmenu;
        public CategoriesHolder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.item_tvname_category);
            img = itemView.findViewById(R.id.item_imgv_category);
            imgmenu = itemView.findViewById(R.id.menu_option);
        }
    }
}
