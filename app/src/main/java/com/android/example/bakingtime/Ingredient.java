package com.android.example.bakingtime;

class Ingredient {
    private double Quantity;
    private String Measure;
    private String Name;

    Ingredient() {
        Quantity = 0.0;
        Measure = "";
        Name = "";
    }

    double getQuantity() {
        return Quantity;
    }

    void setQuantity(double value) {
        Quantity = value;
    }

    String getMeasure() {
        return Measure;
    }

    void setMeasure(String value) {
        Measure = value;
    }

    String getName() {
        return Name;
    }

    void setName(String value) {
        Name = value;
    }
}
