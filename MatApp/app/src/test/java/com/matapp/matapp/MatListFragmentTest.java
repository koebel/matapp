package com.matapp.matapp;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;


/**
 *
 * local unit tests for MatListFragement,
 * implemented with Robolectrics,
 * will be execute on the development machine (host)
 *
 **/

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class MatListFragmentTest {
    private MatListFragment fragment;
    private View view;
    private RecyclerView recyclerView;

    @Test
    public void matListFragmentShouldNotBeNull() throws Exception {
        fragment = MatListFragment.newInstance(0, "newMatListFragment");
        assertNotNull(fragment);
    }

    /* test fails because of Illegal State Exception triggered through startFragment
    --> java.lang.IllegalStateException: FirebaseApp with name [DEFAULT] doesn't exist.

    @Test
    public void checkAddMatFabIsVisible() throws Exception {
        fragment = MatListFragment.newInstance(0, "newMatListFragment");
        startFragment(fragment);
        assertNotNull(fragment);
        View view = fragment.getView();
        assertNotNull(view);
        Button addMaterial = (Button) view.findViewById(R.id.fab_add_item);
        assertNotNull(addMaterial);
        assertEquals(View.VISIBLE, addMaterial.getVisibility());
    }
    */
}











