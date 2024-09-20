package com.example.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

public class RecipeSearchPageActivity extends AppCompatActivity {

    EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        Button btnSearch = findViewById(R.id.btnSearchRec);

        title = findViewById(R.id.txtSearchTitle);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title1 = title.getText().toString();
                Intent i = new Intent(RecipeSearchPageActivity.this, RecipeSearchResultsActivity.class);
                i.putExtra("title", title1);
                startActivity(i);
            }
        });
    }
}
