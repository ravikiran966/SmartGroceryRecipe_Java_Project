package com.example.smartgrocery;

import com.example.smartgrocery.models.Ingredient;
import com.example.smartgrocery.models.Recipe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Lightweight recipe generator — deterministic and offline.
 * Behaves like a constrained LLM: takes ingredient list, applies rules and templates,
 * and returns a list of Recipe objects.
 */
public class RecipeGenerator {

    /**
     * Generate recipe suggestions from ingredients.
     * Deterministic output, safe for offline use and easy to parse.
     */
    public List<Recipe> generate(List<Ingredient> ingredients) {
        List<Recipe> out = new ArrayList<>();
        if (ingredients == null || ingredients.isEmpty()) return out;

        // Build a set of normalized ingredient names
        Set<String> names = new HashSet<>();
        for (Ingredient i : ingredients) {
            if (i.getName() != null) names.add(i.getName().toLowerCase(Locale.ROOT));
        }

        // Heuristics: match common combos
        if (names.contains("tomato") && names.contains("onion") && names.contains("rice")) {
            out.add(makeTomatoRice());
            out.add(makeTomatoCurry());
        } else if (names.contains("tomato") && names.contains("onion")) {
            out.add(makeTomatoCurry());
            out.add(makeQuickSalsa());
        } else if (names.contains("rice") && (names.contains("onion") || names.contains("carrot") || names.contains("peas"))) {
            out.add(makeFriedRice());
        } else if (names.contains("milk") && names.contains("sugar")) {
            out.add(makeKheer());
        } else {
            // Generic suggestions: salad + stir-fry
            out.add(makeGenericSalad(ingredients));
            out.add(makeSimpleStirFry(ingredients));
        }

        // always return at least 1-3 recipes
        if (out.size() > 3) return out.subList(0, 3);
        return out;
    }

    // Template recipes below:
    private Recipe makeTomatoCurry() {
        Recipe r = new Recipe("Simple Tomato Curry");
        r.setCookTime("25 mins");
        r.addIngredient("Tomato", "3", "pcs");
        r.addIngredient("Onion", "1", "pcs");
        r.addStep("Chop onions and tomatoes.");
        r.addStep("Sauté onions in oil until golden.");
        r.addStep("Add tomatoes and cook until soft; add salt, turmeric and chili.");
        r.addStep("Simmer for 8-10 minutes. Serve hot with rice or roti.");
        r.addTag("vegetarian"); r.addTag("spicy");
        return r;
    }

    private Recipe makeTomatoRice() {
        Recipe r = new Recipe("Tomato Rice");
        r.setCookTime("30 mins");
        r.addIngredient("Rice", "2", "cups");
        r.addIngredient("Tomato", "2", "pcs");
        r.addIngredient("Onion", "1", "pcs");
        r.addStep("Cook rice until just done and keep aside.");
        r.addStep("Sauté onions and tomatoes; add spices.");
        r.addStep("Mix rice with the tomato masala, simmer for 2-3 minutes.");
        r.addTag("one-pot"); r.addTag("vegetarian");
        return r;
    }

    private Recipe makeFriedRice() {
        Recipe r = new Recipe("Quick Fried Rice");
        r.setCookTime("15 mins");
        r.addIngredient("Cooked Rice", "2", "cups");
        r.addIngredient("Onion", "1", "pcs");
        r.addStep("Heat oil, sauté onions and any vegetables available.");
        r.addStep("Add rice and soy sauce, stir-fry for 2-3 minutes.");
        r.addTag("quick"); r.addTag("vegetarian");
        return r;
    }

    private Recipe makeQuickSalsa() {
        Recipe r = new Recipe("Quick Tomato-Onion Salsa");
        r.setCookTime("10 mins");
        r.addIngredient("Tomato", "2", "pcs");
        r.addIngredient("Onion", "1", "pcs");
        r.addStep("Finely chop tomatoes and onions; mix with salt and lemon.");
        r.addStep("Serve as a chutney or dip.");
        r.addTag("fresh"); r.addTag("condiment");
        return r;
    }

    private Recipe makeKheer() {
        Recipe r = new Recipe("Easy Kheer (Rice Pudding)");
        r.setCookTime("40 mins");
        r.addIngredient("Milk", "1", "liter");
        r.addIngredient("Rice", "1/2", "cup");
        r.addIngredient("Sugar", "1/2", "cup");
        r.addStep("Cook rice in milk until soft; add sugar and simmer.");
        r.addTag("dessert"); r.addTag("vegetarian");
        return r;
    }

    private Recipe makeGenericSalad(List<Ingredient> ingredients) {
        Recipe r = new Recipe("Quick Mixed Salad");
        r.setCookTime("10 mins");
        for (Ingredient i : ingredients) r.addIngredient(i.getName(), String.valueOf(i.getQuantity()), i.getUnit());
        r.addStep("Chop ingredients; toss with salt, lemon juice and serve.");
        r.addTag("raw"); r.addTag("healthy");
        return r;
    }

    private Recipe makeSimpleStirFry(List<Ingredient> ingredients) {
        Recipe r = new Recipe("Simple Stir Fry");
        r.setCookTime("12 mins");
        for (Ingredient i : ingredients) r.addIngredient(i.getName(), String.valueOf(i.getQuantity()), i.getUnit());
        r.addStep("Heat oil, add ingredients and stir-fry until cooked; season to taste.");
        r.addTag("quick"); r.addTag("savory");
        return r;
    }
}
