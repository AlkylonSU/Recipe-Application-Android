package com.example.recipeapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecipeSearchResultsActivity extends AppCompatActivity {
    Context context;
    RecyclerView recView;



    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            List<Recipes> data = (List<Recipes>) msg.obj;
            RecipeViewAdapter adp
                    = new RecipeViewAdapter(RecipeSearchResultsActivity.this,data);
            recView.setAdapter(adp);
            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_res);

        recView = findViewById(R.id.recSearch);
        recView.setLayoutManager(new LinearLayoutManager(this));
        String title = getIntent().getExtras().getString("title");
        // Create an empty list or initialize with initial data
        List<Recipes> emptyData = new ArrayList<>();

        // Create the adapter and set it to the RecyclerView
        RecipeViewAdapter adapter = new RecipeViewAdapter(this, emptyData);
        recView.setAdapter(adapter);

        // Load the data from the repository
        RecipeRepo repo = new RecipeRepo();
        repo.searchRecipe(((RecipeApplication)getApplication()).srv, handler, title);


    }
}
