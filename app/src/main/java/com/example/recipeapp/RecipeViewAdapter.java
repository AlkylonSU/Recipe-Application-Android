package com.example.recipeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;
import java.util.concurrent.ExecutorService;

public class RecipeViewAdapter extends RecyclerView.Adapter<RecipeViewAdapter.RecipeViewHolder> {

    Context context;
    List<Recipes> data;

    public RecipeViewAdapter(Context context, List<Recipes> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root=
                LayoutInflater.from(context).inflate(R.layout.recipe_row_layout,parent,false);
        RecipeViewHolder holder = new RecipeViewHolder(root);
        holder.setIsRecyclable(false);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        holder.txtTitle.setText(data.get(position).getTitle());
        holder.txtCategory.setText(data.get(position).getCategory());
        RecipeApplication app = (RecipeApplication) ((Activity)context).getApplication();



        holder.row.setOnClickListener(v->{

            Intent i = new Intent(context,RecipeDetailActivity.class);
            i.putExtra("id",data.get(position).getId());
            i.putExtra("MainActivity", MainActivity.class);
            ((Activity)context).startActivity(i);


        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout row;
        TextView txtTitle;
        TextView txtCategory;




        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            row = itemView.findViewById(R.id.reciperow);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtCategory = itemView.findViewById(R.id.txtCategory);


        }

    }



}
