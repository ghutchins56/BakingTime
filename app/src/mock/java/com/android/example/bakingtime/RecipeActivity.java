package com.android.example.bakingtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity implements RecipeAdapter.OnStepClickListener {
    private Recipe ThisRecipe;
    private static final String KEY_STEP = "step";
    private static final String KEY_RECIPE_NAME = "recipeName";
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ThisRecipe = new Recipe();
        ThisRecipe.setId(1);
        ThisRecipe.setName("Recipe name");
        Ingredient ingredient = new Ingredient();
        ingredient.setQuantity(1);
        ingredient.setMeasure("ea");
        ingredient.setName("Recipe ingredient");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient);
        ThisRecipe.setIngredients(ingredients);
        Step step = new Step();
        step.setId(1);
        step.setShortDescription("Short description of recipe step");
        step.setDescription("Full description of recipe step");
        step.setVideoURL("");
        step.setThumbnailURL("");
        ArrayList<Step> steps = new ArrayList<>();
        steps.add(step);
        ThisRecipe.setSteps(steps);
        ThisRecipe.setServings(1);
        ThisRecipe.setImage("");
        setTitle(ThisRecipe.getName());
        isTablet = getResources().getBoolean(R.bool.is_tablet);
        if (savedInstanceState == null) {
            RecipeFragment recipeFragment = new RecipeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_recipe, recipeFragment).
                    commit();
            if (isTablet) {
                StepFragment stepFragment = new StepFragment();
                stepFragment.setStep(ThisRecipe.getSteps().get(0));
                getSupportFragmentManager().beginTransaction().add(R.id.layout_step, stepFragment).
                        commit();
            }
        }
    }

    public void onStepClicked(int position) {
        Step step = ThisRecipe.getSteps().get(position);
        if (isTablet) {
            StepFragment fragment = new StepFragment();
            fragment.setStep(step);
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_step, fragment).
			commit();
        } else {
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(KEY_STEP, step);
            intent.putExtra(KEY_RECIPE_NAME, ThisRecipe.getName());
            startActivity(intent);
        }
    }
}
