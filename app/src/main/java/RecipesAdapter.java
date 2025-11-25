package com.example.smartgrocery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartgrocery.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.VH> {

    private List<Recipe> items = new ArrayList<>();

    public void setItems(List<Recipe> list) {
        items = list != null ? list : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Recipe r = items.get(position);

        holder.tvName.setText(r.getName());
        holder.tvCookTime.setText("Cook Time: " + r.getCookTime());

        // Format ingredients
        StringBuilder ing = new StringBuilder();
        for (String s : r.getIngredients()) ing.append("• ").append(s).append("\n");
        holder.tvIngredients.setText(ing.toString());

        // Format steps
        StringBuilder steps = new StringBuilder();
        for (String s : r.getSteps()) steps.append("• ").append(s).append("\n");
        holder.tvSteps.setText(steps.toString());
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvCookTime, tvIngredients, tvSteps;
        VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvRecipeName);
            tvCookTime = itemView.findViewById(R.id.tvCookTime);
            tvIngredients = itemView.findViewById(R.id.tvIngredients);
            tvSteps = itemView.findViewById(R.id.tvSteps);
        }
    }
}
