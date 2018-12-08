package com.android.example.bakingtime;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

class Recipe implements Parcelable {
    private long Id;
    private String Name;
    private ArrayList<Ingredient> Ingredients;
    private ArrayList<Step> Steps;
    private int Servings;
    private String Image;

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int i) {
            return new Recipe[i];
        }
    };

    Recipe() {
        Id = -1;
        Name = "";
        Ingredients = new ArrayList<>();
        Steps = new ArrayList<>();
        Servings = 0;
        Image = "";
    }

    private Recipe(Parcel parcel) {
        Id = parcel.readLong();
        Name = parcel.readString();
        Ingredients = new ArrayList<>();
        int size = parcel.readInt();
        for (int i = 0; i < size; i++) {
            Ingredient ingredient = new Ingredient();
            ingredient.setQuantity(parcel.readDouble());
            ingredient.setMeasure(parcel.readString());
            ingredient.setName(parcel.readString());
            Ingredients.add(ingredient);
        }
        Steps = new ArrayList<>();
        size = parcel.readInt();
        for (int i = 0; i < size; i++) {
            Step step = new Step();
            step.setId(parcel.readLong());
            step.setShortDescription(parcel.readString());
            step.setDescription(parcel.readString());
            step.setVideoURL(parcel.readString());
            step.setThumbnailURL(parcel.readString());
            Steps.add(step);
        }
        Servings = parcel.readInt();
        Image = parcel.readString();
    }

    void setId(long value) {
        Id = value;
    }

    String getName() {
        return Name;
    }

    void setName(String value) {
        Name = value;
    }

    ArrayList<Ingredient> getIngredients() {
        return Ingredients;
    }

    void setIngredients(ArrayList<Ingredient> ingredients) {
        Ingredients = ingredients;
    }

    ArrayList<Step> getSteps() {
        return Steps;
    }

    void setSteps(ArrayList<Step> steps) {
        Steps = steps;
    }

    int getServings() {
        return Servings;
    }

    void setServings(int value) {
        Servings = value;
    }

    String getImage() {
        return Image;
    }

    void setImage(String value) {
        Image = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(Id);
        parcel.writeString(Name);
        parcel.writeInt(Ingredients.size());
        for (Ingredient ingredient : Ingredients) {
            parcel.writeDouble(ingredient.getQuantity());
            parcel.writeString(ingredient.getMeasure());
            parcel.writeString(ingredient.getName());
        }
        parcel.writeInt(Steps.size());
        for (Step step : Steps) {
            parcel.writeLong(step.getId());
            parcel.writeString(step.getShortDescription());
            parcel.writeString(step.getDescription());
            parcel.writeString(step.getVideoURL());
            parcel.writeString(step.getThumbnailURL());
        }
        parcel.writeInt(Servings);
        parcel.writeString(Image);
    }
}
