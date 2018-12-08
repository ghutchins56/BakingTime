package com.android.example.bakingtime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import java.util.ArrayList;

public class RecipeWidget extends AppWidgetProvider {
    private static Recipe ThisRecipe;
    private static int Position;
    private static final String KEY_POSITION = "position";

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        if (ThisRecipe == null) {
            views.setViewVisibility(R.id.image_default, View.VISIBLE);
            views.setViewVisibility(R.id.text_name, View.GONE);
            views.setViewVisibility(R.id.text_ingredients, View.GONE);
            views.setImageViewResource(R.id.image_default, R.drawable.bake);
        } else {
            views.setViewVisibility(R.id.image_default, View.GONE);
            views.setViewVisibility(R.id.text_name, View.VISIBLE);
            views.setViewVisibility(R.id.text_ingredients, View.VISIBLE);
            views.setTextViewText(R.id.text_name, ThisRecipe.getName());
            ArrayList<Ingredient> ingredients = ThisRecipe.getIngredients();
            StringBuilder builder = new StringBuilder();
            int size = ingredients.size();
            for (int i = 0; i < size; i++) {
                Ingredient ingredient = ingredients.get(i);
                builder.append(ingredient.getQuantity());
                builder.append(" ");
                builder.append(ingredient.getMeasure());
                builder.append(" ");
                builder.append(ingredient.getName());
                if (i < size - 1) builder.append("\n");
            }
            views.setTextViewText(R.id.text_ingredients, builder.toString());
        }
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(KEY_POSITION, Position);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.layout_widget, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int[]
            appWidgetIds, Recipe recipe, int position) {
        ThisRecipe = recipe;
        Position = position;
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
       for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

