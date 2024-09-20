package com.example.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    TextView txtTitle;
    TextView txtCategory;
    TextView txtPrep;
    TextView txtCook;
    TextView txtIngredients;
    TextView txtProc;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            Recipes recipe = (Recipes) msg.obj;

            txtTitle.setText(recipe.getTitle());
            txtCategory.setText(recipe.getCategory());
            txtPrep.setText(recipe.getPreparingTimeMins());
            txtCook.setText(recipe.getCookingTimeMins());
            txtIngredients.setText(recipe.getIngredients());
            txtProc.setText(recipe.getProcedure());
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        txtTitle = findViewById(R.id.txtTitle);
        txtCategory = findViewById(R.id.txtCategory);
        txtPrep = findViewById(R.id.txtPrep);
        txtCook = findViewById(R.id.txtCook);
        txtIngredients = findViewById(R.id.txtIngredients);
        txtProc = findViewById(R.id.txtProc);


        RecipeRepo repo = new RecipeRepo();
        String id = getIntent().getExtras().getString("id");

        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(v->{
            repo.deleteRecipe(((RecipeApplication)getApplication()).srv, id);
            finish();
            Intent intent = new Intent(RecipeDetailActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        });

        Button btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(v->{
            Intent i = new Intent(RecipeDetailActivity.this, UpdateRecipe.class);
            i.putExtra("id", id);
            i.putExtra("MainActivity", MainActivity.class);
            startActivity(i);
        });

        repo.getDataById(((RecipeApplication)getApplication()).srv,handler,id);
    }

}
