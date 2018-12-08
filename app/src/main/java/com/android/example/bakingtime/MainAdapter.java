package com.android.example.bakingtime;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private ArrayList<Recipe> Recipes;
    private static final String KEY_RECIPE = "recipe";
    private final Context ThisContext;

    MainAdapter(Context context) {
        ThisContext = context;
        Recipes = new ArrayList<>();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView TextName;
        private final ImageView ImageRecipe;
        private final TextView TextServings;

        ViewHolder(CardView view) {
            super(view);
            TextName = view.findViewById(R.id.text_name);
            ImageRecipe = view.findViewById(R.id.image_recipe);
            TextServings = view.findViewById(R.id.text_servings);
            view.setOnClickListener(this);
        }

        TextView getTextName() {
            return TextName;
        }

        ImageView getImageRecipe() {
            return ImageRecipe;
        }

        TextView getTextServings() {
            return TextServings;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ThisContext, RecipeActivity.class);
            int position = getAdapterPosition();
            Recipe recipe = Recipes.get(position);
            intent.putExtra(KEY_RECIPE, recipe);
            ThisContext.startActivity(intent);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ThisContext);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(ThisContext,
                    RecipeWidget.class));
            RecipeWidget.updateRecipeWidgets(ThisContext, appWidgetManager, appWidgetIds, recipe,
                    position);
        }
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.
                item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextName().setText(Recipes.get(position).getName());
        int value = Recipes.get(position).getServings();
        String servings;
        if (value == 0) {
            servings = ThisContext.getString(R.string.servings_not_available);
        } else if (value > 1) {
            servings = value + ThisContext.getString(R.string.servings);
        } else {
            servings = value + ThisContext.getString(R.string.serving);
        }
        holder.getTextServings().setText(servings);
        String image = Recipes.get(position).getImage();
        ImageView imageRecipe = holder.getImageRecipe();
        if (image.isEmpty()) imageRecipe.setImageResource(R.drawable.bake);
        else {
            Picasso.get().load(image).into(imageRecipe);
        }
    }

    @Override
    public int getItemCount() {
        return Recipes.size();
    }

    void setRecipes(ArrayList<Recipe> recipes) {
        Recipes = recipes;
    }
}
