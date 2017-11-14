package com.matapp.matapp;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


/**
 *
 * local unit tests for Recycler Adapter,
 * implemented with Robolectrics,
 * will be execute on the development machine (host)
 *
 **/

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class RecyclerAdapterTest {

    private RecyclerAdapter emptyRecyclerAdapter;
    private RecyclerAdapter recyclerAdapter;


    @Before
    public void setUp() throws Exception {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();

        emptyRecyclerAdapter = new RecyclerAdapter(null);

        Map<String,String> map = new HashMap<String,String>();
        map.put("itemKey", "testKey");
        map.put("title", "testTitle");
        map.put("description", "testDescription");
        map.put("thumb", "testThumb");
        list.add(map);

        recyclerAdapter = new RecyclerAdapter(list);
    }

    @Test
    public void testEmptyRecyclerAdapterIsEmpty() {
        assertEquals(0, emptyRecyclerAdapter.getItemCount());
    }

    @Test
    public void testRecyclerAdapterSize() {
        assertEquals(1, recyclerAdapter.getItemCount());
    }

    @Test
    public void testListContent() {
        assertEquals("testKey", recyclerAdapter.getItems().get(0).get("itemKey"));
        assertEquals("testTitle", recyclerAdapter.getItems().get(0).get("title"));
        assertEquals("testDescription", recyclerAdapter.getItems().get(0).get("description"));
        assertEquals("testThumb", recyclerAdapter.getItems().get(0).get("thumb"));
    }
}









