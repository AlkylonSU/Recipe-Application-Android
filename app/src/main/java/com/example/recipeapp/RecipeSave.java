package com.example.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

public class RecipeSave extends AppCompatActivity {

    EditText title;
    EditText category;
    EditText preparingTimeMins;
    EditText cookingTimeMins;
    EditText ingredients;
    EditText procedure;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        Button btnAdd = findViewById(R.id.btnSearchRec);

        title = findViewById(R.id.txtSearchTitle);
        category = findViewById(R.id.ucat);
        preparingTimeMins = findViewById(R.id.uprep);
        cookingTimeMins = findViewById(R.id.ucook);
        ingredients = findViewById(R.id.uing);
        procedure = findViewById(R.id.uproc);
        
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
                RecipeRepo repo = new RecipeRepo();
                repo.saveRecipe(srv, title1, category1, prepMin, cookMin, ing, proc);

                finish();
                Intent intent = new Intent(RecipeSave.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }
}
