package com.example.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context context;
    RecyclerView recView;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            List<Recipes> data = (List<Recipes>) msg.obj;
            RecipeViewAdapter adp
                    = new RecipeViewAdapter(MainActivity.this,data);
            recView.setAdapter(adp);
            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recView = findViewById(R.id.recRecipe);
        recView.setLayoutManager(new LinearLayoutManager(this));

        // Create an empty list or initialize with initial data
        List<Recipes> emptyData = new ArrayList<>();

        // Create the adapter and set it to the RecyclerView
        RecipeViewAdapter adapter = new RecipeViewAdapter(this, emptyData);
        recView.setAdapter(adapter);

        // Load the data from the repository
        RecipeRepo repo = new RecipeRepo();
        repo.getAllRecipes(((RecipeApplication)getApplication()).srv, handler);

        Button addRecipe = findViewById(R.id.saveRecipe);
        addRecipe.setOnClickListener(v->{
            Intent i = new Intent(MainActivity.this, RecipeSave.class);
            startActivity(i);
        });
        Button searchRecipe = findViewById(R.id.btnSearch);
        searchRecipe.setOnClickListener(v->{
            Intent i = new Intent(MainActivity.this, RecipeSearchPageActivity.class);
            startActivity(i);
        });
    }

}