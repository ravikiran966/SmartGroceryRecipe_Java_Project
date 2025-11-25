package com.example.smartgrocery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartgrocery.models.Ingredient;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_CAMERA = 1001;
    private static final int REQ_IMAGE_CAPTURE = 1002;


    private MainViewModel viewModel;
    private Button btnCapture, btnGenerate;
    private RecyclerView rvIngredients;
    private ImageView imagePreview;
    private TextView tvItemCount;
    private IngredientAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        btnCapture = findViewById(R.id.btnCapture);
        btnGenerate = findViewById(R.id.btnGenerate);
        rvIngredients = findViewById(R.id.rvIngredients);
        imagePreview = findViewById(R.id.imagePreview);
        tvItemCount = findViewById(R.id.tvItemCount);

        rvIngredients.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IngredientAdapter();
        rvIngredients.setAdapter(adapter);

        btnCapture.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQ_CAMERA);
            } else {
                dispatchTakePictureIntent();
            }
        });

        btnGenerate.setOnClickListener(v -> viewModel.generateRecipes());

        // Observe ingredients (update adapter)
        viewModel.getIngredients().observe(this, (List<Ingredient> ingredients) -> {
            adapter.setItems(ingredients);
        });

        // Observe item count (update TextView)
        viewModel.getItemCount().observe(this, count -> {
            tvItemCount.setText("Detected items: " + (count == null ? 0 : count));
            // optional toast:
            // Toast.makeText(MainActivity.this, "Total items: " + count, Toast.LENGTH_SHORT).show();
        });

        // Observe recipes if you want (currently not displayed)
        viewModel.getRecipes().observe(this, recipes -> {
            if (recipes != null && recipes.size() > 0) {
                // Convert recipes to JSON string
                String json = com.example.smartgrocery.models.Recipe.listToJsonString(recipes);

                // Open new Activity to display recipes
                Intent i = new Intent(MainActivity.this, RecipesActivity.class);
                i.putExtra("recipes_json", json);
                startActivity(i);
            } else {
                Toast.makeText(this, "No recipes generated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQ_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            if (imageBitmap != null) {
                imagePreview.setImageBitmap(imageBitmap);
                // send to ViewModel to OCR -> parse
                viewModel.processImage(imageBitmap);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera permission required", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
