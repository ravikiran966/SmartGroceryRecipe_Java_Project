package com.example.smartgrocery.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple recipe model with JSON helpers for intent transfer.
 */
public class Recipe {
    private String name;
    private List<String> steps = new ArrayList<>();
    private List<String> ingredients = new ArrayList<>();
    private String cookTime = "";
    private List<String> tags = new ArrayList<>();

    public Recipe(String name) { this.name = name; }

    public String getName() { return name; }
    public List<String> getSteps() { return steps; }
    public List<String> getIngredients() { return ingredients; }
    public String getCookTime() { return cookTime; }
    public List<String> getTags() { return tags; }

    public void setCookTime(String cookTime) { this.cookTime = cookTime; }
    public void addStep(String s) { steps.add(s); }
    public void addIngredient(String name, String qty, String unit) { ingredients.add(name + " - " + qty + " " + unit); }
    public void addTag(String tag) { tags.add(tag); }

    // Convert to JSONObject
    public JSONObject toJson() throws JSONException {
        JSONObject o = new JSONObject();
        o.put("name", name);
        o.put("cook_time", cookTime);
        o.put("ingredients", new JSONArray(ingredients));
        o.put("steps", new JSONArray(steps));
        o.put("tags", new JSONArray(tags));
        return o;
    }

    // From JSONObject
    public static Recipe fromJson(JSONObject o) throws JSONException {
        Recipe r = new Recipe(o.optString("name", "Recipe"));
        r.setCookTime(o.optString("cook_time", ""));
        JSONArray aIng = o.optJSONArray("ingredients");
        if (aIng != null) {
            for (int i=0;i<aIng.length();i++) r.ingredients.add(aIng.getString(i));
        }
        JSONArray aSteps = o.optJSONArray("steps");
        if (aSteps != null) {
            for (int i=0;i<aSteps.length();i++) r.steps.add(aSteps.getString(i));
        }
        JSONArray aTags = o.optJSONArray("tags");
        if (aTags != null) {
            for (int i=0;i<aTags.length();i++) r.tags.add(aTags.getString(i));
        }
        return r;
    }

    public static String listToJsonString(List<Recipe> list) {
        try {
            JSONArray arr = new JSONArray();
            for (Recipe r : list) arr.put(r.toJson());
            return arr.toString();
        } catch (JSONException e) {
            return "[]";
        }
    }

    public static List<Recipe> listFromJsonString(String json) {
        List<Recipe> out = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(json);
            for (int i=0;i<arr.length();i++) {
                out.add(fromJson(arr.getJSONObject(i)));
            }
        } catch (Exception ignored) {}
        return out;
    }
}
