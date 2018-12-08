package com.android.example.bakingtime;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.contrib.RecyclerViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> ThisActivityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void clickRecipeCardDisplaysIngredients() {
        onView(withId(R.id.view_recipes)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.text_ingredients)).check(matches(isDisplayed()));
    }
}
