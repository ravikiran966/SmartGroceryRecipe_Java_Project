package com.example.smartgrocery;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartgrocery.models.Recipe;

import java.util.List;

public class RecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        RecyclerView rv = findViewById(R.id.rvRecipes);
        rv.setLayoutManager(new LinearLayoutManager(this));

        TextView tvHeader = findViewById(R.id.tvRecipesHeader);

        // Get recipes JSON
        String json = getIntent().getStringExtra("recipes_json");
        List<Recipe> recipes = Recipe.listFromJsonString(json);

        tvHeader.setText("Suggested Recipes (" + recipes.size() + ")");

        RecipesAdapter adapter = new RecipesAdapter();
        adapter.setItems(recipes);
        rv.setAdapter(adapter);
    }
}
