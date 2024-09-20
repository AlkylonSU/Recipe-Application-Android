package com.example.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

public class UpdateRecipe extends AppCompatActivity {

        EditText title;
        EditText category;
        EditText preparingTimeMins;
        EditText cookingTimeMins;
        EditText ingredients;
        EditText procedure;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            Recipes recipe = (Recipes) msg.obj;

            title.setText(recipe.getTitle());
            category.setText(recipe.getCategory());
            preparingTimeMins.setText(recipe.getPreparingTimeMins());
            cookingTimeMins.setText(recipe.getCookingTimeMins());
            ingredients.setText(recipe.getIngredients());
            procedure.setText(recipe.getProcedure());
            return true;
        }
    });

        @Override
        protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);

        Button btnAdd = findViewById(R.id.uprec);

        title = findViewById(R.id.txtSearchTitle);
        category = findViewById(R.id.ucat);
        preparingTimeMins = findViewById(R.id.uprep);
        cookingTimeMins = findViewById(R.id.ucook);
        ingredients = findViewById(R.id.uing);
        procedure = findViewById(R.id.uproc);

            RecipeRepo repo = new RecipeRepo();
            String id = getIntent().getExtras().getString("id");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title1 = title.getText().toString();
                String category1 = category.getText().toString();
                String prepMinString = preparingTimeMins.getText().toString();
                String cookMinString = cookingTimeMins.getText().toString();
                String ing = ingredients.getText().toString();
                String proc = procedure.getText().toString();


                int prepMin = Integer.parseInt(prepMinString);
                int cookMin = Integer.parseInt(cookMinString);


                ExecutorService srv = ((RecipeApplication)getApplication()).srv;
                RecipeRepo repo1 = new RecipeRepo();
                repo1.UpdateRecipe(srv,id, title1, category1, prepMin, cookMin, ing, proc);

                finish();
                Intent intent = new Intent(UpdateRecipe.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                MainActivity mainActivity = (MainActivity) intent.getSerializableExtra("MainActivity");
                mainActivity.recreate();

            }
        });


            repo.getDataById(((RecipeApplication)getApplication()).srv,handler,id);

    }
}
