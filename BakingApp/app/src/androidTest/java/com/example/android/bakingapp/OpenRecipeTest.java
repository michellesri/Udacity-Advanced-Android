package com.example.android.bakingapp;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class OpenRecipeTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testOpenRecipe() {
        try {
            Thread.sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        onView(withId(R.id.main_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.detail_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.previous_step_btn))
                .check(ViewAssertions.matches(not(isEnabled())));

        onView(withId(R.id.next_step_btn))
                .check(ViewAssertions.matches(isEnabled()));

        onView(withId(R.id.next_step_btn))
                .perform(click());

        onView(withId(R.id.previous_step_btn))
                .check(ViewAssertions.matches(isEnabled()));

        onView(withId(R.id.next_step_btn))
                .check(ViewAssertions.matches(isEnabled()));






    }



}
