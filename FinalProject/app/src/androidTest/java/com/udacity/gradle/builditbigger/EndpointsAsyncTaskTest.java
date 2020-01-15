package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EndpointsAsyncTaskTest {

    @Test
    public void test() {
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask();
        String joke = endpointsAsyncTask.doInBackground(InstrumentationRegistry.getContext());
        System.out.println(joke);
        assertTrue(joke != null && !joke.isEmpty());
        assertEquals(joke, "funny joke goes here");
    }
}
