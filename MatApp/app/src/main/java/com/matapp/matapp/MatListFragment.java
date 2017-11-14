package com.matapp.matapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * This activity is used to display a list of all Material items using a Recycler View.
 *
 * Created by kathrinkoebel on 25.10.17.
 *
 */

public class MatListFragment extends Fragment {

    public List<Map<String,String>> materials;

    public RecyclerView recyclerView;
    public RecyclerView.Adapter recyclerViewAdapter;
    public RecyclerView.LayoutManager recyclerViewLayoutManager;

    private FirebaseDatabase database;
    private DatabaseReference itemReference;

    // Creates a new fragment given an int and title
    // DemoFragment.newInstance(5, "Hello");
    public static MatListFragment newInstance(int someInt, String someTitle) {
        MatListFragment fragment = new MatListFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        args.putString("someTitle", someTitle);
        fragment.setArguments(args);
        return fragment;
    }


    /* Lifecycle Methods */

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.fragment_mat_list, parent, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_matlist);

        //Get Firebase database instance
        database = FirebaseDatabase.getInstance();
        //Get reference to material
        itemReference = database.getReference("material/" + MatAppSession.getInstance().listKey + "/item");
        //Read at reference
        itemReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                materials = new ArrayList<Map<String,String>>();
                for(DataSnapshot item: dataSnapshot.getChildren()) {
                    //Add a HashMap to ArrayList for each item
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("itemKey", item.getKey());
                    map.put("title", item.child("title").getValue(String.class));
                    map.put("description", item.child("description").getValue(String.class));
                    map.put("thumb", item.child("thumb").getValue(String.class));
                    materials.add(map);
                }
                Log.i("MatListFragment", "items in list: " + materials);

                // check if list is empty (first use)
                if (materials.isEmpty()) {
                    // TODO add some Text for first use case
                    Toast.makeText(getContext(), getResources().getString(R.string.first_use_title) + getResources().getString(R.string.first_use_text), Toast.LENGTH_LONG).show();
                }

                //Pass ArrayList to RecyclerView
                recyclerViewAdapter = new RecyclerAdapter(materials);
                recyclerViewLayoutManager = new LinearLayoutManager(MatListFragment.this.getContext());
                recyclerView.setLayoutManager(recyclerViewLayoutManager);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here

        // FAB neues Material erstellen
        FloatingActionButton fabAddItem = (FloatingActionButton) view.findViewById(R.id.fab_add_item);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, MatAddActivity.class);
                startActivity(intent);
            }
        });

        if(MatAppSession.getInstance().listWriteable == false) {
            fabAddItem.setVisibility(View.GONE);
        }
    }

}
