package com.example.smartgrocery;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.smartgrocery.models.Ingredient;
import com.example.smartgrocery.models.Recipe;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Ingredient>> ingredients = new MutableLiveData<>();
    private final MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();

    // 🔥 THIS IS THE PART YOUR FILE WAS MISSING
    private final MutableLiveData<Integer> itemCount = new MutableLiveData<>(0);

    private final DataRepository repository;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
    }

    public LiveData<List<Ingredient>> getIngredients() {
        return ingredients;
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

    // 🔥 MISSING METHOD — REQUIRED BY MainActivity
    public LiveData<Integer> getItemCount() {
        return itemCount;
    }

    // OCR + parsing logic
    public void processImage(Bitmap bitmap) {
        executor.execute(() -> {
            OCRProcessor ocr = new OCRProcessor(getApplication());
            String raw = ocr.recognizeText(bitmap);

            IngredientParser parser = new IngredientParser();
            List<Ingredient> list = parser.parseRawText(raw);

            ingredients.postValue(list);

            // 🔥 Update count
            if (list != null) itemCount.postValue(list.size());
            else itemCount.postValue(0);

            repository.saveScanEncrypted(raw, list);
        });
    }

    public void generateRecipes() {
        executor.execute(() -> {
            List<Ingredient> list = ingredients.getValue();
            RecipeGenerator generator = new RecipeGenerator();
            List<Recipe> out = generator.generate(list);
            recipes.postValue(out);
        });
    }
}
