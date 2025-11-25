package com.example.smartgrocery;


import com.example.smartgrocery.models.Ingredient;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IngredientParser {
// Simple heuristic parser to convert common patterns to structured data.
// Handles formats like: "Tomato - 1kg", "Onion 500 g", "Rice: 2 kg", "Milk 1L"


    private static final Pattern PATTERN = Pattern.compile("([A-Za-z ]+)[-:\\s]*([0-9]+(?:\\.[0-9]+)?)\\s*(kg|g|l|ml|pcs|pc|tbsp|tsp)?", Pattern.CASE_INSENSITIVE);


    public List<Ingredient> parseRawText(String raw) {
        List<Ingredient> out = new ArrayList<>();
        if (raw == null || raw.trim().isEmpty()) return out;


        String[] lines = raw.split("\\n");
        for (String l : lines) {
            l = l.trim();
            if (l.isEmpty()) continue;
            Matcher m = PATTERN.matcher(l);
            if (m.find()) {
                String name = m.group(1).trim();
                double qty = Double.parseDouble(m.group(2));
                String unit = m.group(3) != null ? m.group(3).toLowerCase(Locale.ROOT) : "pcs";
                Ingredient ing = new Ingredient(name, qty, unit);
                out.add(ing);
            } else {
// fallback: treat whole line as ingredient name
                out.add(new Ingredient(l, 1, "pcs"));
            }
        }
        return out;
    }
}