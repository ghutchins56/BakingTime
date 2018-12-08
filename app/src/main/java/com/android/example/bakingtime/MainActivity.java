package com.android.example.bakingtime;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_INTERNET = 1;
    private static final String RECIPE_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private MainAdapter ThisMainAdapter;
    private RecyclerView ViewRecipes;
    private ProgressBar ThisProgressBar;
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_INGREDIENTS = "ingredients";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_MEASURE = "measure";
    private static final String KEY_INGREDIENT = "ingredient";
    private static final String KEY_STEPS = "steps";
    private static final String KEY_SHORT_DESCRIPTION = "shortDescription";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_VIDEO_URL = "videoURL";
    private static final String KEY_THUMBNAIL_URL = "thumbnailURL";
    private static final String KEY_SERVINGS = "servings";
    private static final String KEY_IMAGE = "image";
    private RequestQueue ThisRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ThisMainAdapter = new MainAdapter(this);
        ThisProgressBar = findViewById(R.id.progress_bar);
        ViewRecipes = findViewById(R.id.view_recipes);
        int spanCount;
        if (getResources().getBoolean(R.bool.is_tablet)) spanCount = 3; else if (getResources().
                getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) spanCount =
                2; else spanCount = 1;
        ViewRecipes.setLayoutManager(new GridLayoutManager(this, spanCount));
        ViewRecipes.setAdapter(ThisMainAdapter);
    }

    @Override
    protected void onPause() {
        ThisRequestQueue.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.INTERNET}, REQUEST_INTERNET);
        else getRecipes();
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_INTERNET:
                if ((grantResults.length > 0) && (grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED)) getRecipes();
                else {
                    ThisProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, R.string.error_recipes, Toast.
                            LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getRecipes() {
        ThisProgressBar.setVisibility(View.VISIBLE);
        ThisRequestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest thisJsonArrayRequest = new JsonArrayRequest(RECIPE_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Recipe> recipes = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject recipeJSON = response.optJSONObject(i);
                            if (recipeJSON != null) {
                                Recipe recipe = new Recipe();
                                String id = recipeJSON.optString(KEY_ID);
                                if ((id != null) && (!id.isEmpty())) recipe.setId(
                                        Long.valueOf(id));
                                String name = recipeJSON.optString(KEY_NAME);
                                if (name != null) recipe.setName(name);
                                JSONArray ingredientsJSON = recipeJSON.optJSONArray(
                                        KEY_INGREDIENTS);
                                if (ingredientsJSON != null) {
                                    ArrayList<Ingredient> ingredients = new ArrayList<>();
                                    for (int j = 0; j < ingredientsJSON.length(); j++) {
                                        JSONObject ingredientJSON = ingredientsJSON.optJSONObject(j);
                                        if (ingredientJSON != null) {
                                            Ingredient ingredient = new Ingredient();
                                            String quantity = ingredientJSON.optString(
                                                    KEY_QUANTITY);
                                            if ((quantity != null) && (!quantity.isEmpty()))
                                                    ingredient.setQuantity(Double.valueOf(
                                                    quantity));
                                            String measure = ingredientJSON.optString(
                                                    KEY_MEASURE);
                                            if (measure != null) ingredient.setMeasure(measure);
                                            name = ingredientJSON.optString(KEY_INGREDIENT);
                                            if (name != null) ingredient.setName(name);
                                            ingredients.add(ingredient);
                                        }
                                    }
                                    recipe.setIngredients(ingredients);
                                }
                                JSONArray stepsJSON = recipeJSON.optJSONArray(KEY_STEPS);
                                if (stepsJSON != null) {
                                    ArrayList<Step> steps = new ArrayList<>();
                                    for (int j = 0; j < stepsJSON.length(); j++) {
                                        JSONObject stepJSON = stepsJSON.optJSONObject(j);
                                        if (stepJSON != null) {
                                            Step step = new Step();
                                            id = stepJSON.optString(KEY_ID);
                                            if ((id != null) && (!id.isEmpty())) step.setId(
                                                    Long.valueOf(id));
                                            String shortDescription = stepJSON.optString(
                                                    KEY_SHORT_DESCRIPTION);
                                            if (shortDescription != null) step.
                                                    setShortDescription(shortDescription);
                                            String description = stepJSON.optString(
                                                    KEY_DESCRIPTION);
                                            if (description != null) step.setDescription(
                                                    description);
                                            String videoURL = stepJSON.optString(KEY_VIDEO_URL);
                                            if (videoURL != null) step.setVideoURL(videoURL);
                                            String thumbnailURL = stepJSON.optString(
                                                    KEY_THUMBNAIL_URL);
                                            if (thumbnailURL != null) step.setThumbnailURL(
                                                    thumbnailURL);
                                            steps.add(step);
                                        }
                                    }
                                    recipe.setSteps(steps);
                                }
                                String servings = recipeJSON.optString(KEY_SERVINGS);
                                if ((servings != null) && (!servings.isEmpty())) recipe.
                                        setServings(Integer.valueOf(servings));
                                String image = recipeJSON.optString(KEY_IMAGE);
                                if (image != null) recipe.setImage(image);
                                recipes.add(recipe);
                            }
                        }
                        ThisProgressBar.setVisibility(View.INVISIBLE);
                        if (recipes.isEmpty()) Toast.makeText(MainActivity.this,
                                R.string.error_recipes, Toast.LENGTH_LONG).show();
                        ViewRecipes.setVisibility(View.VISIBLE);
                        ThisMainAdapter.setRecipes(recipes);
                        ThisMainAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ThisProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, R.string.error_recipes, Toast.
                                LENGTH_LONG).show();
                    }
                });
        ThisRequestQueue.add(thisJsonArrayRequest);
    }
}
