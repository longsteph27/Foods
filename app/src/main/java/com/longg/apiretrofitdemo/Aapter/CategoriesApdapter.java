package com.longg.apiretrofitdemo.Aapter;

import android.content.Context;
import android.content.Intent;
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
import com.longg.apiretrofitdemo.Activity.LoginActivity;
import com.longg.apiretrofitdemo.Activity.UpdateCategoryActivity;
import com.longg.apiretrofitdemo.Model.Categories;
import com.longg.apiretrofitdemo.R;
import com.longg.apiretrofitdemo.api.DeleteCategoryApi;
import com.longg.apiretrofitdemo.api.DeleteFoodApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesApdapter extends RecyclerView.Adapter<CategoriesApdapter.CategoriesHolder>{
    private List<Categories> arrcate;
    private Context context;

    public CategoriesApdapter(List<Categories> arrfoods, Context context) {
        this.arrcate = arrfoods;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesHolder holder, int position) {
        Categories categories = arrcate.get(position);
        if(categories == null){
            return;
        }
        holder.tvname.setText(categories.getName());
        if(categories.getImage() != null){
            Glide.with(context).load(categories.getImage()).into(holder.img);
        }
        holder.imgmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view, categories, holder.getAdapterPosition());
            }
        });
    }

    private void showPopup(View view, Categories categories, int adapterposition) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.menu_options);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuitem) {
                int id=menuitem.getItemId();
                switch (id){
                    case R.id.item_update:
                        Intent intent = new Intent(context, UpdateCategoryActivity.class);
                        intent.putExtra("cate_name", categories.getName());
                        intent.putExtra("cate_id",String.valueOf(categories.getId()));
                        intent.putExtra("cate_image", categories.getImage());
                        /*intent.putExtra("img", foods.getImage());
                        intent.putExtra("cate_id",String.valueOf(foods.getCategory().getId()));
                        intent.putExtra("cate_name", foods.getCategory().getName());
                        intent.putExtra("cate_image", foods.getCategory().getImage());
                        intent.putExtra("description", foods.getDescription());
                        intent.putExtra("energyPerServing", String.valueOf(foods.getEnergyPerServing()));
                        intent.putExtra("Fat", String.valueOf(foods.getFat()));
                        intent.putExtra("id",String.valueOf(foods.getId()));
                        intent.putExtra("name", foods.getName());
                        intent.putExtra("protein", String.valueOf(foods.getProtein()));*/
                        view.getContext().startActivity(intent);
                        break;

                    case R.id.item_delete:
                        callapidelete(LoginActivity.token, categories.getId(), adapterposition);
                        break;
                }
                return false;
            }
        });

        popupMenu.show();
    }

    private void callapidelete(String token, int id, int adapterposition) {
        DeleteCategoryApi.DELETE_CATEGORY_API.deletecate("Bearer " + token,
                id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    arrcate.remove(adapterposition);
                    notifyItemRemoved(adapterposition);
                    Toast.makeText(context, "Delete Succes", Toast.LENGTH_SHORT).show();
                }
                if(response.code() == 400){
                    Toast.makeText(context, "Khong the xoa", Toast.LENGTH_SHORT).show();
                }
                if(response.code() == 401){
                    Toast.makeText(context, "401", Toast.LENGTH_SHORT).show();
                }
                if(response.code() == 403){
                    Toast.makeText(context, "403", Toast.LENGTH_SHORT).show();
                }
                if(response.code() == 404){
                    Toast.makeText(context, "404", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(context, "okkk", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context,"No Delete", Toast.LENGTH_SHORT).show();
            }
        });
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
