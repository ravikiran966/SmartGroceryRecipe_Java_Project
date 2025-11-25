package com.example.smartgrocery;


import android.content.Context;
import android.content.SharedPreferences;


import com.example.smartgrocery.models.Ingredient;


import java.util.List;


public class DataRepository {
    private static final String PREFS = "smartgrocery_prefs";
    private SharedPreferences prefs;
    private SecurityUtils securityUtils;


    public DataRepository(Context ctx) {
        prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        securityUtils = new SecurityUtils(ctx);
    }


    public void saveScanEncrypted(String rawText, List<Ingredient> parsed) {
        try {
            String enc = securityUtils.encryptString(rawText);
            prefs.edit().putString("last_scan", enc).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String loadLastScan() {
        String enc = prefs.getString("last_scan", null);
        if (enc == null) return null;
        try {
            return securityUtils.decryptString(enc);
        } catch (Exception e) {
            return null;
        }
    }
}