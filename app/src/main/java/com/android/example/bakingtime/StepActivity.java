package com.android.example.bakingtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StepActivity extends AppCompatActivity {
    private static final String KEY_STEP = "step";
    private static final String KEY_RECIPE_NAME = "recipeName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra(KEY_RECIPE_NAME));
        if (savedInstanceState == null) {
            StepFragment fragment = new StepFragment();
            fragment.setStep((Step) (intent.getParcelableExtra(KEY_STEP)));
            getSupportFragmentManager().beginTransaction().add(R.id.layout_step, fragment).commit();
        }
    }
}
