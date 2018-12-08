package com.android.example.bakingtime;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeFragment extends Fragment {
    private static final String KEY_POSITION = "position";
    private RecipeAdapter ThisRecipeAdapter;

    public RecipeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setName("Recipe name");
        Ingredient ingredient = new Ingredient();
        ingredient.setQuantity(1);
        ingredient.setMeasure("ea");
        ingredient.setName("Recipe ingredient");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient);
        recipe.setIngredients(ingredients);
        Step step = new Step();
        step.setId(1);
        step.setShortDescription("Short description of recipe step");
        step.setDescription("Full description of recipe step");
        step.setVideoURL("");
        step.setThumbnailURL("");
        ArrayList<Step> steps = new ArrayList<>();
        steps.add(step);
        recipe.setSteps(steps);
        recipe.setServings(1);
        recipe.setImage("");
        Context context = getContext();
        View view = inflater.inflate(R.layout.fragment_recipe,
                container, false);
        ConstraintLayout layout = (ConstraintLayout) view;
        TextView textIngredients = view.findViewById(R.id.text_ingredients);
        RecyclerView viewSteps = layout.findViewById(R.id.view_steps);
        ThisRecipeAdapter = new RecipeAdapter(context);
        viewSteps.setLayoutManager(new LinearLayoutManager(context));
        viewSteps.setAdapter(ThisRecipeAdapter);
        ingredients = recipe.getIngredients();
        int size = ingredients.size();
        if (size == 0) {
            textIngredients.setText("");
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < size; i++) {
                ingredient = ingredients.get(i);
                builder.append(ingredient.getQuantity());
                builder.append(" ");
                builder.append(ingredient.getMeasure());
                builder.append(" ");
                builder.append(ingredient.getName());
                if (i < size - 1) builder.append("\n");
            }
            textIngredients.setText(builder.toString());
        }
        ThisRecipeAdapter.setSteps(recipe.getSteps());
        if (savedInstanceState != null) ThisRecipeAdapter.setPosition(savedInstanceState.getInt(
                KEY_POSITION));
        ThisRecipeAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(KEY_POSITION, ThisRecipeAdapter.getPosition());
        super.onSaveInstanceState(outState);
    }
}
