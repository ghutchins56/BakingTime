package com.android.example.bakingtime;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {
    @Rule
    public ActivityTestRule<RecipeActivity> ThisActivityTestRule = new ActivityTestRule<>(
            RecipeActivity.class);

    @Test
    public void clickRecipeStepDisplaysDetails() {
        onView(withId(R.id.view_steps)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.image_step)).check(matches(isDisplayed()));
    }
}
