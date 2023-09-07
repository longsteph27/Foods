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
import com.longg.apiretrofitdemo.Activity.UpdateFoodActivity;
import com.longg.apiretrofitdemo.Model.Foods;
import com.longg.apiretrofitdemo.R;
import com.longg.apiretrofitdemo.api.DeleteFoodApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodsApdapter extends RecyclerView.Adapter<FoodsApdapter.FoodViewHolder>{
    private List<Foods> arrfoods;
    private Context context;

    public FoodsApdapter(List<Foods> arrfoods, Context context) {
        this.arrfoods = arrfoods;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foods, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Foods foods = arrfoods.get(position);
        if(foods == null){
            return;
        }
        holder.tvname.setText(foods.getName());
        holder.tvkcal.setText(String.valueOf(foods.getEnergyPerServing()));
        if(foods.getImage() != null){
            Glide.with(context).load(foods.getImage()).into(holder.img);
        }
        holder.imgmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view, foods, holder.getAdapterPosition());
            }
        });
    }

    private void showPopup(View view, Foods foods, int adapterposition) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.menu_options);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuitem) {
                int id=menuitem.getItemId();
                switch (id){
                    case R.id.item_update:
                        Intent intent = new Intent(context, UpdateFoodActivity.class);
                        intent.putExtra("carb", String.valueOf(foods.getCarb()));
                        intent.putExtra("img", foods.getImage());
                        intent.putExtra("cate_id",String.valueOf(foods.getCategory().getId()));
                        intent.putExtra("cate_name", foods.getCategory().getName());
                        intent.putExtra("cate_image", foods.getCategory().getImage());
                        intent.putExtra("description", foods.getDescription());
                        intent.putExtra("energyPerServing", String.valueOf(foods.getEnergyPerServing()));
                        intent.putExtra("Fat", String.valueOf(foods.getFat()));
                        intent.putExtra("id",String.valueOf(foods.getId()));
                        intent.putExtra("name", foods.getName());
                        intent.putExtra("protein", String.valueOf(foods.getProtein()));
                        view.getContext().startActivity(intent);
                        break;

                    case R.id.item_delete:
                        callapidelete(LoginActivity.token, foods.getId(), adapterposition);
                        break;
                }
                return false;
            }
        });

        popupMenu.show();
    }

    private void callapidelete(String token, int id, int adapterposition) {
        DeleteFoodApi.DELETE_FOOD_API.deleteFood("Bearer " + token,
                id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    arrfoods.remove(adapterposition);
                    notifyItemRemoved(adapterposition);
                    Toast.makeText(context, "Delete succes", Toast.LENGTH_SHORT).show();
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

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context,"Can't Delete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(arrfoods != null) return arrfoods.size();
        else return 0;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{
        TextView tvname, tvkcal;
        ImageView img, imgmenu;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.item_tvnamefoods);
            tvkcal = itemView.findViewById(R.id.item_tvcalo);
            img = itemView.findViewById(R.id.item_imgvfoods);
            imgmenu = itemView.findViewById(R.id.menu_option);
        }
    }


}
